/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * This filter accepts {@link File}s that are directories.
 * <p>
 * For example, here is how to print out a list of the current directory's subdirectories:
 * </p>
 * <h2>Using Classic IO</h2>
 *
 * <pre>
 * File dir = FileUtils.current();
 * String[] files = dir.list(DirectoryFileFilter.INSTANCE);
 * for (String file : files) {
 *     System.out.println(file);
 * }
 * </pre>
 *
 * <h2>Using NIO</h2>
 *
 * <pre>
 * final Path dir = PathUtils.current();
 * final AccumulatorPathVisitor visitor = AccumulatorPathVisitor.withLongCounters(DirectoryFileFilter.INSTANCE);
 * //
 * // Walk one directory
 * Files.<strong>walkFileTree</strong>(dir, Collections.emptySet(), 1, visitor);
 * System.out.println(visitor.getPathCounters());
 * System.out.println(visitor.getFileList());
 * //
 * visitor.getPathCounters().reset();
 * //
 * // Walk directory tree
 * Files.<strong>walkFileTree</strong>(dir, visitor);
 * System.out.println(visitor.getPathCounters());
 * System.out.println(visitor.getDirList());
 * System.out.println(visitor.getFileList());
 * </pre>
 * <h2>Deprecating Serialization</h2>
 * <p>
 * <em>Serialization is deprecated and will be removed in 3.0.</em>
 * </p>
 *
 * @since 1.0
 * @see FileFilterUtils#directoryFileFilter()
 */
public class DirectoryFileFilter extends AbstractFileFilter implements Serializable {

    /**
     * Singleton instance of directory filter.
     *
     * @since 1.3
     */
    public static final IOFileFilter DIRECTORY = new DirectoryFileFilter();

    /**
     * Singleton instance of directory filter. Please use the identical DirectoryFileFilter.DIRECTORY constant. The new
     * name is more JDK 1.5 friendly as it doesn't clash with other values when using static imports.
     */
    public static final IOFileFilter INSTANCE = DIRECTORY;

    private static final long serialVersionUID = -5148237843784525732L;

    /**
     * Restrictive constructor.
     */
    protected DirectoryFileFilter() {
        // empty.
    }

    /**
     * Tests to see if the file is a directory.
     *
     * @param file the File to check
     * @return true if the file is a directory
     */
    @Override
    public boolean accept(final File file) {
        return isDirectory(file);
    }

    /**
     * Tests to see if the file is a directory.
     *
     * @param file the File to check
     * @param attributes the path's basic attributes (may be null).
     * @return true if the file is a directory
     * @since 2.9.0
     */
    @Override
    public FileVisitResult accept(final Path file, final BasicFileAttributes attributes) {
        return toFileVisitResult(file != null && Files.isDirectory(file));
    }

}
