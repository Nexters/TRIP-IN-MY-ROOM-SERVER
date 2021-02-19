package com.trip.my.room.server.config

import com.trip.my.room.server.common.filter.CorsFilter
import com.trip.my.room.server.user.jwt.filter.IfTokenAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.session.SessionManagementFilter


@Configuration
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Bean
    fun IfCorsFilter(): CorsFilter = CorsFilter()

    // 참고 :  https://godekdls.github.io/Spring%20Security/kotlinconfiguration/#171-httpsecurity
    override fun configure(http: HttpSecurity) {

        // 참고 : https://velog.io/@tlatldms/Spring-boot-Spring-security-JWT-Redis-mySQL-2%ED%8E%B8
        // Disable CSRF (cross site request forgery) - API 서버이므로 csrf 보안이 필요 없음
        http.csrf().disable()
        // REST api 이므로 기본 설정 사용 안함(설정할 경우, 비인증시 로그인폼 화면으로 리다이렉트됨)
        http.httpBasic().disable()
        // 폼 기반 인증 비활성화
        http.formLogin().disable()
        // No session will be created or used by spring security - JWT 사용으로 세션을 사용하지 않도록 설정
        http.sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))


        // 다음 리퀘스트에 대한 사용 권한 체크
        http.authorizeRequests()
//				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers("/users/token/refresh/**").permitAll()
            .antMatchers("/stories/**").authenticated()
            .antMatchers("/countries/**").authenticated()
            .antMatchers("/users/**").authenticated()

        // 토큰 인증 필터 추가
        http.addFilterBefore(getIfTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity) {
        super.configure(web)

        web.ignoring()
            .antMatchers("/users/login/**")
            .antMatchers("/users/page")
            .antMatchers("/h2-console/**")
    }

    fun getIfTokenAuthenticationFilter(): IfTokenAuthenticationFilter {
        return IfTokenAuthenticationFilter()
    }

    fun otherConfigurations(http: HttpSecurity) {
        http.addFilterBefore(IfCorsFilter(), SessionManagementFilter::class.java)
    }


}