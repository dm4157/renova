package party.msdg.renova.generator.basic;

import party.msdg.renova.base.work.WorkAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 数字类基础生成器
 * Wow! Sweet moon.
 */
public class NumberBasic {

    public static int nextInt(int max) {
        return nextInt(0, max);
    }

    /**
     * 随机生成指定范围的整数
     * [min, max）
     * @param min   最小
     * @param max   最大
     */
    public static int nextInt(int min, int max) {
        WorkAssert.lessThan(min, max).message("min应该小于max");
        
        return (int) (min + Math.random() * (max - min));
    }

    /**
     * 随机生成指定范围的浮点数
     * [min, max)
     * @param min   最小（包含）
     * @param max   最大（不包含）
     * @param precision 精度
     */
    public static double nextDouble(double min, double max, int precision) {
        WorkAssert.lessThan(min, max).message("最小值(min)应该小于最大值(max)。");
        WorkAssert.isTrue(precision >= 0).message("精度(precision)应该是自然数。");
        
        double value = ThreadLocalRandom.current().nextDouble(min, max);
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(precision, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
