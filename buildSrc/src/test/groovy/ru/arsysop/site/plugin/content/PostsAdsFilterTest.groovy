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

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import java.nio.file.Path
import java.nio.file.Paths

class PostsAdsFilterTest {

    @Test
    void filterNone() {
        Path residence = Paths.get(getClass().getResource("/posts").toURI())
        List<Map> ads = new Posts(residence, new EmptyContentSupplier())
                .ads(new EmptyPage())
        Assertions.assertEquals(3, ads.size())
    }

    @Test
    void filterSingle() {
        Path residence = Paths.get(getClass().getResource("/posts").toURI())
        List<Map> ads = new Posts(residence, new EmptyContentSupplier())
                .ads(new EmptyPage(), { post -> post.tags.contains("egg") })
        Assertions.assertEquals(2, ads.size())
    }

}
