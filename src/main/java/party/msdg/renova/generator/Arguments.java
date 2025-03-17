package party.msdg.renova.generator;

import lombok.Getter;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkAssert;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成器统一参数定义
 *
 * @author   Mr.Ten
 * @summary  生成器参数定义
 * @since 2025/3/13 15:38
 */
public class Arguments {

    /**
     * 数字大小限制
     */
    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE;

    /**
     * 小数位数，精度
     */
    private int prec = 2;

    /**
     * 字符串长度限制
     */
    private int minLen = 4;
    private int maxLen = 1000;
    /**
     * 语言类型：en，ch
     */
    private String lang;

    // min:3 max:10 prec:3
    public Arguments(String args) {

        // 构造参数map
        String[] argPairs = args.trim().split(" ");
        Map<String, String> argMap = new HashMap<>();
        for (String argPair : argPairs) {
            String[] keyValue = argPair.split(":");
            WorkAssert.equalBasic(keyValue.length, 2).message("生成器参数格式不正确: {}", argPair);

            argMap.put(keyValue[0], keyValue[1]);
        }

        // 放置参数值
        Class<Arguments> argClass = (Class<Arguments>) this.getClass();
        for (java.lang.reflect.Field field : argClass.getDeclaredFields()) {
            if (argMap.containsKey(field.getName())) {
                try {
                    if ("int".equals(field.getType().getName())) {
                        field.set(this, Integer.parseInt(argMap.get(field.getName())));
                    } else {
                        field.set(this, field.getType().cast(argMap.get(field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    throw Work.ex(e);
                }
            }
        }
    }

    /*
        get 方法
     */
    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    public int prec() {
        return prec;
    }

    public int minLen() {
        return minLen;
    }

    public int maxLen() {
        return maxLen;
    }

    public String lang() {
        return lang;
    }
}
