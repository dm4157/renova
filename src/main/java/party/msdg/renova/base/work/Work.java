package party.msdg.renova.base.work;

/**
 * 努力工作，赚钱养家
 * Wow! Sweet moon.
 */
public class Work {
    
    /**
     * 抓住工作的小异常
     */
    public static WorkException.WorkExceptionBuilder ex() {
        return new WorkException.WorkExceptionBuilder();
    }
    
    /**
     * 常规只有消息的异常
     * 默认值：
     * - http status: 417
     * - code: 100000
     * - log level: info
     * - stack: part
     * @param messages  消息
     */
    public static WorkException ex(Object...messages) {
        return new WorkException.WorkExceptionBuilder().message(messages);
    }
    
    public static WorkException ex(Exception e) {
        return new WorkException.WorkExceptionBuilder().message("ExType:{}, ExMessage:{}", e.getClass().getSimpleName(), e.getMessage());
    }
    
    /**
     * 获得日志构造工厂
     */
    public static WorkLog getLogger(Class<?> clazz) {
        return WorkLog.init(clazz);
    }
    public static WorkLog getLogger(Object obj) {
        return WorkLog.init(obj.getClass());
    }
}
