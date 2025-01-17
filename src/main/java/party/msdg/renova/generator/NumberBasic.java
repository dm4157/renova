package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 数字类基础生成器
 * Wow! Sweet moon.
 */
@Component
public class NumberBasic {
    
    /**
     * 随机生成指定范围的整数
     * [min, max）
     * @param min   最小
     * @param max   最大
     */
    public int nextInt(int min, int max) {
        WorkAssert.lessThan(min, max).message("min应该小于max");
        
        return (int) (min + Math.random() * (max - min));
    }
    
    /**
     * 从待选清单中随机抽取1个数据
     * @param items 待选清单
     */
    public int nextInt(List<Integer> items) {
        WorkAssert.notEmpty(items).message("待选清单不应为空。");
        
        int randomIndex = nextInt(0, items.size());
        return items.get(randomIndex);
    }
    
    /**
     * 随机生成指定范围的浮点数
     * [min, max)
     * @param min   最小（包含）
     * @param max   最大（不包含）
     * @param precision 精度
     */
    public double nextDouble(double min, double max, int precision) {
        WorkAssert.lessThan(min, max).message("最小值(min)应该小于最大值(max)。");
        WorkAssert.isTrue(precision >= 0).message("精度(precision)应该是自然数。");
        
        double value = ThreadLocalRandom.current().nextDouble(min, max);
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(precision, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
