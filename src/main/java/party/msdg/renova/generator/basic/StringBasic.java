package party.msdg.renova.generator.basic;

import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkAssert;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 字符类基础生成器
 * Wow! Sweet moon.
 */
public class StringBasic {
    
    private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private final static Random random = new Random();

    // GB2312一级汉字区码范围：0xB0-0xD7
    private static final int HIGH_START = 0xB0;
    private static final int HIGH_END = 0xD7;
    private static final int HIGH_RANGE = HIGH_END - HIGH_START + 1;

    // GB2312位码范围：0xA1-0xFE
    private static final int LOW_START = 0xA1;
    private static final int LOW_END = 0xFE;
    private static final int LOW_RANGE = LOW_END - LOW_START + 1;

    /**
     * 生成指定长度范围内的随机英文字符串
     * 此方法用于生成一个长度在minLen到maxLen之间的随机英文字符串，用于密码生成、数据模拟等场景
     *
     * @param minLen 最小长度，表示生成字符串的最短长度
     * @param maxLen 最大长度，表示生成字符串的最长长度
     * @return 返回生成的随机英文字符串
     */
    public static String genEnglish(int minLen, int maxLen) {
        // 随机确定字符串的长度，确保长度在指定的范围内
        int length = randomLength(minLen, maxLen);

        // 使用StringBuilder来构建最终的字符串，初始化容量为随机确定的长度
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 随机选择一个字母索引
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    /**
     * 生成指定长度范围内的随机中文字符串
     * 此方法用于生成一个长度在minLen到maxLen之间的随机中文字符串，用于密码生成、数据模拟等场景
     *
     * @param minLen 最小长度，表示生成字符串的最短长度
     * @param maxLen 最大长度，表示生成字符串的最长长度
     * @return 返回生成的随机中文字符串
     */
    public static String genChinese(int minLen, int maxLen) {
        int length = randomLength(minLen, maxLen);

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 随机生成区码和位码
            int high = HIGH_START + NumberBasic.nextInt(HIGH_RANGE);
            int low = LOW_START + NumberBasic.nextInt(LOW_RANGE);

            // 构造GB2312编码字节
            byte[] gb2312Bytes = new byte[]{(byte) high, (byte) low};

            // 转换为中文字符
            String chineseChar;
            try {
                chineseChar = new String(gb2312Bytes, "GB2312");
                sb.append(chineseChar);
            } catch (UnsupportedEncodingException e) {
                throw Work.ex(e);
            }
        }
        
        return sb.toString();
    }

    /**
     * 在给定范围内随机长度
     * @param minLen    最小长度（包含）
     * @param maxLen    最大长度（不包含）
     * @return          随机长度
     */
    private static int randomLength(int minLen, int maxLen) {
        WorkAssert.moreThan(minLen, 0).message("生产长度不能小于0，当前{}", minLen);
        WorkAssert.lessThan(maxLen, 10000).message("生成长度不超过10000，当前{}", maxLen);
        WorkAssert.lessEqual(minLen, maxLen).message("最小长度不能大于最大长度，最大{},最小{}", maxLen, minLen);

        // 随机长度范围
        return random.nextInt(maxLen - minLen + 1) + minLen;
    }
    
    /**
     * 随机生成中文姓名
     */
    public static String genChineseName() {
        // 这是一些常用的姓氏，可以根据需要添加更多
        String[] surnames = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "楮", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许"};
        // 这是一些常用的名字字符，可以根据需要添加更多
        String commonNameChars = "伟芳娜秀英敏静丽强磊军洋勇艳杰娟涛春花飞霞秀兰翠燕";
        Random rand = new Random();
        
        // 随机选择一个姓氏
        String surname = surnames[rand.nextInt(surnames.length)];
        
        // 随机选择一个或两个常用的名字字符
        String name = String.valueOf(commonNameChars.charAt(rand.nextInt(commonNameChars.length())));
        if (rand.nextBoolean()) {
            name += commonNameChars.charAt(rand.nextInt(commonNameChars.length()));
        }
        
        // 输出生成的随机中文姓名
        return surname + name;
    }
}
