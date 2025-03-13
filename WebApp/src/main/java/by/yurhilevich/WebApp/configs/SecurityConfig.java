package by.yurhilevich.WebApp.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers( "/", "/signup", "/login", "/signup1", "images/security.png","/current-role").permitAll()  // доступ для всех к этим страницам
                        .requestMatchers("/analyst/**").hasRole("ANALYST")  // только для пользователей с ролью Analyst
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // только для пользователей с ролью ADMIN
                        .requestMatchers("/director/**").hasRole("DIRECTOR")  // только для пользователей с ролью DIRECTOR
                        .anyRequest().authenticated()  // все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login")  // путь к кастомной странице входа
                        .loginProcessingUrl("/login_processing")  // URL, по которому отправляются данные формы
                        .defaultSuccessUrl("/", true)  // куда перенаправлять при успешном входе
                        .failureUrl("/login?error=true")  // куда перенаправлять при неудачном входе
                        .usernameParameter("username")  // параметр для имени пользователя
                        .passwordParameter("password")  // параметр для пароля
                        .permitAll()  // доступ к странице логина для всех
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                );

        return http.build();
    }

}
