/*******************************************************************************
 * Copyright (c) 2020 ArSysOp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package ru.arsysop.site.plugin

import groovy.transform.PackageScope
import org.gradle.api.Project
import ru.arsysop.lang.function.CachingFunction

import java.nio.file.Path
import java.util.function.Supplier

@PackageScope
class BuildDirectory implements Supplier<Path> {

    private final CachingFunction<Project, Path> directory

    BuildDirectory(Project project) {
        this.directory = new CachingFunction<>(project, { freshOne it })
    }

    private Path freshOne(Project project) {
        String name = 'sito'
        File target = new File(project.buildDir, name)
        if (target.exists()) {
            if (!target.deleteDir()) {
                throw new RuntimeException("Failed to clean ${target.absolutePath}")
            }
        }
        project.mkdir(new File(project.buildDir, name)).toPath()
    }

    Path get() {
        directory.get()
    }

}
