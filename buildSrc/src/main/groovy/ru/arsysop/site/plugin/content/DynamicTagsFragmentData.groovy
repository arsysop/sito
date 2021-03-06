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
package ru.arsysop.site.plugin.content

import groovy.transform.PackageScope

@PackageScope
final class DynamicTagsFragmentData {
    private final Set<String> tags
    private final ContentSupplier content
    private final Page page

    DynamicTagsFragmentData(Set<String> tags, ContentSupplier content, Page page) {
        this.tags = tags
        this.content = content
        this.page = page
    }

    Map get() {
        [TAGS: tags.collect { String tag ->
            [
                    REF : content.reference(new SingleTagUnitCodes(tag).page(), page),
                    NAME: tag
            ]
        }]
    }
}
