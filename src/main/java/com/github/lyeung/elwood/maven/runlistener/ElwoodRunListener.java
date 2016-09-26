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

package com.github.lyeung.elwood.maven.runlistener;

import com.github.lyeung.elwood.maven.runlistener.impl.ResultMapImpl;
import com.github.lyeung.elwood.maven.runlistener.impl.ResultMapReporterImpl;
import com.github.lyeung.elwood.maven.runlistener.impl.ResultMapWriterImpl;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.io.IOException;

/**
 * Created by lyeung on 13/11/2015.
 */
public class ElwoodRunListener extends RunListener {

    private static final String ELWOOD_RUN_LISTENER_RESULT_FILE = ".elwood.result";

    private ResultMapImpl resultMap = new ResultMapImpl();

    private ResultMapWriter resultMapWriter = new ResultMapWriterImpl();

    private String filename;

    /**
     * Collect all results and into a message string.
     *
     * @param result result
     * @throws Exception is thrown if an error occurs
     */
    @Override
    public void testRunFinished(Result result) throws Exception {
        ResultMapReporter reporter = new ResultMapReporterImpl(result, resultMap);
        writeResult(reporter.getReport());
    }

    private void writeResult(String content) throws IOException {
        resultMapWriter.write(content, new File(System.getenv("ELWOOD_BUILD_DIR"), "test-results"),
                filename + ELWOOD_RUN_LISTENER_RESULT_FILE);
    }

    @Override
    public void testStarted(Description description) throws Exception {
        resultMap.addSucceeded(description);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        filename = description.getTestClass().getCanonicalName();
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        resultMap.addFailed(failure);
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        resultMap.addFailed(failure);
    }

    @Override
    public void testIgnored(Description description) {
        resultMap.addIgnored(description);
    }
}

