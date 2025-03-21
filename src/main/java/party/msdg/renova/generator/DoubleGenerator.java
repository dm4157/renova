package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.generator.basic.NumberBasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DoubleGenerator extends Generator<Double> {

    @Override
    public String name() {
        return "DoubleGenerator";
    }

    @Override
    public Double next() {
        return NumberBasic.nextDouble(args.min(), args.max(), args.prec());
    }
}