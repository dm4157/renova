package party.msdg.renova.mock.value;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueConfigDao {

    int batchAdd(int paramType, int paramRoot, List<ValueConfig> configs);

    /**
     * 查询所有值生成规则
     * @param paramType 字段类型：1-表字段，2-接口字段
     * @param paramRoot 字段根，数据表或者接口
     */
    List<ValueConfig> all(int paramType, int paramRoot);

    int delete(int paramType, int paramRoot);

    int update(int muser, int paramType, int paramRoot, int paramId,
               int genType, String generator, String genArgs, int pick, int dataSet);
}
