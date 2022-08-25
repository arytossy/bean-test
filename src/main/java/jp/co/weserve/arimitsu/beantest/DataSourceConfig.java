package jp.co.weserve.arimitsu.beantest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    ////////////////////////////////////////////////////////////////////////////////
    // ↓こっちはアプリの立ち上げ時にDB接続を確立しようとする。
    ////////////////////////////////////////////////////////////////////////////////

    // @Bean
    // @ConfigurationProperties("mydatasource.hikari")
    // public HikariConfig hikariConfig() {
    //     return new HikariConfig();
    // }

    // @Bean
    // public HikariDataSource dataSource(HikariConfig hikariConfig) {
    //     return new HikariDataSource(hikariConfig);
    // }

    ////////////////////////////////////////////////////////////////////////////////
    // ↓こっちはアプリの立ち上げ時はDB接続を確認しないみたい。
    ////////////////////////////////////////////////////////////////////////////////

    @Bean
    @Scope(scopeName = "properties", proxyMode = ScopedProxyMode.TARGET_CLASS)
    @ConfigurationProperties("mydatasource.hikari")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
