package io.github.followsclosley.brick.jpa.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(value = "bricks")
public class NativeQueries {
    private Map<String,QueryDetails> queries;

    public Map<String, QueryDetails> getQueries() {
        return queries;
    }

    public void setQueries(Map<String, QueryDetails> queries) {
        this.queries = queries;
    }

    public static class QueryDetails{
        private String sql;
        private String[] columns;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public void setColumns(String columns) {
            this.columns = columns.split(",");
        }
        public String[] getColumnNames(){
            return columns;
        }
    }
}
