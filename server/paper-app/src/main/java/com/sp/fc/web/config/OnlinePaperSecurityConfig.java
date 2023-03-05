package com.sp.fc.web.config;

import com.sp.fc.user.service.UserSecurityService;
import com.sp.fc.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class OnlinePaperSecurityConfig {
    private final UserSecurityService userSecurityService;

    AuthenticationManager authenticationManager;

    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    private RememberMeServices rememberMeServices(){
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices(
                "paper-site-remember-me",
                userSecurityService
        );
        rememberMeServices.setParameter("remember-me");
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setTokenValiditySeconds(3600);
        return rememberMeServices;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder  authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
        final SpLoginFilter filter = new SpLoginFilter(
                authenticationManager,
                rememberMeServices()
        );


        http
                .csrf().disable()
                .formLogin(login->{
                    login.loginPage("/login")
                    ;
                })
                .logout(logout->{
                    logout.logoutSuccessUrl("/")
                    ;
                })
                .rememberMe(config->{
                    config.rememberMeServices(rememberMeServices())
                    ;
                })
                .addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception->{
                    exception.accessDeniedPage("/access-denied");
                })
                .authorizeHttpRequests(config->{
                    config
                            .requestMatchers("/").permitAll()
                            .requestMatchers("/login").permitAll()
                            .requestMatchers("/error").permitAll()
                            .requestMatchers("/signup/*").permitAll()
                            .requestMatchers("/study/**").hasAnyAuthority("ROLE_ADMIN","ROLE_STUDENT")
                            .requestMatchers("/teacher/**").hasAnyAuthority("ROLE_ADMIN","ROLE_TEACHER")
                            .requestMatchers("/manager/**").hasAnyAuthority("ROLE_ADMIN")
                            ;
                })
        ;
        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)->{
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        };
    }
}
