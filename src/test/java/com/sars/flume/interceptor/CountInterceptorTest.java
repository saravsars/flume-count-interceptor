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

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.Interceptor;
import org.junit.Assert;
import org.junit.Test;

public class CountInterceptorTest {

	private static final String EVENT_BODY = "test event";

	@Test
	public void testBasic() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Context ctx = new Context();
		Interceptor.Builder builder = CountInterceptor.Builder.class.newInstance();
		builder.configure(ctx);
		Interceptor interceptor = builder.build();
		Event event = EventBuilder.withBody(EVENT_BODY.getBytes());
		Assert.assertNull(event.getHeaders().get(CountInterceptor.DEFAULT_HEADER));

		event = interceptor.intercept(event);
		String timestampStr = event.getHeaders().get(CountInterceptor.DEFAULT_HEADER);
		Assert.assertNotNull(timestampStr);

		Long now = System.currentTimeMillis() * 100;
		Assert.assertTrue(Long.parseLong(timestampStr) <= now);
	}

	@Test
	public void testCustomHeader() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		final String CUSTOM_HEADER = "event_counter";
		Context ctx = new Context();
		ctx.put(CountInterceptor.HEADER_CONF_KEY, CUSTOM_HEADER);
		Interceptor.Builder builder = CountInterceptor.Builder.class.newInstance();
		builder.configure(ctx);
		Interceptor interceptor = builder.build();
		Event event = EventBuilder.withBody(EVENT_BODY.getBytes());
		Assert.assertNull(event.getHeaders().get(CUSTOM_HEADER));

		event = interceptor.intercept(event);
		String timestampStr = event.getHeaders().get(CUSTOM_HEADER);
		Assert.assertNotNull(timestampStr);

		Long now = System.currentTimeMillis() * 100;
		Assert.assertTrue(Long.parseLong(timestampStr) <= now);
	}

}
