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
