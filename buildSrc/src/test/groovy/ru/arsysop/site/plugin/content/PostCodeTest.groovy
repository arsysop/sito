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

class PostCodeTest {

    @Test
    void 'rich title'() {
        Assertions.assertEquals(
                '2012-11-21-post-12-title-',
                new Posts.PostCode(
                        [
                                date : '2012-11-21',
                                title: 'Post 12 titlE!'
                        ]).get())
    }

    @Test
    void 'simple title'() {
        Assertions.assertEquals(
                'date-title',
                new Posts.PostCode(
                        [
                                date : 'date',
                                title: 'title'
                        ]).get())
    }

    @Test
    void 'no title'() {
        Assertions.assertThrows(NullPointerException.class,
                {
                    new Posts.PostCode(
                            [
                                    date : '2012-11-21',
                                    title: null
                            ]).get()
                }
        )
    }

    @Test
    void 'no date'() {
        Assertions.assertThrows(NullPointerException.class,
                {
                    new Posts.PostCode(
                            [
                                    date : null,
                                    title: 'some title'
                            ]).get()
                }
        )
    }

}
