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

import com.github.lyeung.elwood.maven.runlistener.ResultMapWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lyeung on 25/09/16.
 */
public class ResultMapWriterImpl implements ResultMapWriter {

    @Override
    public void write(String content, File targetDir, String filename) throws IOException {
        if (!targetDir.isDirectory() && !targetDir.mkdir()) {
            throw new IOException("unable to create directory [" + targetDir.getAbsolutePath() + "]");
        }

        final File resultFile = new File(targetDir, filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {
            writer.write(content);
        } catch (IOException e) {
            // TODO: find a better way to capture this as a system exception
            e.printStackTrace(System.err);
            throw e;
        }
    }
}
