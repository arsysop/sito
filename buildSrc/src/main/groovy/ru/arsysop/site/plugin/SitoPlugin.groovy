/*******************************************************************************
 * Copyright (c) 2020, 2021 ArSysOp
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

import org.gradle.api.Plugin
import org.gradle.api.Project
import ru.arsysop.site.plugin.content.Site

import java.awt.Desktop
import java.nio.file.Path
import java.util.function.Supplier

final class SitoPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('sito', SitoPluginExtension)
        project.task("sito") {
            group "Build"
            description "build a fresh ArSysOp site assembly"
            doLast {
                Supplier<Path> target = new BuildDirectory(project);
                new Site(new ContentDirectory(project), target).generate()
                print(target.get())
                if(extension.open){
                    Desktop.desktop.open(target.get().resolve("index.html").toFile())
                }
            }
        }
    }
}