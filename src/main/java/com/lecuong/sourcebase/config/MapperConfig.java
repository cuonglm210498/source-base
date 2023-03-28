package com.lecuong.sourcebase.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:33 AM
 * @project source-base
 */
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getMapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
