package com.nttdata.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nttdata.persistence.model.Role;
import com.nttdata.services.UserManagementI;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(PasswordConfig.class)
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UserManagementI userService;
	private final PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("/v1/users/saveUser").permitAll()
						.requestMatchers(HttpMethod.GET, "/v1/usersBooks/**").hasAuthority(Role.USER.toString())
						.requestMatchers(HttpMethod.PUT, "/v1/usersBooks/**").hasAuthority(Role.USER.toString())
						.requestMatchers(HttpMethod.GET, "/v1/genres").hasAuthority(Role.USER.toString())
						.requestMatchers(HttpMethod.GET, "/v1/status").hasAuthority(Role.USER.toString())
						.requestMatchers(HttpMethod.GET, "/v1/books/**").hasAuthority(Role.USER.toString())
						.requestMatchers(HttpMethod.POST, "/v1/books/**").hasAuthority(Role.USER.toString())
						.requestMatchers(HttpMethod.DELETE, "/v1/books/**").hasAuthority(Role.USER.toString())
						.anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.cors(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService.userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

//	 @Override
//	    protected void configure(HttpSecurity http) throws Exception {
//	        http
//	            .authorizeRequests()
//	            .antMatchers("/v1/users/login").permitAll() // Permitir acceso anónimo al login
//	            .anyRequest().authenticated()
//	            .and()
//	            .httpBasic();
//	    }
}
