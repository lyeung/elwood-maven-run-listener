/*
 *
 * Copyright (C) 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.github.lyeung.elwood.maven.runlistener.impl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 25/09/16.
 */
public class ResultMapWriterImplTest {

    private static final String FILENAME = "result.txt";

    private static final String CONTENT = "this is a test\nand another test";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testWrite() throws IOException {
        File dir = folder.newFolder("test-result-map-writer-dir").getAbsoluteFile();
        new ResultMapWriterImpl().write(CONTENT, dir, FILENAME);

        assertEquals(CONTENT, FileUtils.readFileToString(new File(dir, FILENAME), "UTF-8"));
    }
}