/*******************************************************************************
 * Copyright (c) 2021 ArSysOp
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
import org.lesscss.LessCompiler

import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Supplier

@PackageScope
final class Styles {

    private final Path source
    private final Supplier<Path> target
    private final String lessExt = ".less"
    private final String cssExt = ".css"
    private final LessCompiler compiler = new LessCompiler()


    Styles(Path source, Supplier<Path> target) {
        this.source = source
        this.target = target
    }

    void compile() {
        compileDir(source, sameNamed(source, target.get()))
    }

    private void compileDir(Path src, Path dst) {
        if (Files.exists(dst)) {
            dst.toFile().mkdirs() // do not even thing about using Files::create*
        }
        Files.list(src).each {
            if (Files.isDirectory(it)) {
                compileDir(it, sameNamed(it, dst))
            } else if (isLess(it)) {
                compileLess(it, sameNamedCss(it, dst))
            } else if (isCss(it)) {
                copyFile(it, sameNamed(it, dst))
            }
        }
    }

    private void compileLess(Path less, Path css) {
        compiler.compile(less.toFile(), css.toFile())
    }

    private void copyFile(Path src, Path dst){
        Files.copy(src, dst)
    }

    private Path sameNamed(Path src, Path parent) {
        parent.resolve(src.getFileName().toString())
    }

    private Path sameNamedCss(Path src, Path parent) {
        String less = src.getFileName().toString()
        String css = less.substring(0, less.length() - lessExt.length()) + cssExt
        parent.resolve(css)
    }

    private boolean isLess(Path file) {
        isOfExt(file, lessExt)
    }

    private boolean isCss(Path file) {
        isOfExt(file, cssExt)
    }

    private boolean isOfExt(Path file, String ext) {
        file.getFileName().toString().endsWith(ext)
    }

}
