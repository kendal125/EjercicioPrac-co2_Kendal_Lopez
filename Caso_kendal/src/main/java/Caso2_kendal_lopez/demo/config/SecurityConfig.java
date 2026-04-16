/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_kendal_lopez.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // CAMBIO AQUÍ: Agregamos "/img/**" para que las fotos sean visibles
                .requestMatchers("/login", "/css/**", "/js/**", "/img/**", "/error").permitAll()
                
                .requestMatchers("/usuarios/**", "/roles/**").hasAuthority("ADMIN")
                
                .requestMatchers("/eventos/nuevo", "/eventos/editar/**", "/eventos/eliminar/**")
                    .hasAnyAuthority("ADMIN", "ORGANIZADOR")
                    
                .requestMatchers("/eventos").hasAnyAuthority("ADMIN", "ORGANIZADOR", "CLIENTE")
                .requestMatchers("/consultas/**").hasAnyAuthority("ADMIN", "ORGANIZADOR", "CLIENTE")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String rol = authentication.getAuthorities().iterator().next().getAuthority();
            
            if (rol.equals("ADMIN")) {
                response.sendRedirect("/usuarios");
            } else if (rol.equals("ORGANIZADOR")) {
                response.sendRedirect("/eventos");
            } else {
                response.sendRedirect("/eventos");
            }
        };
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}