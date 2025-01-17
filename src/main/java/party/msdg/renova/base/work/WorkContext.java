package party.msdg.renova.base.work;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 工作环境信息
 * Wow! Sweet moon.
 */
@Getter
@Setter
public class WorkContext {

    /*
     --------------------------
     🤔 日志级别
     --------------------------
     */
    public static final String LOG_LEVEL_NONE = "none";
    public static final String LOG_LEVEL_DEBUG = "debug";
    public static final String LOG_LEVEL_INFO = "info";
    public static final String LOG_LEVEL_WARN = "warn";
    public static final String LOG_LEVEL_ERROR = "error";
    
    /*
     --------------------------
     🤔 堆栈信息级别
     --------------------------
     */
    public static final String LOG_STACK_NONE = "none";
    public static final String LOG_STACK_PART = "part";
    public static final String LOG_STACK_WHOLE = "whole";
    
    
    /**
     * HTTP请求状态，默认417
     */
    private HttpStatus status = HttpStatus.EXPECTATION_FAILED;
    
    /**
     * 错误编码
     */
    private WorkCode code = new WorkCode(100000, "业务异常");
    
    /**
     * 现场信息，协助破案
     */
    private String scene;
    
    /**
     * Log级别,默认输出INFO级别日志
     * none, debug, info, warn, error
     */
    private String logLevel = LOG_LEVEL_INFO;
    
    /**
     * 前提是logLevel != none
     * 是否输出异常堆栈，默认输出业务相关的部分堆栈
     * none, keys, whole
     */
    private String logMode = LOG_STACK_PART;
    
    /**
     * 日志#业务关键字#
     */
    private String tag;
    
    /**
     * 数据内容
     */
    private Object data;
}
