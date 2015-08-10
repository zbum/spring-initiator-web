/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zbum.example.springweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring Java Configuration
 *
 * @author Jibeom Jung
 */
@Configuration
@ComponentScan(basePackages = "com.zbum.example.springweb",
    excludeFilters = @ComponentScan.Filter(value=Controller.class, type= FilterType.ANNOTATION))
@EnableJpaRepositories(basePackages = "com.zbum.example.springweb.repository",
        transactionManagerRef = "jpaTransactionManager",
        entityManagerFactoryRef = "entityManagerFactory")
public class AppConfig {
    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("/database/h2/schema.sql")
                .addScript("/database/h2/insert.sql")
                .build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setJpaProperties(jpaProperties());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.zbum.example.springweb.repository");
        localContainerEntityManagerFactoryBean.afterPropertiesSet();

        return localContainerEntityManagerFactoryBean.getObject();
    }

    private Properties jpaProperties(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.connection.pool_size", "1");
        properties.setProperty("hibernate.connection.shutdown", "true");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "validate");

        return properties;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        return jpaTransactionManager;
    }


    /*

    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"></property>
    </bean>
<bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter" />
        </property>
        <property name="dataSource" ref="dataSource"/>
        <property name="jpaProperties">
            <props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
                <prop key="hibernate.connection.pool_size">1</prop>
                <prop key="hibernate.connection.shutdown">true</prop> <!-- hsql에 있는 마지막 연결이 끊어지면 데이터베이스 shutdown 하는 플래그 -->
                <prop key="hibernate.show_sql">true</prop> <!-- SQL 출력 -->
                <prop key="hibernate.hbm2ddl.auto">validate</prop> <!-- 테이블 자동 생성 -->
            </props>
        </property>
        <!-- 엔티티 정의된 클래스들이 있는 패키지 등록 -->
        <!-- property name="packagesToScan" value="com.zbum.manty.engine.jpa.domain"/ -->
        <property name="packagesToScan" value="com.zbum.manty.engine.jpa.domain"/>

    </bean>

     */

}
