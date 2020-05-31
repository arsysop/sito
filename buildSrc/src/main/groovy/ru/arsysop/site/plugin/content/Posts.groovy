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
class Posts {

    private final Path residence
    private final ContentSupplier content
    private final CachingSupplier<Map<String, Map>> index
    private final CachingSupplier<Set<String>> tags

    Posts(Path residence, ContentSupplier content) {
        this.residence = residence
        this.content = content
        this.index = new CachingSupplier({
            new JsonSlurper().parse(
                    residence.resolve("index.json").toFile(), "UTF-8")
        })
        this.tags = new CachingSupplier({
            index.get().posts
                    .collect { tagsExtracted(it.tags) }
                    .flatten()
                    .toSet()
        })
    }

    List<Map> ads(Page owner, Closure<Boolean> filter = { true }) {
        index.get().posts
                .withIndex()
                .findAll(filter)
                .collect { post, position ->
                    String code = position as String
                    [
                            ABOUT_SHORT: post.title,
                            ABOUT_WIDE : post.synopsis,
                            DATE       : post.date,
                            CONTENT    : post.synopsis,
                            REF_FULL   : content.reference(code, owner)
                    ]
                }
    }

    List<Page> pages() {
        index.get().posts.withIndex().collect { post, position -> new PostPage(post, position as String) } +
                tags.get().collect { new TagPage(it) }
    }

    List<Fragment> fragments() {
        tags.get().collect { new TagFragment(it) } + [new TagsFragment()]
    }

    Set<String> tags() {
        tags.get()
    }

    private Set<String> tagsExtracted(String tags) {
        tags.toLowerCase().split(',').toList().toSet()
    }

    private class PostPage extends Page {

        private final Map source
        private final String code

        PostPage(Map source, String code) {
            this.code = code
            this.source = source
        }

        @Override
        String path() {
            "./blog/${source.folder}"
        }

        @Override
        String code() {
            code
        }

        @Override
        Map data() {
            [
                    TITLE           : source.title,
                    HEADER          : content.fragment('header', PostPage.this),
                    FOOTER          : content.fragment('footer', PostPage.this),
                    CONTENT         : residence
                            .resolve('posts')
                            .resolve(source.folder)
                            .resolve('index.html')
                            .getText("UTF-8"),
                    TAGS            : content.dynamicFragment(
                            'tags_set',
                            PostPage.this,
                            new DynamicTagsFragmentData(tagsExtracted(source.tags), content, PostPage.this).get()),
                    META_KEYWORDS   : source.keywords,
                    META_DESCRIPTION: source.description
            ]
        }

        @Override
        String template() {
            "post"
        }
    }

    private class TagPage extends Page {

        private final String value
        private final SingleTagUnitCodes codes

        TagPage(String value) {
            this.value = value
            this.codes = new SingleTagUnitCodes(value)
        }

        @Override
        String path() {
            './tags'
        }

        @Override
        String code() {
            codes.page()
        }

        @Override
        Map data() {
            [
                    POSTS : ads(TagPage.this, { post, position -> tagsExtracted(post.tags).contains(value) }),
                    TAGS  : content.fragment(codes.fragment(), TagPage.this),
                    HEADER: content.fragment('header', TagPage.this),
                    FOOTER: content.fragment('footer', TagPage.this)
            ]
        }

        @Override
        String template() {
            'blog'
        }
    }

    private class TagFragment extends Fragment {

        private final String tag
        private final SingleTagUnitCodes codes

        TagFragment(String tag) {
            this.tag = tag
            this.codes = new SingleTagUnitCodes(tag)
        }

        @Override
        String code() {
            codes.fragment()
        }

        @Override
        Map data(Page owner) {
            [TAGS: [
                    REF : content.reference(codes.page(), owner),
                    NAME: tag
            ]]
        }

        @Override
        String template() {
            'tags'
        }
    }

    private class TagsFragment extends Fragment {
        @Override
        String code() {
            'tags_set'
        }

        @Override
        Map data(Page owner) {
            [:]
        }

        @Override
        String template() {
            'tags'
        }
    }

}
