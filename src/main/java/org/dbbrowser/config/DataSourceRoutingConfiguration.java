package org.dbbrowser.config;

import org.dbbrowser.service.impl.DbContextHolder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceRoutingConfiguration {

    private final DatabaseProperties databaseProperties;

    // Iniettiamo la tua classe che mappa lo YAML
    public DataSourceRoutingConfiguration(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        DataSource defaultDataSource = null;

        Map<String, DatabaseProperties.DbConfig> dataSourcesMap = databaseProperties.getDatasource();

        if (dataSourcesMap == null || dataSourcesMap.isEmpty()) {
            throw new IllegalStateException("Nessun database configurato nelle properties sotto 'spring.datasource'");
        }

        // Cicliamo dinamicamente sulla tua mappa
        for (Map.Entry<String, DatabaseProperties.DbConfig> entry : dataSourcesMap.entrySet()) {
            String dbKey = entry.getKey(); // es. "oracle-MS00529"
            DatabaseProperties.DbConfig config = entry.getValue();

            // Costruiamo il DataSource usando i tuoi campi
            DataSource dataSource = DataSourceBuilder.create()
                    .url(config.getJdbcUrl()) // o config.getUrl() in base a come risolvi il placeholder
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .driverClassName("oracle.jdbc.OracleDriver") // Fisso per Oracle o aggiungilo al tuo DbConfig
                    .build();

            targetDataSources.put(dbKey, dataSource);

            // Impostiamo il primo che trova come default di fallback
            if (defaultDataSource == null) {
                defaultDataSource = dataSource;
            }
        }

        // Creiamo il routing vero e proprio legato al ThreadLocal
        AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DbContextHolder.getCurrentDb();
            }
        };

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(defaultDataSource);

        return routingDataSource;
    }

    @Bean
    public JdbcClient jdbcClient(DataSource routingDataSource) {
        return JdbcClient.create(routingDataSource);
    }
}