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

import com.github.lyeung.elwood.maven.runlistener.enums.ResultType;

/**
 * Created by lyeung on 24/09/16.
 */
public class ResultMapReporterValue {

    private final ResultType resultType;
    private final int count;
    private final int resultCount;

    public ResultMapReporterValue(ResultType resultType, int count, int resultCount) {
        this.resultType = resultType;
        this.count = count;
        this.resultCount = resultCount;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public int getCount() {
        return count;
    }

    public int getResultCount() {
        return resultCount;
    }
}
