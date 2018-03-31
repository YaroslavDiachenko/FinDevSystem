package findev.config;

import findev.repository.IRepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = IRepositoryUser.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired private UserDetailsService userDetailsService;

    @Autowired private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired private MySavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }

/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN")
                .and()
                .withUser("user").password("user").roles("USER");
    }
*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().authorizeRequests().anyRequest().authenticated()
//                .antMatchers("/employees").hasRole("ADMIN")
//                .antMatchers("/events").hasRole("USER")
                .and().formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();
    }

    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler(){
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
        return new SimpleUrlAuthenticationFailureHandler();
    }
}