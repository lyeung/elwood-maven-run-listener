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
import com.github.lyeung.elwood.maven.runlistener.ResultMapReporter;
import com.github.lyeung.elwood.maven.runlistener.ResultMapReporterValue;
import com.github.lyeung.elwood.maven.runlistener.enums.ResultType;
import org.junit.runner.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lyeung on 24/09/16.
 */
public class ResultMapReporterImpl implements ResultMapReporter {

    private final Result result;

    private final Map<ResultType, List<Map.Entry<String, ResultType>>> groupBy;

    public ResultMapReporterImpl(Result result, ResultMap resultMap) {
        this.result = result;
        groupBy = resultMap.getInternalMap().entrySet().stream()
                .collect(Collectors.groupingBy(e -> e.getValue()));
    }

    @Override
    public ResultMapReporterValue getIgnoredResult() {
        final int ignored = getCount(ResultType.IGNORED);
        if (ignored != result.getIgnoreCount()) {
            throw new IllegalArgumentException("expecting ignored count=["
                    + result.getIgnoreCount()
                    + " but was ["
                    + ignored + "]");
        }

        return new ResultMapReporterValue(ResultType.IGNORED, ignored, result.getIgnoreCount());
    }

    @Override
    public ResultMapReporterValue getFailedResult() {
        final int failed = getCount(ResultType.FAILED);
        if (failed != result.getFailureCount()) {
            throw new IllegalArgumentException("expecting failure count=["
                    + result.getFailureCount()
                    + "] but was ["
                    + failed + "]");
        }

        return new ResultMapReporterValue(ResultType.FAILED, failed, result.getFailureCount());
    }

    @Override
    public ResultMapReporterValue getSucceededResult() {
        final int success = getCount(ResultType.SUCCEEDED);
        final int expectedSuccess = result.getRunCount();
        if (success != expectedSuccess) {
            throw new IllegalArgumentException("expecting success count=["
                    + expectedSuccess
                    + "] but was ["
                    + success + "]");
        }

        return new ResultMapReporterValue(ResultType.SUCCEEDED, success, result.getRunCount());
    }

    @Override
    public String getReport() {
        return "success: " + getMethodNames(ResultType.SUCCEEDED) + "\n"
                + "failed: " + getMethodNames(ResultType.FAILED) + "\n"
                + "ignored: " + getMethodNames(ResultType.IGNORED);
    }

    private int getCount(ResultType status) {
        final List<Map.Entry<String, ResultType>> value = groupBy.get(status);

        if (value == null) {
            return 0;
        }

        return value.size();
    }

    private List<String> getMethodNames(ResultType status) {
        final List<Map.Entry<String, ResultType>> list = groupBy.get(status);

        if (list == null) {
            return new ArrayList<>();
        }

        return list.stream()
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

}
