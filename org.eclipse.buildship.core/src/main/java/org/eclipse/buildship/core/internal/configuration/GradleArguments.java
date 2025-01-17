/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.gradle.api.JavaVersion;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.LongRunningOperation;
import org.gradle.tooling.model.build.BuildEnvironment;
import org.gradle.tooling.model.build.GradleEnvironment;
import org.gradle.tooling.model.build.JavaEnvironment;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;

import org.eclipse.buildship.core.GradleDistribution;
import org.eclipse.buildship.core.internal.CorePlugin;
import org.eclipse.buildship.core.internal.GradlePluginsRuntimeException;
import org.eclipse.buildship.core.internal.gradle.GradleProgressAttributes;
import org.eclipse.buildship.core.internal.i18n.CoreMessages;
import org.eclipse.buildship.core.internal.util.collections.CollectionsUtils;
import org.eclipse.buildship.core.internal.util.file.FileUtils;
import org.eclipse.buildship.core.internal.util.gradle.GradleVersion;
import org.eclipse.buildship.core.internal.workspace.EclipseVmUtil;

/**
 * Holds configuration values to apply on Tooling API objects.
 *
 * @author Donat Csikos
 */
public final class GradleArguments {

    private static final String INITSCRIPT_LOCATION = "/org/eclipse/buildship/core/internal/configuration/eclipsePlugin.gradle";

    private final File rootDir;
    private final GradleDistribution gradleDistribution;
    private final File gradleUserHome;
    private final File javaHome;
    private final boolean buildScansEnabled;
    private final boolean offlineMode;
    private final List<String> arguments;
    private final List<String> jvmArguments;

    private GradleArguments(File rootDir, GradleDistribution gradleDistribution, File gradleUserHome, File javaHome, boolean buildScansEnabled, boolean offlineMode,
            List<String> arguments, List<String> jvmArguments) {
        this.rootDir = Preconditions.checkNotNull(rootDir);
        this.gradleDistribution = Preconditions.checkNotNull(gradleDistribution);
        this.gradleUserHome = gradleUserHome;
        this.javaHome = javaHome;
        this.buildScansEnabled = buildScansEnabled;
        this.offlineMode = offlineMode;
        this.arguments = ImmutableList.copyOf(arguments);
        this.jvmArguments = ImmutableList.copyOf(jvmArguments);
    }

