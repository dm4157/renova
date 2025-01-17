package party.msdg.renova.base.work;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import party.msdg.renova.toolkit.LittleTrick;

import static party.msdg.renova.base.work.WorkContext.*;

/**
 * 业务异常类，封装各种需要的信息。
 * 长期维护（希望）
 * Wow! Sweet moon.
 */
@Getter
public class WorkException extends RuntimeException {
    
    private WorkContext wc;
    
    private WorkException() {
        super();
    }
    
    public WorkException(String message) {
        super(message);
    }
    
    public void setWc(WorkContext wc) {
        this.wc = wc;
    }
    
    /**
     * WorkException构造器
     */
    public static class WorkExceptionBuilder {
    
        private WorkContext wc;
        
        protected WorkExceptionBuilder() {
            wc = new WorkContext();
            wc.setLogLevel(LOG_LEVEL_ERROR);
            wc.setTag("W.E");
        }
    
        public void setWc(WorkContext wc) {
            this.wc = wc;
        }
        
        /*
         --------------------------
         🤔 终结方法
         --------------------------
         */
        
        /**
         * 异常提示信息,同时也是log信息。支持占位填写，例如：
         *  Exs.message("这是一个异常提示信息，出错位置在{}, 原因可能是{}", "答题场"， "人为操作失误");
         *  输出内容：这是一个异常提示信息，出错位置在答题场, 原因可能是人为操作失误
         * @param messages  支持{}占位，可变长度
         * @return  拼接内容
         */
        public WorkException message(Object...messages) {
            String message = LittleTrick.buildMsg(messages);
            if (LittleTrick.isEmpty(message) && null != wc.getCode()) {
                message = wc.getCode().text();
            }
            
            WorkException we = LittleTrick.isEmpty(message) ? new WorkException() : new WorkException(message);
            we.setWc(wc);
            return we;
        }
        
        /**
         * 终止构造，创建异常对象
         */
        public WorkException just() {
            WorkException we = null == wc.getCode() ? new WorkException() : new WorkException(wc.getCode().text());
            we.setWc(wc);
            return we;
        }
    
        public WorkException just(WorkCode code) {
            this.wc.setCode(code);
            return just();
        }
        
        
        /*
         --------------------------
         🤔 过程方法
         --------------------------
         */
    
        /**
         * 业务异常编码，影响返回值的code
         * 如果未设置message，则使用其text
         */
        public WorkExceptionBuilder code(WorkCode workCode) {
            this.wc.setCode(workCode);
            return this;
        }
        
        /**
         * 现场提示信息。支持占位填写，例如：
         *  Exs.message("异常发生时，答题场ID:{}", "128");
         *  输出内容：异常发生时，答题场ID:128
         * @param messages  支持{}占位，可变长度
         * @return  拼接内容
         */
        public WorkExceptionBuilder scene(Object...messages) {
            this.wc.setScene(LittleTrick.buildMsg(messages));
            return this;
        }
    
        /**
         * 请求响应对HTTP状态，默认417
         */
        public WorkExceptionBuilder http(HttpStatus status) {
            this.wc.setStatus(status);
            return this;
        }
    
        /**
         * 日志的#业务关键字#
         */
        public WorkExceptionBuilder tag(String tag) {
            this.wc.setTag(tag);
            return this;
        }
        
        /**
         * 业务数据。即使出错也要传递，一定是很重要的数据吧
         */
        public WorkExceptionBuilder data(Object data) {
            this.wc.setData(data);
            return this;
        }
        
        /**
         * 按等级输出日志，日志的内容是异常信息
         * 无堆栈
         */
        public WorkExceptionBuilder logDebug() {
            this.wc.setLogLevel(LOG_LEVEL_DEBUG);
            return this;
        }
        public WorkExceptionBuilder logInfo() {
            this.wc.setLogLevel(LOG_LEVEL_INFO);
            return this;
        }
        public WorkExceptionBuilder logWarn() {
            this.wc.setLogLevel(LOG_LEVEL_WARN);
            return this;
        }
        public WorkExceptionBuilder logError() {
            this.wc.setLogLevel(LOG_LEVEL_ERROR);
            return this;
        }
        public WorkExceptionBuilder logNone() {
            this.wc.setLogLevel(LOG_LEVEL_NONE);
            return this;
        }
    
        /**
         * 输出全部堆栈
         */
        public WorkExceptionBuilder stackWhole() {
            this.wc.setLogMode(LOG_STACK_WHOLE);
            return this;
        }
    
        /**
         * 输出部分堆栈
         */
        public WorkExceptionBuilder stackPart() {
            this.wc.setLogMode(LOG_STACK_PART);
            return this;
        }
        
        public WorkExceptionBuilder stackNone() {
            this.wc.setLogMode(LOG_STACK_NONE);
            return this;
        }
    }
}
