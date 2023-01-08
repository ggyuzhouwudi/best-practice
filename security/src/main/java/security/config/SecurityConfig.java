package security.config;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.filter.CustomUsernamePasswordAuthenticationFilter;
import security.handler.CustomAuthenticationFailureHandler;
import security.handler.CustomAuthenticationSuccessHandler;
import security.handler.CustomLogoutSuccessHandler;
import security.metasource.CustomSecurityMetadataSource;
import security.module.service.CustomUserDetailService;
import security.module.service.SecurityPersistentTokenBasedRememberMeServices;

/**
 * @author Oliver
 * @date 2022年12月29日 17:53
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService customUserDetailService;
    private final CustomSecurityMetadataSource customSecurityMetadataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter()
        throws Exception {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter();
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(
            authenticationManagerBean());
        //这里设置的是认证成功后使用记住我默认实现，用于将认证后的用户信息写会客户端
        customUsernamePasswordAuthenticationFilter.setRememberMeServices(rememberMeServices());
        customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(
            new CustomAuthenticationSuccessHandler());
        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(
            new CustomAuthenticationFailureHandler());
        return customUsernamePasswordAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 获取工厂对象
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        // 设置自定义url权限处理
        http.apply(new UrlAuthorizationConfigurer<>(applicationContext))
            .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                    //是否拒绝公共资源访问
                    object.setRejectPublicInvocations(false);
                    object.setSecurityMetadataSource(customSecurityMetadataSource);
                    return object;
                }
            });
        //所有请求必须认证
        http.formLogin()
            //这里设置是携带了记住我的cookie进行验证的记住我实现
            .and().rememberMe().rememberMeServices(rememberMeServices())
            .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.DELETE.name()))
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .and()
            .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        //替换filter
        http.addFilterAt(customUsernamePasswordAuthenticationFilter(),
            UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        // todo 持久化token信息
        return new SecurityPersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(),
            userDetailsService(), new InMemoryTokenRepositoryImpl());
    }

}
