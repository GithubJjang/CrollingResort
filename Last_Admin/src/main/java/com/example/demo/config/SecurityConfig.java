package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
	/*
	private final AdminServerProperties adminServer = new AdminServerProperties();


	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 로그인 성공 시 메인페이지로 redirect
        final SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        loginSuccessHandler.setTargetUrlParameter("redirectTo");
        loginSuccessHandler.setDefaultTargetUrl(this.adminServer.path("/"));
        http
            .authorizeHttpRequests(authorize -> {
			
					authorize.
							requestMatchers("/login","/assets/*").permitAll()
							.anyRequest().authenticated();
			})
            .formLogin(formLogin ->{
            	formLogin.loginPage(this.adminServer.path("/login")).successHandler(loginSuccessHandler);
            })
            .logout(logout ->{
            	logout.logoutUrl("/logout");
            })
            .csrf(csrf -> {
            	csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            	    .ignoringRequestMatchers(
                            this.adminServer.path("/instances"),
                            this.adminServer.path("/actuator/**")
                    );
            })
            ;
        return http.build();
        */
    }
