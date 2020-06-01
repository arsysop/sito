package ru.arsysop.site.plugin.content

class EmptyPage extends Page {
    @Override
    String code() {
        'no code'
    }

    @Override
    String path() {
        '.'
    }

    @Override
    Map data() {
        [:]
    }
}
