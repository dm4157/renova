package party.msdg.renova.base.work;

import org.springframework.http.HttpStatus;
import party.msdg.renova.toolkit.LittleTrick;

import static party.msdg.renova.base.work.WorkContext.*;

/**
 * Wow! Sweet moon.
 */
public class WorkAssert {

    /*
     * --------------------------
     * 🤔 断言方法（也是过程）
     * --------------------------
     */

    public static WorkAssertInfoBuilder isTrue(boolean expression) {
        return new WorkAssertInfoBuilder(expression);
    }

    /**
     * 非空报错
     */
    public static WorkAssertInfoBuilder isNull(Object data) {
        return isTrue(null == data);
    }

    /**
     * 空则报错
     */
    public static WorkAssertInfoBuilder notNull(Object data) {
        return isTrue(null != data);
    }

    /**
     * 逻辑非空判断
     */
    public static WorkAssertInfoBuilder notEmpty(Object data) {
        return isTrue(LittleTrick.notEmpty(data));
    }

    /**
     * 逻辑空判断
     */
    public static WorkAssertInfoBuilder isEmpty(Object data) {
        return isTrue(LittleTrick.isEmpty(data));
    }

    /**
     * 闭区间范围判断
     * 
     * @param data 判断值
     * @param floor 最小取值
     * @param ceil 最大取值
     */
    public static WorkAssertInfoBuilder between(int data, int floor, int ceil) {
        return isTrue(data >= floor && data <= ceil);
    }

    /**
     * 不相等报错
     */
    public static WorkAssertInfoBuilder equal(Object d1, Object d2) {
        if (null == d1) {
            return isTrue(null == d2);
        } else {
            return isTrue(d1.equals(d2));
        }
    }

    public static WorkAssertInfoBuilder equalBasic(int d1, int d2) {
        return isTrue(d1 == d2);
    }

    public static WorkAssertInfoBuilder equalBasic(long d1, long d2) {
        return isTrue(d1 == d2);
    }

    public static WorkAssertInfoBuilder equalBasic(short d1, short d2) {
        return isTrue(d1 == d2);
    }

    /**
     * 相等报错
     */
    public static WorkAssertInfoBuilder notEqual(Object d1, Object d2) {
        if (null == d1) {
            return isTrue(null != d2);
        } else {
            return isTrue(!d1.equals(d2));
        }
    }

    public static WorkAssertInfoBuilder notTrue(boolean bool) {
        return isTrue(!bool);
    }

    /**
     * value < target = true
     */
    public static WorkAssertInfoBuilder lessThan(int value, int target) {
        return isTrue(value < target);
    }

    public static WorkAssertInfoBuilder lessThan(long value, long target) {
        return isTrue(value < target);
    }
    
    public static WorkAssertInfoBuilder lessThan(double value, double target) {
        return isTrue(value < target);
    }

    /**
     * value > target = true
     */
    public static WorkAssertInfoBuilder moreThan(int value, int target) {
        return isTrue(value > target);
    }

    public static WorkAssertInfoBuilder moreThan(long value, long target) {
        return isTrue(value > target);
    }
    
    public static WorkAssertInfoBuilder moreThan(double value, double target) {
        return isTrue(value > target);
    }

    /**
     * value <= target = true
     */
    public static WorkAssertInfoBuilder lessEqual(int value, int target) {
        return isTrue(value <= target);
    }

    public static WorkAssertInfoBuilder lessEqual(long value, long target) {
        return isTrue(value <= target);
    }
    
    public static WorkAssertInfoBuilder lessEqual(double value, double target) {
        return isTrue(value <= target);
    }

    /**
     * value >= target = true
     */
    public static WorkAssertInfoBuilder moreEqual(int value, int target) {
        return isTrue(value >= target);
    }

    public static WorkAssertInfoBuilder moreEqual(long value, long target) {
        return isTrue(value >= target);
    }
    
    public static WorkAssertInfoBuilder moreEqual(double value, double target) {
        return isTrue(value >= target);
    }
    
    /**
     * 判断 不为空
     * 并且
     * 判断 字符串参数最大长度不超过 maxLen
     */
    public static WorkAssertInfoBuilder lenLess(String param, int maxLen) {
        return isTrue(LittleTrick.isEmpty(param) || param.length() <= maxLen);
    }
    
