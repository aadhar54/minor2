package com.aadhar.minor.config;

import com.aadhar.minor.services.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommonConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth.admin}")
    private String ADMIN_AUTHORITY;

    @Value("${auth.student}")
    private String STUDENT_AUTHORITY;

    @Value("${auth.bookviwer}")
    private String BOOK_VIEWER_AUTHORITY;


    @Autowired
    private MyUserService myUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/admin/**").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers(HttpMethod.POST,"/student").permitAll()
                .antMatchers("/student/id/**").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers("/student/**").hasAuthority(STUDENT_AUTHORITY)
                .antMatchers(HttpMethod.POST,"/book/**").hasAuthority(ADMIN_AUTHORITY)
                .antMatchers("/book/**").hasAuthority(BOOK_VIEWER_AUTHORITY)
                .antMatchers("/hello/**").permitAll()
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }


    public LettuceConnectionFactory getRedisFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(
                "redis-16627.c93.us-east-1-3.ec2.cloud.redislabs.com",16627
        );
        redisStandaloneConfiguration.setPassword("BVuQU07zIJPe8t6PqxSNrelyiDmckuke");
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> getTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(getRedisFactory());

        return redisTemplate;
    }

}
