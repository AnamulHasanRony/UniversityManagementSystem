package com.example.UniversityManagementSystem.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws  Exception{


        http.csrf().disable();

                http.authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers(
                                "/assets/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**",
                                "/fonts/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/teacher/**").hasRole("TEACHER")
                        .anyRequest().permitAll());
        http.formLogin(form->form.loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
        );
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails= User.withUsername("admin")
//                .username("admin")
//                .password("{noop}123")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;

    }


}
