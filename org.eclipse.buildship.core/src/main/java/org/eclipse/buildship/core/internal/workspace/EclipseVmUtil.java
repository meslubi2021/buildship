/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.workspace;

import java.io.File;
import java.util.Arrays;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

import org.eclipse.jdt.internal.launching.StandardVMType;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstall2;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMStandin;
import org.eclipse.jdt.launching.environments.IExecutionEnvironment;

/**
 * Helper class to access the installed VMs in the Eclipse registry.
 */
@SuppressWarnings("restriction")
public final class EclipseVmUtil {

    private static final String VM_ID_PREFIX = "org.eclipse.buildship.core.vm.";

    /**
     * Finds a Java VM in the Eclipse VM registry or registers a new one if none was available with
     * the selected version.
     *
     * @param version the VM's supported Java version
     * @param location the location of the VM
     * @return the reference of an existing or newly created VM
     */
    public static IVMInstall findOrRegisterStandardVM(String version, File location) {
        Preconditions.checkNotNull(version);
        Preconditions.checkNotNull(location);

        return findOrRegisterVM(version, location);
    }

    /**
     * Finds a Java VM in the Eclipse VM registry or registers a new one.
     *
     * @param location the location of the VM
     * @return the reference of an existing or newly created VM
     */
    public static IVMInstall findOrRegisterStandardVM(File location) {
        Preconditions.checkNotNull(location);
        return findOrRegisterVM(location);
    }

    private static IVMInstall findOrRegisterVM(String version, File location) {
        Optional<IVMInstall> vm = findRegisteredVM(version);
        return vm.isPresent() ? vm.get() : registerNewVM("Java " + version, location);
    }

    private static IVMInstall findOrRegisterVM(File location) {
        Optional<IVMInstall> vm = findRegisteredVM(location);
        return vm.isPresent() ? vm.get() : registerNewVM(location);
    }

    private static Optional<IVMInstall> findRegisteredVM(String version) {
        Optional<IExecutionEnvironment> possibleExecutionEnvironment = findExecutionEnvironment(version);
        if (!possibleExecutionEnvironment.isPresent()) {
            return Optional.absent();
        }

        IExecutionEnvironment executionEnvironment = possibleExecutionEnvironment.get();
        IVMInstall defaultVm = executionEnvironment.getDefaultVM();
        if (defaultVm != null) {
            return Optional.of(defaultVm);
        } else {
            IVMInstall firstVm = Iterables.getFirst(Arrays.asList(executionEnvironment.getCompatibleVMs()), null);
            return Optional.fromNullable(firstVm);
        }
    }

    private static Optional<IVMInstall> findRegisteredVM(File location) {
        for (IExecutionEnvironment executionEnvironment : JavaRuntime.getExecutionEnvironmentsManager().getExecutionEnvironments()) {
            for (IVMInstall vm : executionEnvironment.getCompatibleVMs()) {
                if (location.equals(vm.getInstallLocation())) {
                    return Optional.fromNullable(vm);
                }
            }
        }
        return Optional.absent();
    }

    /**
     * Finds the execution environment for the given compliance version, e.g. 'JavaSE-1.6' for
     * version '1.6'.
     *
     * @param version the Java version
     * @return the execution environment or {@link Optional#absent()} if none was found
     */
    public static Optional<IExecutionEnvironment> findExecutionEnvironment(String version) {
        String executionEnvironmentId = getExecutionEnvironmentId(version);
        for (IExecutionEnvironment executionEnvironment : JavaRuntime.getExecutionEnvironmentsManager().getExecutionEnvironments()) {
            if (executionEnvironment.getId().equals(executionEnvironmentId)) {
                return Optional.of(executionEnvironment);
            }
        }
        return Optional.absent();
    }

    private static String getExecutionEnvironmentId(String version) {
        // the result values correspond to the standard execution environment definitions in the
        // org.eclipse.jdt.launching/plugin.xml file
        if ("1.1".equals(version)) {
            return "JRE-1.1";
        } else if (Arrays.asList("1.5", "1.4", "1.3", "1.2").contains(version)) {
            return "J2SE-" + version;
        } else {
            return "JavaSE-" + version;
        }
    }

    private static IVMInstall registerNewVM(File location) {
        // use the 'Standard VM' type to register a new VM
        IVMInstallType installType = JavaRuntime.getVMInstallType(StandardVMType.ID_STANDARD_VM_TYPE);

        // both the id and the name have to be unique for the registration
        String vmId = generateUniqueVMId(installType);

        // create the VM without firing events on individual method calls
        VMStandin vm = new VMStandin(installType, vmId);
        vm.setName("Java (unknown version)");
        vm.setInstallLocation(location);
        IVMInstall realVm = vm.convertToRealVM();
        if (realVm instanceof IVMInstall2) {
            realVm.setName("Java " + ((IVMInstall2) realVm).getJavaVersion());
        }
        return realVm;

    }

    private static IVMInstall registerNewVM(String name, File location) {
        // use the 'Standard VM' type to register a new VM
        IVMInstallType installType = JavaRuntime.getVMInstallType(StandardVMType.ID_STANDARD_VM_TYPE);

        // both the id and the name have to be unique for the registration
        String vmId = generateUniqueVMId(installType);

        // create the VM without firing events on individual method calls
        VMStandin vm = new VMStandin(installType, vmId);
        vm.setName(name);
        vm.setInstallLocation(location);
        return vm.convertToRealVM();
    }

    private static String generateUniqueVMId(IVMInstallType type) {
        // return a unique id for the VM
        int counter = 1;
        String vmId = VM_ID_PREFIX + counter;
        while (type.findVMInstall(vmId) != null) {
            counter++;
            vmId = VM_ID_PREFIX + counter;
        }
        return vmId;
    }

    private EclipseVmUtil() {
    }

}
