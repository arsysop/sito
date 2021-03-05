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

import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Supplier

class Site {
    
    // todo: huge. refactor
    private final Templates templates
    private final Supplier<Path> output
    private final ContentResidence content
    private final Map<String, Page> pages // todo: cache it
    private final Map<String, Fragment> fragments
    private final Projects projects
    private final Posts posts
    private final ContentSupplier contentSupplier
    private final Set<String> generated = new HashSet<>()

    Site(ContentResidence content, Supplier<Path> output) {
        this.content = content
        this.output = output
        this.templates = new Templates(content.templates(), output)
        this.contentSupplier = new Content()
        this.projects = new Projects(content.projects(), contentSupplier)
        this.posts = new Posts(content.blog(), contentSupplier)
        pages = [
                new IndexPage(contentSupplier, projects),
                new AboutPage(contentSupplier),
                *projects.pages(),
                *posts.pages(),
                new BlogPage(contentSupplier, posts)
        ].collectEntries { [it.code(), it] }
        println "Pages ${pages.size()} : ${pages.keySet()}"
        fragments = [
                new HeaderFragment(contentSupplier),
                new FooterFragment(),
                *posts.fragments()
        ].collectEntries { [it.code(), it] }
        println "Fragmenets ${fragments.size()} : ${fragments.keySet()}"
    }

    void generate() {
        compileStyles()
        generatePage("index")
    }

    private void compileStyles(){
        new Styles(content.styles(), output).compile()
    }

    private void generatePage(String code) {
        if (generated.contains(code)) {
            return
        }
        generated.add(code)
        pages.get(code).with { templates.build(it)}
    }

    private class Content implements ContentSupplier {

        @Override
        String reference(String code, Page from) {
            println "reference page ${code} from page ${from.code()}"
            generatePage(code) // do not reference absent page
            pages.get(code).with { page ->
                String path = Paths.get(from.path()).relativize(Paths.get(page.path()))
                "${path}${path ? '/' : ''}${code}.html"
            }
        }

        @Override
        String fragment(String code, Page from) {
            println "request fragment ${code} for page ${from.code()}"
            templates.content(fragments.get(code), [:], from)
        }

        @Override
        String dynamicFragment(String code, Page from, Map data) {
            println "request dynamic fragment ${code} for page ${from.code()}"
            templates.content(fragments.get(code), data, from)
        }

    }

}
