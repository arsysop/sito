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
import ru.arsysop.lang.function.CachingSupplier
import ru.arsysop.site.plugin.content.ContentResidence

import java.nio.file.Path

@PackageScope
class ContentDirectory implements ContentResidence {

    private final CachingFunction<Project, Path> content
    private final CachingSupplier<Path> templates
    private final CachingSupplier<Path> projects
    private final CachingSupplier<Path> blog

    ContentDirectory(Project project) {
        this.content = new CachingFunction<>(project, { contentDirectory it })
        this.blog = new CachingSupplier<>({ this.content.get().resolve("blog") })
        this.templates = new CachingSupplier<>({ this.content.get().resolve("templates") })
        this.projects = new CachingSupplier<>({ this.content.get().resolve("projects") })
    }

    private Path contentDirectory(Project project) {
        project.file("src/main/resources/content").toPath()
    }

    Path templates() {
        templates.get()
    }

    Path projects() {
        projects.get()
    }

    Path blog() {
        blog.get()
    }

}
