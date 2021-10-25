package ru.arsysop.site.plugin

import org.gradle.api.provider.Property

interface SitoPluginExtension {

    Property<Boolean> getOpen()

}
