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
package org.apache.commons.io.output;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link AppendableOutputStream}.
 */
class AppendableOutputStreamTest {

    private AppendableOutputStream<StringBuilder> out;

    @BeforeEach
    public void setUp() {
        out = new AppendableOutputStream<>(new StringBuilder());
    }

    @Test
    void testWriteInt() throws Exception {
        out.write('F');

        assertEquals("F", out.getAppendable().toString());
    }

    @Test
    void testWriteStringBuilder() throws Exception {
        final String testData = "ABCD";

        out.write(testData.getBytes());

        assertEquals(testData, out.getAppendable().toString());
    }
}
