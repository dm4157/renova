package party.msdg.renova.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * mybatis配置
 */
@Configuration
@MapperScan(basePackages = "party.msdg.renova", sqlSessionFactoryRef = "renovaFactory")
class MybatisConfig {

    @Bean(name = "renovaFactory")
    @Autowired
    public SqlSessionFactory renovaFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        // 设置mapper路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/**/*.xml"));

        // 设置别名解析基本包
        sessionFactoryBean.setTypeAliasesPackage("party.msdg.renova");
        return sessionFactoryBean.getObject();
    }

}
