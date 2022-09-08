package volk.gantt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // Pages who do not require login
        http.authorizeHttpRequests().antMatchers("/", "/signup", "/login", "/logout").permitAll();
        // TODO: http.authorizeHttpRequests().antMatchers("/userInfo").access("hasRole(" + Role.ROLE_USER + ")");

        // For ADMIN only
        // TODO: http.authorizeHttpRequests().antMatchers("/admin").access("hasRole(" + Role.ROLE_ADMIN + ")");

        // When the user has logged in as XX
        // But access a page that requires role YY
        // AccessDeniedException will be thrown
        http.authorizeHttpRequests().and().exceptionHandling().accessDeniedPage("/403");

        // FORM Login config
        http.authorizeHttpRequests().and().formLogin()
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/login")
                .defaultSuccessUrl("/userInfo")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password");

        // Logout Config
        http.authorizeHttpRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

        // Spring Social Config
        // TODO:

    }
    
    // This method load the user specific data when the form login is used
    @Override
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
