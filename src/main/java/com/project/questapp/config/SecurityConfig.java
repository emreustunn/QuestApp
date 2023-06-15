package com.project.questapp.config;

import com.project.questapp.security.JwtAuthenticationEntryPoint;
import com.project.questapp.security.JwtAuthenticationFilter;
import com.project.questapp.service.UserDetailsServiceImpl;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private UserDetailsServiceImpl userDetailsServiceImpl;
    private JwtAuthenticationEntryPoint handler;

    /**
     * tüm security işlemlerinin otomatik bir şekilde olmasını sağlamak için bir config classı
     * classını kullanacağız.
     */

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtAuthenticationEntryPoint handler) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.handler = handler;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * cors filter -> cross originden gelen istekler için bir filtreleme
     * gelen istekelere izin vereceğiz. otomatik olarak kullanacağız
     * serverimizin origini hariç farklı isteklere izin vermemiz.
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors() // yukarıda tanımlı course filteri otomatik olarak bulur ve ekler.
                .and()
                .csrf().disable() //bizim adımıza istek atılamaması için bu onemli normalde disable edilmemesi lazım postman için disable ettik
                .exceptionHandling().authenticationEntryPoint(handler).and() // hata olusursa bizim handleri kullan
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/posts")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/comments")
                .permitAll()
                .antMatchers("/auth/**") //herhangi biri girdiğinde bu linke erişsin token istemeyeceğiz.
                .permitAll()
                .anyRequest().authenticated(); // onun dışında diğerlerinin hepsini kapat.

        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }





}
