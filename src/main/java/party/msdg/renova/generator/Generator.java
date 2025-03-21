package party.msdg.renova.generator;

import party.msdg.renova.base.work.WorkAssert;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成器接口，描述生成器的行为
 *
 * @author Mr.Ten
 * @summary 生成器
 * @since 2025/3/13 15:34
 */
public abstract class Generator<T> {

    protected Arguments args;

    public void init(String args) {
        this.args = new Arguments(args);
    }


    /**
     * 生成器名称
     */
    public abstract String name();

    public abstract T next();

    public List<T> next(int num) {
        WorkAssert.moreThan(num, 0).message("批量生产数量不正确：{}", num);

        List<T> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(next());
        }
        return result;
    }
}
