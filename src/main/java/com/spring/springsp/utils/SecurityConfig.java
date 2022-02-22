package com.spring.springsp.utils;


import com.spring.springsp.service.UsuarioRepo;
import com.spring.springsp.service.UsuarioRepoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true) // Habilira @Secured
@EnableGlobalMethodSecurity(prePostEnabled = true) //Habilita @PreAuthorize
class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("JWT Filter")
    private JWTAuthFilter jwtAuthFilter;

    @Qualifier("UsuarioRepoImp")
    @Autowired
    private UsuarioRepo myService;

    //Configuracion del AuthManager
    //Seteo del AuthProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(((UsuarioRepoImp)myService));
    }

   @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .exceptionHandling()
               .and().sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
               .formLogin().loginPage("/login.html");

                //.antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                //.anyRequest().authenticated();
                //.addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //.antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**");
    }

    //Encargado de la validacion de contrasenias
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Argon2PasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
