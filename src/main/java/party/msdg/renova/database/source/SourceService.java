package party.msdg.renova.database.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.DataAuth;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkCode;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.database.driver.Driver;
import party.msdg.renova.toolkit.Trick;

import java.util.List;

/**
 * Created by Administrator on 2017/5/28.
 */
@Service
public class SourceService {

    @Autowired
    private SourceDao sourceDao;

    @Autowired
    private Driver driver;

    /**
     * 查询数据源清单
     * @param creatUserId   创建用户id。 -1表示查询全部信息
     */
    public List<Source> all(int creatUserId) {
        return sourceDao.all(creatUserId);
    }


    @DataAuth
    public Source one(int id) {
        Source source = sourceDao.one(id);
        WorkAssert.notNull(source).scene("id:{}", id).just(WorkCode.DB_SOURCE_NOT_FOUND);
        return source;
    }

    /**
     * 新增数据源
     */
    public void add(Source source) {
        // 测试数据源可用性
        driver.tryConnect(source.toDataSource());

        sourceDao.add(source);
    }

}
