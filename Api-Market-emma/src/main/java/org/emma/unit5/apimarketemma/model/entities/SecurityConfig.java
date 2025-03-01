package org.emma.unit5.apimarketemma.model.entities;

import org.emma.unit5.apimarketemma.model.dao.ISellersDAO;
import org.emma.unit5.apimarketemma.service.SellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ISellersDAO sellersDAO;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return SellersService.encryptPassword(rawPassword.toString()).toUpperCase();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return cif -> {
            Seller seller = sellersDAO.findByCif(cif)
                    .orElseThrow(() -> new UsernameNotFoundException("Seller cif not found"));

            return User.withUsername(seller.getCif())
                    .password(seller.getPassword())
                    .roles("SELLER") // not really necessary
                    //.passwordEncoder(passwordEncoder::encode) // uncomment this line if password is not stored encoded
                    .build();
        };
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel( channel -> channel.anyRequest().requiresSecure() )
                .authorizeHttpRequests(auth -> auth
                        // free access to the REST API
                        .requestMatchers("/").permitAll()
                        // free access to the styles.css file
                        .requestMatchers("/style.css").permitAll()
                        // free access to login page
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/create-offer").hasRole("SELLER")
                        .requestMatchers("/addproduct").hasRole("SELLER")
                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/login")
                        .defaultSuccessUrl("/welcome", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                /**.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            String contextPath = request.getContextPath();
                            response.sendRedirect(contextPath + "/login?logout");
                        })
                )**/
                .httpBasic(withDefaults());

        return http.build();
    }

}
