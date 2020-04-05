package com.homework.security;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// 授权管理，permitAll()为不需要任何权限，即游客即可访问。.authenticated()则为必须登录才可访问。
				.authorizeRequests()
				// antMatchers和regexMatchers是两种不同的字符串匹配方法，可搭配使用
//				.regexMatchers(HttpMethod.GET, "/").permitAll()// 获取主页
				.anyRequest().permitAll()// 剩下的url一律不需要任何权限

				// 认证管理
				.and().formLogin().loginPage("/")
				.loginProcessingUrl("/authentication/authenticate").successHandler(myAuthenticationSuccessHandler)
				.failureHandler(myAuthenticationFailureHandler)

				// 退出管理
				.and().logout().logoutUrl("/authentication/logout").logoutSuccessUrl("/").deleteCookies("JSESSIONID")
				.and().csrf().disable();
	}

	// 用于注册后自动登录
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

}
