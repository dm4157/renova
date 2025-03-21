package party.msdg.renova.database.driver;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.base.work.WorkCode;
import party.msdg.renova.base.work.WorkLog;
import party.msdg.renova.database.column.Column;
import party.msdg.renova.database.column.ColumnService;
import party.msdg.renova.database.source.Source;
import party.msdg.renova.database.source.SourceService;
import party.msdg.renova.database.table.Table;
import party.msdg.renova.database.table.TableService;

import java.util.Collections;
import java.util.List;

/**
 * 数据库元信息加载，数据库信息、表信息、字段信息
 *
 * @author   Mr.Ten
 * @summary  数据库加载器
 * @since 2025/2/20 10:37
 */
@Slf4j
@Service
public class Loader {

    private static final String TAG = "DBLoader";
    private final WorkLog logger = Work.getLogger(Loader.class);

    @Autowired
    private Driver driver;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private TableService tableService;

    @Autowired
    private ColumnService columnService;

    /**
     * 刷新
     * 加载并存储数据源结构信息
     * 会清除目前保存的信息
     */
    public void loadAll() {
        long start = System.currentTimeMillis();
        logger.tag(TAG).info("加载所有数据源信息 开始。");
        sourceService.all(-1).forEach(this::loadDB);
        logger.tag(TAG).info("加载所有数据源信息 结束。 {}ms", System.currentTimeMillis() - start);
    }

    void loadOne(Source source) {
        long start = System.currentTimeMillis();
        logger.tag(TAG).info("begin load one data..");
        loadDB(source);
        logger.tag(TAG).info("end load one data. {}ms", System.currentTimeMillis() - start);
    }

    /**
     * 加载数据源信息
     */
    private void loadDB(Source source) {
        logger.tag("loadDB").debug("load db {} ...", source.getName());
        long start = System.currentTimeMillis();

        List<Table> tables = loadTable(source);
        tableService.batchAdd(source.getId(), tables);
        logger.tag("loadDB").debug("load table {}", tables.size());

        List<Column> columns = loadColumn(source);
        columnService.batchAdd(source.getId(), columns);
        logger.tag("loadDB").debug("load column {}", columns.size());

        long end = System.currentTimeMillis();
        logger.tag("loadDB").debug("load db {} consuming {}s.", source.getName(), (end - start)/1000 );
    }

    /**
     * 加载表信息
     */
    private List<Table> loadTable(Source source) {
        SqlSessionFactory factory = driver.createFactory(LoadMapping.class, source.toDataSource());
        try (SqlSession session = factory.openSession()) {
            LoadMapping loadMapping = session.getMapper(LoadMapping.class);

            if (Source.TYPE_MYSQL.equals(source.getType())) {
                return loadMapping.allTablesForMysql(source.getDbname());
            } else {
                throw Work.ex().scene("srcId={}, type={}", source.getId(), source.getType()).message("未知的数据库类型");
            }
        } catch (Exception e) {
            logger.tag("load column").stackPart().error(e);
            return Collections.emptyList();
        }
    }

    /**
     * 加载数据源所有字段信息
     */
    private List<Column> loadColumn(Source source) {
        SqlSessionFactory factory = driver.createFactory(LoadMapping.class, source.toDataSource());
        try (SqlSession session = factory.openSession()) {
            LoadMapping loadMapping = session.getMapper(LoadMapping.class);
            if (Source.TYPE_MYSQL.equals(source.getType())) {
                return loadMapping.allColumnsForMysql(source.getDbname());
            } else {
                throw Work.ex().scene("srcId={}, type={}", source.getId(), source.getType()).message("未知的数据库类型");
            }
        } catch (Exception e) {
            logger.tag("load column").stackPart().error(e);
            return Collections.emptyList();
        }
    }

}
