package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.generator.basic.NumberBasic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class IntegerGenerator extends Generator<Integer> {

    @Override
    public String name() {
        return "IntegerGenerator";
    }

    @Override
    public Integer next() {
        return NumberBasic.nextInt(args.min(), args.max());
    }
}