package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.generator.basic.NumberBasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DoubleGenerator extends Generator<Double> {

    private final Random random;

    public DoubleGenerator() {
        this.random = new Random();
    }

    @Override
    public String name() {
        return "DoubleGenerator";
    }

    @Override
    public Double next() {
        return NumberBasic.nextDouble(args.min(), args.max(), args.prec());
    }

    @Override
    public List<Double> next(int num) {
        WorkAssert.moreThan(num, 0).message("批量生产数量不正确：{}", num);

        List<Double> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(next());
        }
        return result;
    }
}