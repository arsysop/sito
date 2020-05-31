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

import groovy.transform.PackageScope

@PackageScope
class IndexPage extends Page {

    private final ContentSupplier content
    private final Projects projects

    IndexPage(ContentSupplier content, Projects projects) {
        this.projects = projects
        this.content = content
    }

    @Override
    String path() {
        '.'
    }

    @Override
    String code() {
        'index'
    }

    @Override
    Map data() {
        [
                ABOUT_REF: content.reference('about', this),
                PROJECTS : projects.ads(this),
                HEADER   : content.fragment('header', this),
                FOOTER   : content.fragment('footer', this),
        ]
    }
}
