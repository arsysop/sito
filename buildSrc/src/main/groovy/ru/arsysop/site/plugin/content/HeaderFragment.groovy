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
class HeaderFragment extends Fragment {

    private final ContentSupplier content

    HeaderFragment(ContentSupplier content) {
        this.content = content
    }

    @Override
    String code() {
        'header'
    }

    @Override
    Map data(Page owner) {
        [REF_INDEX: content.reference('index', owner),
         NAV      : [[
                             REF : content.reference('blog', owner),
                             NAME: "blog"
                     ], [
                             REF : content.reference('about', owner),
                             NAME: "about"
                     ]]
        ]
    }

}
