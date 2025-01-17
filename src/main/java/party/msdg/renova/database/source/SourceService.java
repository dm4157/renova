package party.msdg.renova.database.source;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.DataAuth;
import party.msdg.renova.base.work.WorkCode;
import party.msdg.renova.database.table.TablesMapping;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkAssert;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */
@Service
public class SourceService {

    @Autowired
    private SourceDao sourceDao;

    /**
     * 全部数据源，暂不分页
     */
    public List<Source> allSources(int userId) {
        return sourceDao.all(userId);
    }

    @DataAuth
    public Source one(int id) {
        Source source = sourceDao.one(id);
        WorkAssert.notNull(source).scene("id:{}", id).just(WorkCode.DB_SOURCE_NOT_FOUND);

        return source;
    }

    public void add(Source source) {
        testConnectDB(source.toDataSource());
        sourceDao.add(source);
    }
    
    /**
     * 测试数据源是否可以建立链接
     */
    private void testConnectDB(DataSource dataSource) {
        SqlSessionFactory factory = createFactory(TablesMapping.class, dataSource);
        try (SqlSession session = factory.openSession()) {
            session.getConnection();
        } catch (Exception e) {
            throw Work.ex().message("无法与数据源建立链接。");
        }
    }
    
    /**
     * 手动创建sqlSession
     */
    private <T> SqlSessionFactory createFactory(Class<T> tClass, DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("renova", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(tClass);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

}
