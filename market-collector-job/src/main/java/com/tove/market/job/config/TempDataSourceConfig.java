package com.tove.market.job.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = TempDataSourceConfig.PACKAGE, sqlSessionFactoryRef = TempDataSourceConfig.SQL_SESSION_FACTORY_BEAN_NAME)
public class TempDataSourceConfig {
    /** 指定 Sql Session Factory 的 Bean 名称 */
    static final String SQL_SESSION_FACTORY_BEAN_NAME = "db2SqlSessionFactory";
    static final String DATASOURCE_BEAN_NAME = "db2DataSource";
    static final String DB_CONFIG_PREFIX = "datasource.db2";
    static final String DB_TRANSACTION_MANAFER_BEAN_NAME =  "db2TransactionManager";

    /** 指定 Mapper 类的包路径 */
    static final String PACKAGE = "com.tove.market.dao.temp";
    /** 指定数据库 Mapper 对应的 xml 文件路径 */
    static final String MAPPER = "classpath:mappers/db1/*.xml";

    /**
     * 配置数据源，这里设置为 hikari 数据库连接池
     * @return DataSource
     */
    //@Primary
    @Bean(name = DATASOURCE_BEAN_NAME)
    @ConfigurationProperties(DB_CONFIG_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    /**
     * 数据源事务管理器
     * @return 数据源事务管理器
     */
    //@Primary
    @Bean(name = DB_TRANSACTION_MANAFER_BEAN_NAME)
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier(DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //@Primary
    @Bean(name = SQL_SESSION_FACTORY_BEAN_NAME)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATASOURCE_BEAN_NAME) DataSource masterDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        //sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(TempDataSourceConfig.MAPPER));
        return sessionFactory.getObject();
    }
}
