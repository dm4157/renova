package party.msdg.renova.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.Work;

import java.util.List;

@Component
public class Generators {

    @Autowired
    private List<Generator<?>> generators;

    /**
     * 根据名称获取生成器
     *
     * 此方法通过遍历预定义的生成器列表来查找与给定名称匹配的生成器
     * 如果找到匹配的生成器，则返回它；如果未找到，则抛出一个异常
     *
     * @param name 生成器的名称，用于唯一标识一个生成器
     * @return 返回找到的生成器对象
     * @throws party.msdg.renova.base.work.WorkException 如果没有找到指定名称的生成器，则抛出此异常
     */
    public Generator<?> getGenerator(String name, String args) {
        for (Generator<?> generator : generators) {
            if (generator.name().equals(name)) {
                generator.init(args);
                return generator;
            }
        }

        // 当找不到指定名称的生成器时，抛出一个含有详细信息的异常
        throw Work.ex().message("找不到名为{}的生成器", name);
    }

}
