package com.oolaa.TiresCRM.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		  MvcRequestMatcher h2RequestMatcher = new MvcRequestMatcher(introspector, "/**");
		  //h2RequestMatcher.setServletPath("/h2-console/");
		  http.csrf(csrf -> csrf.disable()).cors().and().headers((headers) ->
						  headers
								  .frameOptions((frameOptions) -> frameOptions.disable())
				  )

				  .authorizeHttpRequests((requests) ->
						  requests
								  .requestMatchers(PathRequest.toH2Console()).permitAll()
								  .anyRequest().authenticated()
				  ).httpBasic();
	    return http.build();
	  }
	  
//	    @Bean
//	    public WebSecurityCustomizer webSecurityCustomizer() {
//	        return (web) -> web.ignoring().requestMatchers("/h2-console/**", "/ignore2");
//	    }
	  
//	    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	    }
//	  
//	    @Bean
//	    public InMemoryUserDetailsManager userDetailsService() {
//	        UserDetails user = User.withDefaultPasswordEncoder()
//	            .username("user")
//	            .password("password")
//	            .roles("USER")
//	            .build();
//	        return new InMemoryUserDetailsManager(user);
//	    }
}
