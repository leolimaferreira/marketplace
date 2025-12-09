package com.marketplace.config;

import com.marketplace.security.SecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.marketplace.utils.Constantes.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/autorizacao/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clientes").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, CLIENTES_URL).hasAnyRole(ADMIN, CLIENTE)
                        .requestMatchers(HttpMethod.PUT, CLIENTES_URL).hasAnyRole(ADMIN, CLIENTE)
                        .requestMatchers(HttpMethod.DELETE, CLIENTES_URL).hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/produtos").permitAll()
                        .requestMatchers(HttpMethod.GET, PRODUTOS_URL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.PUT, PRODUTOS_URL).hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.DELETE, PRODUTOS_URL).hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.GET, "/lojas").permitAll()
                        .requestMatchers(HttpMethod.GET, "/lojas/ativas").permitAll()
                        .requestMatchers(HttpMethod.POST, "/lojas/com-dono-existente").hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.POST, "/lojas/com-dono-novo").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/lojas/**").hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.POST, "/pedidos").hasRole(CLIENTE)
                        .requestMatchers(HttpMethod.GET, "/pedidos/cliente/**").hasAnyRole(ADMIN, VENDEDOR, CLIENTE)
                        .requestMatchers(HttpMethod.GET, "/pedidos/**").hasAnyRole(ADMIN, VENDEDOR, CLIENTE)
                        .requestMatchers(HttpMethod.PUT, "/pedidos/**").hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.GET, "/pagamentos/*/nota-fiscal").permitAll()
                        .requestMatchers(HttpMethod.POST, "/pagamentos").hasRole(CLIENTE)
                        .requestMatchers(HttpMethod.POST, "/pagamentos/*/simular-aprovacao").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/pagamentos/*/simular-rejeicao").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/pagamentos/cliente/**").hasAnyRole(ADMIN, VENDEDOR, CLIENTE)
                        .requestMatchers(HttpMethod.GET, "/pagamentos/**").hasAnyRole(ADMIN, VENDEDOR, CLIENTE)
                        .requestMatchers(HttpMethod.PUT, "/pagamentos/**").hasAnyRole(ADMIN, VENDEDOR)
                        .requestMatchers(HttpMethod.POST, "/itens-pedido").hasRole(CLIENTE)
                        .requestMatchers(HttpMethod.POST, "/enderecos/*").hasAnyRole(ADMIN, CLIENTE)
                        .requestMatchers(HttpMethod.GET, "/enderecos/*").hasAnyRole(ADMIN, CLIENTE)
                        .requestMatchers(HttpMethod.POST, "/avaliacoes").hasRole(CLIENTE)
                        .requestMatchers(HttpMethod.PUT, "/avaliacoes/**").hasRole(CLIENTE)
                        .requestMatchers(getDocumentationEndpoints()).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private String[] getDocumentationEndpoints() {
        return new String[]{
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/actuator/**"
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
