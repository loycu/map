package com.example.eurekaconsumer;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置注册（全局）
 * @author: fallsea
 * @version 1.0
 */
@Configuration
public class FeignSupportConfig {
	
	/**
	 * feign请求拦截器
	 * @author: fallsea
	 * @return
	 */
	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignBasicAuthRequestInterceptor();
	}

}
