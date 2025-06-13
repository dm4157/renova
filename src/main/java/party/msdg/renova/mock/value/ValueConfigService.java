package party.msdg.renova.mock.value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueConfigService {

    @Autowired
    private ValueConfigDao valueConfigDao;

    /**
     * 查询所有字段生成规则配置
     * @param paramType 字段类型，1-数据表字段，2-接口字段
     * @param paramRoot 字段根，表id或接口id
     */
    public List<ValueConfig> all(int paramType, int paramRoot) {
        return valueConfigDao.all(paramType, paramRoot);
    }

}