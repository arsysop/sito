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
