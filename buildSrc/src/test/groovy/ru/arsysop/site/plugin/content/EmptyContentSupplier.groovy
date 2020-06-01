package ru.arsysop.site.plugin.content

import ru.arsysop.site.plugin.content.ContentSupplier
import ru.arsysop.site.plugin.content.Page

class EmptyContentSupplier implements ContentSupplier{
    @Override
    String reference(String code, Page from) {
        'no reference'
    }

    @Override
    String fragment(String code, Page from) {
        'no fragment either'
    }

    @Override
    String dynamicFragment(String code, Page from, Map data) {
        'nope, no dyn fragment'
    }
}