    public void describe(GradleProgressAttributes progressAttributes, BuildEnvironment buildEnvironment) {
        GradleEnvironment gradleEnv = buildEnvironment.getGradle();
        JavaEnvironment javaEnv = buildEnvironment.getJava();

        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_WorkingDirectory, this.rootDir));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.Preference_Label_Gradle_User_Home, toNonEmpty(this.gradleUserHome != null ? this.gradleUserHome
                : getGradleUserHome(gradleEnv), CoreMessages.Value_UseGradleDefault)));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_GradleDistribution, this.gradleDistribution.getDisplayName()));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_GradleVersion, gradleEnv.getGradleVersion()));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_JavaHome, toNonEmpty(this.javaHome != null ? this.javaHome
                : javaEnv.getJavaHome(), CoreMessages.Value_UseGradleDefault)));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_JvmArguments, toNonEmpty(this.jvmArguments, CoreMessages.Value_None)));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_Arguments, toNonEmpty(this.arguments, CoreMessages.Value_None)));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_BuildScansEnabled, this.buildScansEnabled));
        progressAttributes.writeConfig(String.format("%s: %s", CoreMessages.RunConfiguration_Label_OfflineModeEnabled, this.offlineMode));
    }

    private static File getGradleUserHome(GradleEnvironment gradleEnvironment) {
        try {
            return gradleEnvironment.getGradleUserHome();
        } catch (Exception ignore) {
            return null;
        }
    }

    private String toNonEmpty(File fileValue, String defaultMessage) {
        String string = FileUtils.getAbsolutePath(fileValue).orNull();
        return string != null ? string : defaultMessage;
    }

    private String toNonEmpty(List<String> stringValues, String defaultMessage) {
        String string = Strings.emptyToNull(CollectionsUtils.joinWithSpace(stringValues));
        return string != null ? string : defaultMessage;
    }

    public void applyTo(GradleConnector connector) {
        connector.forProjectDirectory(this.rootDir);
        connector.useGradleUserHomeDir(this.gradleUserHome);
        this.gradleDistribution.apply(connector);
    }

    public void applyTo(LongRunningOperation operation, BuildEnvironment environment) {
        operation.withArguments(collectArguments(this.arguments, this.buildScansEnabled, this.offlineMode, environment, this.javaHome));
        operation.setJavaHome(this.javaHome);
        operation.setJvmArguments(this.jvmArguments);
    }

    public static GradleArguments from(File rootDir, GradleDistribution gradleDistribution, File gradleUserHome, File javaHome, boolean buildScansEnabled, boolean offlineMode,
            List<String> arguments, List<String> jvmArguments) {
        return new GradleArguments(rootDir, gradleDistribution, gradleUserHome, javaHome, buildScansEnabled, offlineMode, arguments, jvmArguments);
    }

    private static List<String> collectArguments(List<String> baseArgs, boolean buildScansEnabled, boolean offlineMode, BuildEnvironment buildEnvironment, File javaHome) {
        List<String> arguments = Lists.newArrayList(baseArgs);
        if (buildScansEnabled) {
            String buildScanArgument = buildScanArgumentFor(buildEnvironment);
            if (!arguments.contains(buildScanArgument)) {
                arguments.add(buildScanArgument);
            }
        }
        if (offlineMode && !arguments.contains("--offline")) {
            arguments.add("--offline");
        }
        arguments.addAll(CorePlugin.invocationCustomizer().getExtraArguments());
        arguments.addAll(getInitScriptArguments(javaHome));
        return arguments;
    }

    private static String buildScanArgumentFor(BuildEnvironment environment) {
        if (GradleVersion.version(environment.getGradle().getGradleVersion()).supportsDashDashScan()) {
            return "--scan";
        } else {
            return "-Dscan";
        }
    }

    private static List<String> getInitScriptArguments(File javaHome) {
        if (javaHome == null) {
            javaHome = new File(System.getProperty("java.home"));
        }
        File initScript = getEclipsePluginInitScriptLocation(javaHome);
        maybeUpdateInitScript(initScript);
        return Arrays.asList("--init-script", initScript.getAbsolutePath());
    }

    private static void maybeUpdateInitScript(File scriptFile) {
        try {
            byte[] scriptContent = initScriptContent();
            if (!scriptFile.exists()) {
                Files.createParentDirs(scriptFile);
                Files.asByteSink(scriptFile).write(scriptContent);
            } else {
                // don't touch the file if no changes needed
                byte[] existingContent = Files.asByteSource(scriptFile).read();
                boolean scriptUpToDate = Arrays.equals(scriptContent, existingContent);
                if (!scriptUpToDate) {
                    Files.asByteSink(scriptFile).write(scriptContent);
                }
            }
        } catch (IOException e) {
            throw new GradlePluginsRuntimeException("Failed to create init script", e);
        }
    }

    private static byte[] initScriptContent() {
        URL resource = GradleVersion.class.getResource(INITSCRIPT_LOCATION);
        if (resource == null) {
            throw new GradlePluginsRuntimeException(String.format("Resource '%s' not found.", INITSCRIPT_LOCATION));
        }
        try {
            return ByteStreams.toByteArray(resource.openConnection().getInputStream());
        } catch (Exception e) {
            throw new GradlePluginsRuntimeException("Failed to read init script", e);
        }
    }

    private static File getEclipsePluginInitScriptLocation(File javaHome) {
        // Old Gradle versions might return the binary init script from the cache compiled with a
        // wrong Java version. To work around that we add the java version to the init script file
        // name.
        IVMInstall vm = EclipseVmUtil.findOrRegisterStandardVM(javaHome);
        String suffix = "";
        if (vm instanceof IVMInstall2) {
            String javaVersion = ((IVMInstall2) vm).getJavaVersion();
            suffix = "Java" + JavaVersion.toVersion(javaVersion).getMajorVersion();
        }

        return CorePlugin.getInstance().getStateLocation().append("init.d").append("buildshipInit" + suffix + ".gradle").toFile();
    }

}
