package party.msdg.renova.database.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.Codes;
import party.msdg.renova.database.table.Table;
import party.msdg.renova.database.table.TableService;
import party.msdg.renova.mock.value.ValueConfig;
import party.msdg.renova.mock.value.ValueConfigService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ColumnService {

    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private ValueConfigService valueConfigService;
    @Autowired
    private TableService tableService;

    /**
     * 批量新增字段信息
     * @param sourceId  数据源
     * @param columns   字段
     */
    public void batchAdd(int sourceId, List<Column> columns) {
        // 0代表系统本身
        columnDao.batchInsert(sourceId, Codes.SYS_CUSER, columns);
    }

    /**
     * 数据表下的字段信息。包含字段基本信息和数据生成信息
     * @param sourceId      数据源id
     * @param tableName     表名
     */
    public List<Column> tableColumns(int sourceId, String tableName) {
        Table table = tableService.one(sourceId, tableName);
        List<Column> columns = columnDao.tableColumns(sourceId, tableName);
        List<ValueConfig> configs = valueConfigService.all(Codes.PARAM_TYPE_TABLE, table.getId());
        Map<Integer, ValueConfig> configMap = configs.stream().collect(Collectors.toMap(ValueConfig::getId, one -> one));

        columns.forEach(one -> one.setValueConfig(configMap.getOrDefault(one.getId(), null)));
        return columns;
    }
}
