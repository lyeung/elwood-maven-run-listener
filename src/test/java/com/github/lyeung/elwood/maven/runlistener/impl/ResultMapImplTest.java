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

import com.github.lyeung.elwood.maven.runlistener.enums.ResultType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by lyeung on 25/09/16.
 */
public class ResultMapImplTest {

    private static final Description DESC_1 = Description.createTestDescription(
            ResultMapImplTest.class, "sampleTest1()");

    private static final Description DESC_2 = Description.createTestDescription(
            ResultMapImplTest.class, "sampleTest2()");

    private static final Failure FAILURE_1 = new Failure(DESC_1, new NullPointerException("mocked exception"));

    private static final Failure FAILURE_2 = new Failure(DESC_2, new NullPointerException("mocked exception"));

    private ResultMapImpl resultMap;

    @Before
    public void setUp() {
        resultMap = new ResultMapImpl();
    }

    @Test
    public void addIgnored() {
        resultMap.addIgnored(DESC_1);
        resultMap.addIgnored(DESC_2);

        assertByResultType(ResultType.IGNORED);

        List<Integer> list = Arrays.asList(3,5,1,6,4);
        Collections.sort(list);
    }

    @Test
    public void addFailed() {
        resultMap.addFailed(FAILURE_1);
        resultMap.addFailed(FAILURE_2);

        assertByResultType(ResultType.FAILED);
    }

    @Test
    public void addSucceeded() {
        resultMap.addSucceeded(DESC_1);
        resultMap.addSucceeded(DESC_2);

        assertByResultType(ResultType.SUCCEEDED);
    }

    private void assertByResultType(ResultType resultType) {
        assertEquals(resultType, resultMap.getInternalMap().get(
                ResultMapImplTest.class.getCanonicalName() + "." + "sampleTest1()"));
        assertEquals(resultType, resultMap.getInternalMap().get(
                ResultMapImplTest.class.getCanonicalName() + "." + "sampleTest2()"));

        assertEquals(2, resultMap.getInternalMap().size());
    }
}