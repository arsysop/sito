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
package ru.arsysop.site.plugin.content

import com.github.mustachejava.DefaultMustacheFactory
import groovy.transform.PackageScope

import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier

@PackageScope
class Templates {

    private final DefaultMustacheFactory factory
    private final Path residence
    private final Supplier<Path> output

    Templates(Path residence, Supplier<Path> output) {
        this.output = output
        this.residence = residence
        this.factory = new DefaultMustacheFactory()
    }

    void build(Page page) {
        println "build page ${page.code()}"
        output.get().resolve(page.path()).with {
            Files.createDirectories(it)
            it.resolve("${page.code()}.html")
                    .withWriter { writer ->
                        templatePath(page)
                                .withReader { factory.compile(it, "") }
                                .execute(writer, page.data())
                    }
        }
    }


    String content(Fragment fragment, Map data, Page owner) {
        println "build fragment ${fragment.code()} for page ${owner.code()}"
        new StringWriter().with { writer ->
            this.templatePath(fragment)
                    .withReader { factory.compile(it, "") }
                    .execute(writer, fragment.data(owner) + data)
        }
    }

    private Path templatePath(Templated source) {
        residence.resolve("${source.template()}.mustache")
    }

}
