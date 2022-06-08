package intern.task4.authorization.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfigurer  {
    public static final String[] WHITE_LIST = {
            "/auth/login","/auth/register", "/auth/home","/auth/block-users"
    };

    public static final String[] WHITE_LIST_RESOURCES = {
            "/css/**", "/webjars/**", "/js/**", "/error", "/images/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .antMatchers(WHITE_LIST).permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer
                                .loginPage("/auth/login")
                                .defaultSuccessUrl("/auth/home", true)
                                .loginProcessingUrl("/auth/login")
                )
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer
                                .logoutUrl("/auth/logout")
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                );
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->web.ignoring().antMatchers(WHITE_LIST_RESOURCES);
    }


}