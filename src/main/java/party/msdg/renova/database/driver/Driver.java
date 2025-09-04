package party.msdg.renova.database.driver;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.Work;

/**
 * 驱动数据库工作，同数据库沟通的底层对象
 *
 * @author   Mr.Ten
 * @summary  数据库驱动
 * @since 2025/6/16 16:12
 */
@Component
public class Driver {

    /**
     * 测试数据源是否可以建立链接
     */
    public void tryConnect(DataSource dataSource) {
        SqlSessionFactory factory = createFactory(LoadMapping.class, dataSource);
        try (SqlSession session = factory.openSession()) {
            session.getConnection();
        } catch (Exception e) {
            throw Work.ex().message("无法与数据源建立链接。");
        }
    }

    /**
     * 手动创建sqlSession
     */
    <T> SqlSessionFactory createFactory(Class<T> tClass, javax.sql.DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("renova", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(tClass);
        return new SqlSessionFactoryBuilder().build(configuration);
    }
}
