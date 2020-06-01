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

class TagsExtractedTest {

    @Test
    void 'reduce duplicates'() {
        Assertions.assertEquals(
                ['egg', 'plate'].toSet(),
                new Posts.TagsExtracted([tags: 'egg,plate,egg,plate']).get()
        )
    }

    @Test
    void 'tags are comma-separated'() {
        Assertions.assertEquals(
                ['egg', 'plate', 'egg;plate'].toSet(),
                new Posts.TagsExtracted([tags: 'egg,plate,egg;plate']).get()
        )
    }

    @Test
    void 'tags are trimmed'() {
        Assertions.assertEquals(
                ['egg', 'plate'].toSet(),
                new Posts.TagsExtracted([tags: ' egg, plate ,egg ']).get()
        )
    }

    @Test
    void 'no empty tags'() {
        Assertions.assertEquals(
                ['egg', 'plate'].toSet(),
                new Posts.TagsExtracted([tags: ' egg, ,   ,\t, plate ']).get()
        )
    }

    @Test
    void 'no tags - no fuss'() {
        Assertions.assertTrue(new Posts.TagsExtracted([:]).get().isEmpty())
    }

    @Test
    void 'null tags is ok'() {
        Assertions.assertTrue(new Posts.TagsExtracted([tags: null]).get().isEmpty())
    }

    @Test
    void 'source Post cannot be null'() {
        Assertions.assertThrows(NullPointerException.class) { new Posts.TagsExtracted(null).get() }
    }


}
