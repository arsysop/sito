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
                new Posts.TagsExtracted([tags: ' egg, , ,\t, plate ']).get()
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
