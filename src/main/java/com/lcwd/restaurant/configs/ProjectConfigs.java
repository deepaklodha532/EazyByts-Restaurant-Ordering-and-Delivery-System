package com.lcwd.restaurant.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProjectConfigs {

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper() ;
    }

}
