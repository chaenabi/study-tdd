package study.tdd.simpleboard.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import study.tdd.simpleboard.api.member.entity.MemberRepository;
import study.tdd.simpleboard.config.JwtSecurityConfig;
import study.tdd.simpleboard.util.JWTTokenProvider;

/**
 * 보안 설정
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */

@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Security extends WebSecurityConfigurerAdapter {

    private final JWTTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/test/role_member").hasRole("USER")
//                .antMatchers("/test/role_admin").hasRole("ADMIN")
                .antMatchers("/").permitAll()
                .antMatchers("/posts/**").permitAll();


        http.csrf()
                .disable();

        http.headers()
                .frameOptions()
                .disable();

        http.formLogin()
                .disable();

        http.apply(new JwtSecurityConfig(jwtTokenProvider, memberRepository));

    }


}