    /**
     * 判断字符串不为空
     * 且
     * 长度在范围内（含）
     */
    public static WorkAssertInfoBuilder lenBetween(String param, int minLen, int maxLen) {
        return isTrue(null != param && param.length() >= minLen && param.length() <= maxLen);
    }
    
    
    /**
     * 断言异常构造
     */
    public static class WorkAssertInfoBuilder {
    
        /**
         * 断言通过
         */
        private final boolean pass;
        
        private final WorkContext wc;
    
    
        private WorkAssertInfoBuilder(boolean pass) {
            this.pass = pass;
            wc = new WorkContext();
            wc.setLogLevel(LOG_LEVEL_NONE);
            wc.setLogMode(LOG_STACK_NONE);
            wc.setTag("W.A");
        }
    
    
        /*
         * --------------------------
         * 🤔 过程方法
         * --------------------------
         */

        public WorkAssertInfoBuilder code(WorkCode code) {
            this.wc.setCode(code);

            return this;
        }

        public WorkAssertInfoBuilder http(HttpStatus status) {
            this.wc.setStatus(status);
            return this;
        }

        public WorkAssertInfoBuilder scene(Object... messages) {
            this.wc.setScene(LittleTrick.buildMsg(messages));
            return this;
        }
        
        public WorkAssertInfoBuilder tag(String tag) {
            this.wc.setTag(tag);
            return this;
        }
        
        public WorkAssertInfoBuilder data(Object data) {
            this.wc.setData(data);
            return this;
        }


        /**
         * 按等级输出日志，日志的内容是异常信息
         * 无堆栈
         */

        public WorkAssertInfoBuilder logNone() {
            this.wc.setLogLevel(LOG_LEVEL_NONE);
            return this;
        }
    
        public WorkAssertInfoBuilder logDebug() {
            this.wc.setLogLevel(LOG_LEVEL_DEBUG);
            this.wc.setLogMode(LOG_STACK_PART);
            return this;
        }
    
        public WorkAssertInfoBuilder logInfo() {
            this.wc.setLogLevel(LOG_LEVEL_INFO);
            this.wc.setLogMode(LOG_STACK_PART);
            return this;
        }
    
        public WorkAssertInfoBuilder logWarn() {
            this.wc.setLogLevel(LOG_LEVEL_WARN);
            this.wc.setLogMode(LOG_STACK_PART);
    
            return this;
        }
    
        public WorkAssertInfoBuilder logError() {
            this.wc.setLogLevel(LOG_LEVEL_ERROR);
            this.wc.setLogMode(LOG_STACK_PART);
            return this;
        }
        
        public WorkAssertInfoBuilder logDebug(String tag) {
            this.wc.setTag(tag);
            return logDebug();
        }

        public WorkAssertInfoBuilder logInfo(String tag) {
            this.wc.setTag(tag);
            return logInfo();
        }

        public WorkAssertInfoBuilder logWarn(String tag) {
            this.wc.setTag(tag);
            return logWarn();
        }

        public WorkAssertInfoBuilder logError(String tag) {
            this.wc.setTag(tag);
            return logError();
        }

        /**
         * 输出全部堆栈
         */
        public WorkAssertInfoBuilder stackWhole() {
            this.wc.setLogMode(LOG_STACK_WHOLE);
            return this;
        }

        /**
         * 输出部分堆栈
         */
        public WorkAssertInfoBuilder stackPart() {
            this.wc.setLogMode(LOG_STACK_PART);
            return this;
        }

        public WorkAssertInfoBuilder stackNone() {
            this.wc.setLogMode(LOG_STACK_NONE);
            return this;
        }

        /*
         * --------------------------
         * 🤔 终结方法
         * --------------------------
         */

        /**
         * 没有消息的异常
         */
        public void just() {
            message();
        }
        
        public void just(WorkCode code) {
            this.wc.setCode(code);
            message();
        }

        /**
         * 自定义消息
         */
        public void message(Object... messages) {
            // 断言通过，啥也不做
            if (pass)
                return;

            // 构造异常
            WorkException.WorkExceptionBuilder exBuilder = Work.ex();
            exBuilder.setWc(wc);

            throw exBuilder.message(messages);
        }
    }
}
