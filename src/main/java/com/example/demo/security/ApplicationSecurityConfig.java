package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.example.demo.security.ApplicationRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(
//                ApplicationPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(
//                ApplicationPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(
//                ApplicationPermission.COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(
//                ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                     .loginPage("/login").permitAll()
                     .defaultSuccessUrl("/courses", true)
                //.passwordParameter()
                //.usernameParameter()
                .and()
                .rememberMe() // default for two weeks
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                .key("somethingspecial")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID","remember-me" )
                    .logoutSuccessUrl("/login");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails student = User.builder()

                .username("ter-amos")
                .password(passwordEncoder.encode("password"))
                .authorities(STUDENT.getGrantedPermission())
                .build();
        UserDetails admin = User.builder()
                .username("Jerry-akin")
                .password(passwordEncoder.encode("password123"))
                .authorities(ADMIN.getGrantedPermission())
                .build();
        UserDetails adminTrainee = User.builder()
                .username("Felix")
                .password(passwordEncoder.encode("password123"))
                .authorities(ADMINTRAINEE.getGrantedPermission())
                .build();
        return new InMemoryUserDetailsManager(
                student,
                admin,
                adminTrainee
        );
    }
}
