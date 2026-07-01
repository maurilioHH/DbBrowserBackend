package org.dbbrowser.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring") // Ci fermiamo a 'spring'
public class DatabaseProperties {

    // Il nome della variabile deve coincidere con il nodo dello YAML: 'datasource'
    private Map<String, DbConfig> datasource;

    public Map<String, DbConfig> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DbConfig> datasource) {
        this.datasource = datasource;
    }

    @Getter
    @Setter
    public static class DbConfig {
        private String url;
        private String jdbcUrl;
        private String username;
        private String password;
    }
}