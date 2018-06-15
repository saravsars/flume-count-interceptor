/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sars.flume.interceptor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

public class CountInterceptor implements Interceptor {
	private final AtomicLong counter;
	private final String headerName;
	public static final String HEADER_CONF_KEY = "headerName";
	public static final String DEFAULT_HEADER = "counter";

	public CountInterceptor(Context ctx) {
		this.headerName = ctx.getString(HEADER_CONF_KEY, DEFAULT_HEADER);
		this.counter = new AtomicLong(System.currentTimeMillis() * 10);
	}

	@Override
	public void close() {
		// no-op
	}

	@Override
	public void initialize() {
		// no-op
	}

	@Override
	public Event intercept(Event event) {
		long count = counter.incrementAndGet();
		Map<String, String> headers = event.getHeaders();
		headers.put(headerName, String.valueOf(count));
		return event;
	}

	@Override
	public List<Event> intercept(List<Event> events) {
		for (Event event : events) {
			intercept(event);
		}
		return events;
	}

	public static class Builder implements Interceptor.Builder {

		private Context ctx;

		@Override
		public void configure(Context context) {
			this.ctx = context;
		}

		@Override
		public Interceptor build() {
			return new CountInterceptor(ctx);
		}
	}
}
