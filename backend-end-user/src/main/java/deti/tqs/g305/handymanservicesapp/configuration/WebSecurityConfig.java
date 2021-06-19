package deti.tqs.g305.handymanservicesapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientBearerMatcher clientBearerMatcher;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .antMatchers("/api/*").hasAuthority("*").requestMatchers(clientBearerMatcher).permitAll()
                .anyRequest().denyAll();
    }
}