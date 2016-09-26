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
import com.github.lyeung.elwood.maven.runlistener.enums.ResultType;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyeung on 24/09/16.
 */
public class ResultMapImpl implements ResultMap {

    private final Map<String, ResultType> map = new HashMap<>();

    @Override
    public void addIgnored(Description description) {
        map.put(getCanonicalName(description), ResultType.IGNORED);
    }

    @Override
    public void addFailed(Failure failure) {
        map.put(getCanonicalName(failure.getDescription()), ResultType.FAILED);
    }

    @Override
    public void addSucceeded(Description description) {
        map.put(getCanonicalName(description), ResultType.SUCCEEDED);
    }

    private String getCanonicalName(Description description) {
        return description.getTestClass().getCanonicalName() + "." + description.getMethodName();
    }

    @Override
    public Map<String, ResultType> getInternalMap() {
        return new HashMap<>(map);
    }
}
