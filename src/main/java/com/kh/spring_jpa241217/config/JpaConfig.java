package com.kh.spring_jpa241217.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // @CreatedDate, @ModifiedDate 활성화
}