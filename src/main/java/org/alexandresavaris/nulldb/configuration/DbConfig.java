package org.alexandresavaris.nulldb.configuration;

import org.alexandresavaris.nulldb.bean.DbBean;
import org.alexandresavaris.nulldb.service.PropertiesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

// For configuring on-disk and in-memory database structures.
@Configuration
public class DbConfig {
    // For accessing the application properties.
    @Autowired
    private PropertiesLoader propertiesLoader;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public DbBean getDbBean() throws IOException {

        return new DbBean(
            propertiesLoader.getDbPath(),
            propertiesLoader.getDbFileName()
        );
    }
}
