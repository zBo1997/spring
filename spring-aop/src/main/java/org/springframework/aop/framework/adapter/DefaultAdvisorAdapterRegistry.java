/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * AdvisorAdapterRegistry的实现类。也是SpringAOP中唯一默认的实现类。
 * 持有：MethodBeforeAdviceAdapter、AfterReturningAdviceAdapter、ThrowsAdviceAdapter实例。
 *
 * Default implementation of the {@link AdvisorAdapterRegistry} interface.
 * Supports {@link org.aopalliance.intercept.MethodInterceptor},
 * {@link org.springframework.aop.MethodBeforeAdvice},
 * {@link org.springframework.aop.AfterReturningAdvice},
 * {@link org.springframework.aop.ThrowsAdvice}.
 *
 * @author Rod Johnson
 * @author Rob Harrop
 * @author Juergen Hoeller
 */
@SuppressWarnings("serial")
public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry, Serializable {

	// 持有一个AdvisorAdapter的List，这个List中的adapter是与实现Spring AOP的advice增强功能相对应的
	private final List<AdvisorAdapter> adapters = new ArrayList<>(3);


	/**
	 * 此方法把已有的advice实现的adapter加入进来 方便后续匹配对应的MethodInterceptor
	 *
	 * 可是明明 总共有5哥Adapter 为什么只添加了 3个? 这是为什么？？？
	 *
	 *  因为要实现 {@link AdvisorAdapter} 这个接口要实现两个方法，具体去看这个类的。
	 *
	 *  这样做Spring是为了方便拓展，只要实现Adapter中的两个方法就可以了，给的是一个模板方法，
	 *  本质上MethodBeforeAdvice 就是BeforeAdvice
	 *  本质上MethodAfterAdvice 就是AfterAdvice
	 *  本质上MethodThrowAdvice 也是是AfterAdvice（这里Spring细化出来了）,
	 *  所以说Spring为了方便我们拓展，所以使用了适配器模式，按照接口的方式帮我们会把这几中MethodInterceptor
	 *  按照Adapter()适配器的方式进行类型划分，让我门更好的拓展
	 *
	 *  @see DefaultAdvisorAdapterRegistry#getInterceptors
	 *
	 * Create a new DefaultAdvisorAdapterRegistry, registering well-known adapters.
	 */
	public DefaultAdvisorAdapterRegistry() {
		registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
		registerAdvisorAdapter(new AfterReturningAdviceAdapter());
		registerAdvisorAdapter(new ThrowsAdviceAdapter());
	}


	@Override
	public Advisor wrap(Object adviceObject) throws UnknownAdviceTypeException {
		// 如果要封装的对象本身就是Advisor类型，那么无须做任何处理
		if (adviceObject instanceof Advisor) {
			return (Advisor) adviceObject;
		}
		// 如果类型不是Advisor和Advice两种类型的数据，那么将不能进行封装
		if (!(adviceObject instanceof Advice)) {
			throw new UnknownAdviceTypeException(adviceObject);
		}
		Advice advice = (Advice) adviceObject;
		if (advice instanceof MethodInterceptor) {
			// So well-known it doesn't even need an adapter.
			// 如果是MethodInterceptor类型则使用DefaultPointcutAdvisor封装
			return new DefaultPointcutAdvisor(advice);
		}
		// 如果存在Advisor的适配器那么也同样需要进行封装
		for (AdvisorAdapter adapter : this.adapters) {
			// Check that it is supported.
			if (adapter.supportsAdvice(advice)) {
				return new DefaultPointcutAdvisor(advice);
			}
		}
		throw new UnknownAdviceTypeException(advice);
	}

	// 将 Advisor转换为 MethodInterceptor
	@Override
	public MethodInterceptor[] getInterceptors(Advisor advisor) throws UnknownAdviceTypeException {
		List<MethodInterceptor> interceptors = new ArrayList<>(3);
		// 从Advisor中获取 Advice
		Advice advice = advisor.getAdvice();
		if (advice instanceof MethodInterceptor) {
			interceptors.add((MethodInterceptor) advice);
		}
		for (AdvisorAdapter adapter : this.adapters) {
			if (adapter.supportsAdvice(advice)) {
				// 转换为对应的 MethodInterceptor类型
				// AfterReturningAdviceInterceptor MethodBeforeAdviceInterceptor  ThrowsAdviceInterceptor
				interceptors.add(adapter.getInterceptor(advisor));
			}
		}
		if (interceptors.isEmpty()) {
			throw new UnknownAdviceTypeException(advisor.getAdvice());
		}
		return interceptors.toArray(new MethodInterceptor[0]);
	}

	// 新增的 Advisor适配器
	@Override
	public void registerAdvisorAdapter(AdvisorAdapter adapter) {
		this.adapters.add(adapter);
	}

}
