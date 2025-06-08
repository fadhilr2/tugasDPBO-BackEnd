package com.dpbo.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dpbo.api.config.OtherConfig.RestAuthenticationEntryPoint;
import com.dpbo.api.model.User;
import com.dpbo.api.repository.UserRepository;
import com.dpbo.api.services.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailsServiceImpl userDetailsService;
	
	public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	@Autowired
	UserRepository userRepo;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// CONFIGURE AUTH PROVIDER WITH CUSTOM USERDETAILSSERVICE AND PASSWORD ENCODER
    @SuppressWarnings("deprecation")
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); 
        authProvider.setPasswordEncoder(passwordEncoder());          
        return authProvider;
    }
    
    // BUILD CUSTOM AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider()); 
        return authenticationManagerBuilder.build();
    }
    
    // CUSTOM UNAUTHORIZED REDIRECT
    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    
    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception{
    	http.csrf(csrf -> csrf.disable())
    	.anonymous().disable()
    	// AUTHORIZED URL
    	.authorizeHttpRequests(requests -> requests
    			.requestMatchers("/api/auth/check-session").authenticated()
    			.anyRequest().permitAll()
    			)
    	// BUILT IN LOGIN HANDLER
    	.formLogin(formLogin -> formLogin
    			.loginProcessingUrl("/api/auth/signin")
    			/* x-www-form-urlencoded
    			 * ----- payload -----
    			 * phoneNumber: phoneNumber
    			 * password
    			 */
    			.usernameParameter("phoneNumber")
    			.passwordParameter("password")
                // API successful login response
    			.successHandler((request, response, authentication) -> {
    				// 200 
                    response.setStatus(HttpServletResponse.SC_OK); 
                    response.setContentType("application/json");
                    User user =  userRepo.findByPhoneNumber(authentication.getName());
                    String jsonResponse = String.format("{\"message\": \"Login successful\", \"user\": \"%s\"}", user);
                    response.getWriter().write(jsonResponse);
                    response.getWriter().flush();
    			})
                // API failed login response
    			.failureHandler((request, response, exception) -> {
    				// 401
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
                    response.setContentType("application/json");
                    String jsonResponse = String.format("{\"error\": \"Authentication failed\", \"message\": \"%s\"}", exception.getMessage());
                    response.getWriter().write(jsonResponse);
                    response.getWriter().flush();
    			}))
    	// CUSTOM UNAUTHORIZED EXCEPTION HANDLER
    	.exceptionHandling(exception -> {
    		exception.authenticationEntryPoint(authenticationEntryPoint);
    	})
    	//BUILT IN LOGOUT HANDLER
    	.logout(logout -> logout
    			.logoutUrl("/api/auth/signout")
                // API successful successful response
    			.logoutSuccessHandler((request, response, exception) ->{
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"message\": \"Logout successful\"}");
                    response.getWriter().flush();
    			})
    			.invalidateHttpSession(true)
    			.deleteCookies("JSESSIONID"))
    	//BUILT IN SESSION MANAGEMENT
    	.sessionManagement(session -> session
    			.sessionFixation(fixation -> 
    			fixation.migrateSession())
    			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
    			.maximumSessions(1)
    			.expiredUrl("/api/auth/session-expired"));
    	return http.build();
    }
    
}
