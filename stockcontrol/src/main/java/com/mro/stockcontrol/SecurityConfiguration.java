package com.mro.stockcontrol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mro.stockcontrol.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {	    
   
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) 
      throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
          .userDetailsService()
          .passwordEncoder(bCryptPasswordEncoder)
          .and()
          .build();
    }
    
    
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("12345"))
//                .roles("ADMINISTRADOR")
//            .and()
//                .withUser("user")
//                .password(passwordEncoder().encode("12345"))
//                .roles("USUARIO");
//    }
    
    
    
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/nota-entrada", "/nota-saida", "/estoque").hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
            )
            .httpBasic(basicAuthentication -> {
                basicAuthentication
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
            });
    }
}
