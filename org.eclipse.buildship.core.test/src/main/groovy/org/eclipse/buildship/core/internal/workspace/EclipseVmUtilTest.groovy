/*******************************************************************************
 * Copyright (c) 2023 Gradle Inc. and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.buildship.core.internal.workspace

import spock.lang.Specification

import org.eclipse.jdt.internal.launching.StandardVMType
import org.eclipse.jdt.launching.IVMInstall
import org.eclipse.jdt.launching.JavaRuntime

import org.eclipse.buildship.core.internal.workspace.EclipseVmUtil


@SuppressWarnings("restriction")
class EclipseVmUtilTest extends Specification {

    def "Can find an existing VM"() {
        when:
        EclipseVmUtil.findOrRegisterVM('1.7', new File("foo"))

        then:
        allVms().size() == old(allVms().size())
        !allVms().find {
            it.installLocation == "foo"
        }
    }

    def "Creates new VM if none registered for that version"() {
        given:
        File vmLocation = allVms().first().installLocation

        when:
        def vm = EclipseVmUtil.findOrRegisterVM('0.3', vmLocation)

        then:
        allVms().size() == old(allVms().size()) + 1

        and:
        with(vm) {
            id.startsWith EclipseVmUtil.VM_ID_PREFIX
            name == 'Java 0.3'
            VMInstallType.id == StandardVMType.ID_STANDARD_VM_TYPE
            installLocation == vmLocation
        }
    }

    def "Finds the matching execution environment"() {
        expect:
        EclipseVmUtil.findExecutionEnvironment(version).orNull()?.id == expected

        where:
        version | expected
        'foo'   | null
        '1.1'   | 'JRE-1.1'
        '1.2'   | 'J2SE-1.2'
        '1.3'   | 'J2SE-1.3'
        '1.4'   | 'J2SE-1.4'
        '1.5'   | 'J2SE-1.5'
        '1.6'   | 'JavaSE-1.6'
        '1.7'   | 'JavaSE-1.7'
    }

    private static List<IVMInstall> allVms() {
        JavaRuntime.VMInstallTypes.VMInstalls.findAll { it.installLocation }.flatten()
    }

}

