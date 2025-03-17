package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.generator.basic.NumberBasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class IntegerGenerator extends Generator<Integer> {

    private final Random random = new Random();

    @Override
    public String name() {
        return "IntegerGenerator";
    }

    @Override
    public Integer next() {
        return NumberBasic.nextInt(args.min(), args.max());
    }

    @Override
    public List<Integer> next(int num) {
        WorkAssert.moreThan(num, 0).message("批量生产数量不正确：{}", num);

        List<Integer> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(next());
        }
        return result;
    }
}