package party.msdg.renova.base;

import lombok.Data;

/**
 * Wow! Sweet moon.
 * 东硕定义返回结果
 */
@Data
public class Re<T> {

    public static final int OK = 0;
    
    /**
     * 返回code 0 为正常
     */
    private int code;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 现场信息
     */
    private String scene;
    
    /**
     * 返回数据
     */
    private T data;

    public static <T> Re<T> ok() {
        return ok(null);
    }

    public static <T> Re<T> ok(T data) {
        Re<T> re = new Re<T>();
        re.code = OK;
        re.message = "";
        re.scene = "";
        re.data = data;
        return re;
    }
    
    public Re() {
    }
    
    public Re(int code, String message, String scene, T data) {
        this.code = code;
        this.message = message;
        this.scene = scene;
        this.data = data;
    }
}
