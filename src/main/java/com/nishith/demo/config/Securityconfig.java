package com.nishith.demo.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Securityconfig
{
    @Autowired
    JwtFilter jwtfilter;
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.csrf(csrf->csrf.disable());

        http.authorizeHttpRequests(auth ->auth
                .requestMatchers("/movies").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/signup","/login").permitAll()
               // .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated());

//        http.formLogin(form->form.loginPage("/login")
//                                                                .successForwardUrl("/")
//                                                                .permitAll());


        // http.httpBasic(Customizer.withDefaults());
         http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })
        );
        return http.build();

    }
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails user1= User.withDefaultPasswordEncoder().username("nishith")
//                .password("n")
//                .roles("USER")
//                .build();
//        UserDetails user2= User.withDefaultPasswordEncoder().username("sai")
//                .password("s")
//                .roles("USER")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user1,user2);
//    }
    @Autowired
private UserDetailsService uds;
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(uds);
        return provider;
    }
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authconfig) throws Exception {
    return authconfig.getAuthenticationManager();
}

}
