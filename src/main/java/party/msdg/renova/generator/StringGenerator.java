package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.generator.basic.StringBasic;

import java.util.ArrayList;
import java.util.List;

@Component
public class StringGenerator extends Generator<String> {

    @Override
    public String name() {
        return "StringGenerator";
    }

    @Override
    public String next() {
        if (args.lang().equals("en")) {
            return StringBasic.genEnglish(args.minLen(), args.maxLen());
        } else if (args.lang().equals("ch")) {
            return StringBasic.genChinese(args.minLen(), args.maxLen());
        } else {
            throw Work.ex().message("不支持的语言类型。{}", args.lang());
        }
    }

    @Override
    public List<String> next(int num) {
        List<String> result = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            result.add(next());
        }
        return result;
    }
}