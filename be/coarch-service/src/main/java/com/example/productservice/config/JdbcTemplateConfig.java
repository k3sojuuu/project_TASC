package com.example.productservice.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@NoArgsConstructor
@ConditionalOnMissingBean(JdbcOperations.class)
public class JdbcTemplateConfig {
    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate(DataSource dataSource, JdbcProperties properties){
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        JdbcProperties.Template template=properties.getTemplate();
        jdbcTemplate.setFetchSize(template.getFetchSize());
        jdbcTemplate.setMaxRows(template.getMaxRows());
        return jdbcTemplate;
    }
}
