package party.msdg.renova.generator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import party.msdg.renova.toolkit.Trick;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
class GeneratorsTest {

    @Autowired
    private Generators generators;


    @Test
    public void testNext() {
        gen("IntegerGenerator", "min:9 max:10");
        gen("DoubleGenerator", "min:2 max:9 prec:3");
        gen("StringGenerator", "minLen:5 maxLen:12 lang:en");
        gen("StringGenerator", "minLen:2 maxLen:5 lang:ch");
    }

    private void gen(String gen, String args) {
        // 获取生成器
        Generator<?> generator = generators.getGenerator(gen, args);

        // 检查生成器是否为 null
        assertNotNull(generator, "Generator should not be null");

        System.out.println("单个生成测试");
        // 尝试强制转换并捕获异常
        for (int i = 0; i < 10; i++) {
            Object next = generator.next();
            System.out.println(next);
            assertNotNull(next, "Generated integer should not be null");
        }


        System.out.println("批量生成测试");
        List<?> result = generator.next(100);
        for (Object one : result) {
            System.out.println(one);
        }

        System.out.println("=========END============");
    }
}