package uz.pdp.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    public SecurityConfigurer(CustomUserDetailsService userDetailsService, CustomAuthenticationFailureHandler authenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.userDetailsService(userDetailsService);
        http.authorizeRequests()
                .requestMatchers( "/css/**", "/auth/login","/auth/register").permitAll()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                .anyRequest()
                .fullyAuthenticated();

        http.formLogin()
                .loginPage("/auth/login")
                .usernameParameter("uname")
                .passwordParameter("pswd")
                .defaultSuccessUrl("/home",false)
                .failureHandler(authenticationFailureHandler);

        http.logout()
                .logoutUrl("/auth/logout")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout","POST"));

        http.rememberMe()
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rem-me")
                .tokenValiditySeconds(24 * 60 *60)
                .key("secret-key")
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
       // return NoOpPasswordEncoder.getInstance();   // do not use depreceted class
        return new BCryptPasswordEncoder();
    }

/*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder().username("hakim").password("123").roles("ADMIN").build();
        UserDetails user = User.withDefaultPasswordEncoder().username("jl").password("123").roles("USER").build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/
}
