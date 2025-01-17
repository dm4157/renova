package party.msdg.renova.generator;

import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;

import java.util.Random;

/**
 * 字符类基础生成器
 * Wow! Sweet moon.
 */
@Component
public class StringBasic {
    
    final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    
    /**
     * 随机生成一定长度的英文字符串
     * @param length    长度
     */
    public String genEnglish(int length) {
        WorkAssert.isTrue(length > 0 && length < 10000).message("生成英文字符串长度不超过10000。");
        
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            stringBuilder.append(randomChar);
        }
        
        return stringBuilder.toString();
    }
    
    /**
     * 随机生成一定长度的中文字符串
     * @param length    长度
     */
    public String genChinese(int length) {
        WorkAssert.isTrue(length > 0 && length < 10000).message("生成中文字符串长度不超过10000。");
        
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // Unicode range for Chinese characters is roughly 0x4E00 to 0x9FFF
            char randomChineseChar = (char) (0x4E00 + random.nextInt(0x9FFF - 0x4E00));
            stringBuilder.append(randomChineseChar);
        }
        
        return stringBuilder.toString();
    }
    
    /**
     * 随机生成中文姓名
     */
    public String genChineseName() {
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
    
    public static void main(String[] args) {
        StringBasic basic = new StringBasic();
        System.out.println(basic.genEnglish(25));
        System.out.println(basic.genChinese(25));
    }
}
