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

import com.github.lyeung.elwood.maven.runlistener.ResultMap;
import com.github.lyeung.elwood.maven.runlistener.ResultMapReporterValue;
import com.github.lyeung.elwood.maven.runlistener.enums.ResultType;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by lyeung on 26/09/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ResultMapReporterImplTest {

    private static final ResultMap RESULT_MAP;

    static {
        ResultMap resultMap = new ResultMapImpl();
        resultMap.addSucceeded(Description.createTestDescription(
                ResultMapImplTest.class, "successMethod1()"));

        resultMap.addFailed(new Failure(Description.createTestDescription(
                ResultMapImplTest.class, "failedMethod1()"), new NullPointerException("mocked exception")));
        resultMap.addFailed(new Failure(Description.createTestDescription(
                ResultMapImplTest.class, "failedMethod2()"), new NullPointerException("mocked exception")));

        resultMap.addIgnored(Description.createTestDescription(
                ResultMapImplTest.class, "ignoredMethod1()"));
        resultMap.addIgnored(Description.createTestDescription(
                ResultMapImplTest.class, "ignoredMethod2()"));
        resultMap.addIgnored(Description.createTestDescription(
                ResultMapImplTest.class, "ignoredMethod3()"));

        RESULT_MAP = resultMap;
    }

    @Mock
    private Result result;

    @Test
    public void getIgnoredResult() {
        when(result.getIgnoreCount()).thenReturn(3);
        ResultMapReporterImpl reporter = new ResultMapReporterImpl(result, RESULT_MAP);

        ResultMapReporterValue reporterValue = reporter.getIgnoredResult();
        assertEquals(ResultType.IGNORED, reporterValue.getResultType());
        assertEquals(3, reporterValue.getCount());

        verify(result).getIgnoreCount();
        verifyNoMoreInteractions(result);
    }

    @Test
    public void getFailedResult() {
        when(result.getFailureCount()).thenReturn(2);
        ResultMapReporterImpl reporter = new ResultMapReporterImpl(result, RESULT_MAP);

        ResultMapReporterValue reporterValue = reporter.getFailedResult();
        assertEquals(ResultType.FAILED, reporterValue.getResultType());
        assertEquals(2, reporterValue.getCount());

        verify(result).getFailureCount();
        verifyNoMoreInteractions(result);
    }

    @Test
    public void getSucceededResult() {
        when(result.getRunCount()).thenReturn(1);
        ResultMapReporterImpl reporter = new ResultMapReporterImpl(result, RESULT_MAP);

        ResultMapReporterValue reporterValue = reporter.getSucceededResult();
        assertEquals(ResultType.SUCCEEDED, reporterValue.getResultType());
        assertEquals(1, reporterValue.getCount());

        verify(result).getRunCount();
        verifyNoMoreInteractions(result);
    }

    @Test
    public void getReport() throws IOException {
        ResultMapReporterImpl reporter = new ResultMapReporterImpl(result, RESULT_MAP);
        final String content = FileUtils.readFileToString(new File(getClass().getClassLoader()
                .getResource("result-map-reporter.txt").getFile()), "UTF-8");
        assertEquals(content, reporter.getReport());

        verifyZeroInteractions(result);
    }

}