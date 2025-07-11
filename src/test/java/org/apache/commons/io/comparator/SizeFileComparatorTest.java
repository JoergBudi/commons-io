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
package org.apache.commons.io.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.test.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link SizeFileComparator}.
 */
class SizeFileComparatorTest extends ComparatorAbstractTest {

    private File smallerDir;
    private File largerDir;
    private File smallerFile;
    private File largerFile;

    @BeforeEach
    public void setUp() throws Exception {
        comparator = (AbstractFileComparator) SizeFileComparator.SIZE_COMPARATOR;
        reverse = SizeFileComparator.SIZE_REVERSE;
        smallerDir = new File(dir, "smallerdir");
        largerDir = new File(dir, "largerdir");
        smallerFile = new File(smallerDir, "smaller.txt");
        final File equalFile = new File(dir, "equal.txt");
        largerFile = new File(largerDir, "larger.txt");
        smallerDir.mkdir();
        largerDir.mkdir();
        if (!smallerFile.getParentFile().exists()) {
            throw new IOException("Cannot create file " + smallerFile
                    + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output2 =
                new BufferedOutputStream(Files.newOutputStream(smallerFile.toPath()))) {
            TestUtils.generateTestData(output2, 32);
        }
        if (!equalFile.getParentFile().exists()) {
            throw new IOException("Cannot create file " + equalFile
                    + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output1 =
                new BufferedOutputStream(Files.newOutputStream(equalFile.toPath()))) {
            TestUtils.generateTestData(output1, 48);
        }
        if (!largerFile.getParentFile().exists()) {
            throw new IOException("Cannot create file " + largerFile
                    + " as the parent directory does not exist");
        }
        try (BufferedOutputStream output =
                new BufferedOutputStream(Files.newOutputStream(largerFile.toPath()))) {
            TestUtils.generateTestData(output, 64);
        }
        equalFile1 = equalFile;
        equalFile2 = equalFile;
        lessFile   = smallerFile;
        moreFile   = largerFile;
    }

    /**
     * Test a file which doesn't exist.
     */
    @Test
    void testCompareDirectorySizes() {
        assertEquals(0, comparator.compare(smallerDir, largerDir), "sumDirectoryContents=false");
        assertEquals(-1, SizeFileComparator.SIZE_SUMDIR_COMPARATOR.compare(smallerDir, largerDir), "less");
        assertEquals(1, SizeFileComparator.SIZE_SUMDIR_REVERSE.compare(smallerDir, largerDir), "less");
    }

    /**
     * Test a file which doesn't exist.
     */
    @Test
    void testNonExistentFile() {
        final File nonExistentFile = new File(FileUtils.current(), "non-existent.txt");
        assertFalse(nonExistentFile.exists());
        assertTrue(comparator.compare(nonExistentFile, moreFile) < 0, "less");
    }
}
