package com.naim.recruitingapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages={"com.naim.recruitingapp.repository"})
public class JpaConfiguration {

}