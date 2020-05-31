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

import groovy.json.JsonSlurper
import groovy.transform.PackageScope
import ru.arsysop.lang.function.CachingSupplier

import java.nio.file.Path

@PackageScope
class Projects {

    private final Path residence
    private final ContentSupplier content
    private final CachingSupplier<Map<String, Map>> index

    Projects(Path residence, ContentSupplier content) {
        this.residence = residence
        this.content = content
        this.index = new CachingSupplier({
            new JsonSlurper().parse(
                    residence.resolve("index.json").toFile(), "UTF-8")
        })
    }

    List<Map> ads(Page from) {
        index.get().projects.collect {
            [
                    ABOUT_SHORT: it.title,
                    ABOUT_WIDE : it.synopsis,
                    REF_FULL   : content.reference(it.code, from),
                    CONTENT    : residence
                            .resolve(it.code)
                            .resolve("${it.ad ?: index.get().defaults.ad}.html")
                            .getText("UTF-8"),
            ]
        }
    }

    List<Page> pages() {
        index.get().projects.collect { new ProjectPage(it) }
    }

    private class ProjectPage extends Page {

        private final Map source

        ProjectPage(Map source) {
            this.source = source
        }

        @Override
        String path() {
            './projects'
        }

        @Override
        String code() {
            source.code
        }

        @Override
        Map data() {
            [
                    TITLE           : source.title,
                    HEADER          : content.fragment('header', ProjectPage.this),
                    FOOTER          : content.fragment('footer', ProjectPage.this),
                    CONTENT         : residence
                            .resolve(source.code)
                            .resolve("${source.full ?: index.get().defaults.full}.html")
                            .getText("UTF-8"),
                    META_KEYWORDS   : source.keywords,
                    META_DESCRIPTION: source.description
            ]
        }

        @Override
        String template() {
            "project"
        }
    }

}
