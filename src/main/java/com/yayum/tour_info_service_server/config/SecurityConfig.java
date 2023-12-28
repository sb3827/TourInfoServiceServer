package com.yayum.tour_info_service_server.config;

import com.yayum.tour_info_service_server.config.jwt.JwtAuthenticationEntryPoint;
import com.yayum.tour_info_service_server.config.jwt.TokenProvider;
import com.yayum.tour_info_service_server.security.filter.JwtFilter;
import com.yayum.tour_info_service_server.security.handler.JwtAccessDeniedHandler;
import com.yayum.tour_info_service_server.security.handler.OAuth2SuccessHandler;
import com.yayum.tour_info_service_server.security.service.OAuth2MemberDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsConfig corsConfig;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuth2MemberDetailsService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Bean
    protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                //cors 설정
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfig.corsConfigurationSource()))

                // setting exception handler
                .exceptionHandling(exceptionHandlingConfigurer ->
                        exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )

                // setting authorize of address
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/auth/getTest", "/auth/logout").authenticated();
                    authorizeRequests.anyRequest().permitAll();
                })

                .oauth2Login(OAuth2LoginConfigurer ->
                        OAuth2LoginConfigurer
                                .successHandler(successHandler)
//                                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserService))
                )

                // 세션 state less
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // token filter를 filter chain 추가
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    // jwt filter bean
    public JwtFilter jwtFilter(){
        return new JwtFilter(tokenProvider);
    }

    @Bean
    // authentication bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    // password encoder bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
