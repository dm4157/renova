package party.msdg.renova.base.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import party.msdg.renova.toolkit.LittleTrick;

import static party.msdg.renova.base.work.WorkContext.*;

public class WorkLog {
    
    private static final Map<String, Logger> loggerMap = new ConcurrentHashMap<>();
    
    /**
     * LOG for CLASS
     */
    private Class<?> clazz;
    
    /**
     * 私有构造
     */
    private WorkLog(Class<?> clazz) {
        this.clazz = clazz;
    }
    
    public static WorkLog init(Class<?> clazz) {
        return new WorkLog(clazz);
    }
    
    /**
     * 创建一个Log对象
     */
    public WorkLogEvent tag(String tag) {
        Logger logger = loggerMap.get(clazz.getTypeName());
        if (null == logger) {
            logger = LoggerFactory.getLogger(clazz);
            loggerMap.put(clazz.getTypeName(), logger);
        }
        
        WorkLogEvent builder = new WorkLogEvent();
        builder.logger = logger;
        builder.tag = tag;
        return builder;
    }
    
    public static class WorkLogEvent {
        private Logger logger;
        
        private String tag;
        private String scene;
        private Throwable throwable;
        
        // 默认输出部分堆栈
        private String throwMode = LOG_STACK_PART;
        
        private WorkLogEvent() {}
        
        /*
         * --------------------------
         * 🤔 过程方法
         * --------------------------
         */
        
        public WorkLogEvent tag(String tag) {
            this.tag = tag;
            return this;
        }
        
        public WorkLogEvent scene(Object... messages) {
            this.scene = LittleTrick.buildMsg(messages);
            return this;
        }
        
        public WorkLogEvent ex(Throwable throwable) {
            this.throwable = throwable;
            return this;
        }
        
        public WorkLogEvent stackPart() {
            this.throwMode = LOG_STACK_PART;
            return this;
        }
        
        public WorkLogEvent stackWhole() {
            this.throwMode = LOG_STACK_WHOLE;
            return this;
        }
        
        public WorkLogEvent stackNone() {
            this.throwMode = LOG_STACK_NONE;
            return this;
        }
        
        /*
         * --------------------------
         * 🤔 终结方法
         * --------------------------
         */
        public void debug(Object...messages) {
            if (null != throwable && throwMode.equals(LOG_STACK_WHOLE)) {
                logger.debug(buildMessage(messages), throwable);
            } else {
                logger.debug(buildMessage(messages));
            }
        }
        
        public void info(Object... messages) {
            if (null != throwable && throwMode.equals(LOG_STACK_WHOLE)) {
                logger.info(buildMessage(messages), throwable);
            } else {
                logger.info(buildMessage(messages));
            }
        }
        
        public void warn(Object... messages) {
            if (null != throwable && throwMode.equals(LOG_STACK_WHOLE)) {
                logger.warn(buildMessage(messages), throwable);
            } else {
                logger.warn(buildMessage(messages));
            }
        }
        
        public void error(Object...messages) {
            if (null != throwable && throwMode.equals(LOG_STACK_WHOLE)) {
                logger.error(buildMessage(messages), throwable);
            } else {
                logger.error(buildMessage(messages));
            }
        }
        
        private String buildMessage(Object...messages) {
            StringBuffer sb = new StringBuffer();
            if (LittleTrick.notEmpty(tag)) {
                sb.append("#").append(tag).append("#");
            }
            
            if (LittleTrick.notEmpty(scene)) {
                sb.append(" <").append(scene).append(">");
            }
            
            String message = LittleTrick.buildMsg(messages);
            if (LittleTrick.notEmpty(message)) {
                sb.append(" 🐳").append(message);
            }
            
            if (null != throwable) {
                if (throwMode.equals(LOG_STACK_PART)) {
                    appendStackTrace(sb, throwable);
                } else {
                    sb.append("\n👻").append(throwable);
                }
            }
            return sb.toString();
        }
        
        /*
         * --------------------------
         * 🤔 copy from Throwable.class
         * --------------------------
         */
        
        /** Caption for labeling causative exception stack traces */
        private static final String CAUSE_CAPTION = "Caused by: ";
        
        /** Caption for labeling suppressed exception stack traces */
        private static final String SUPPRESSED_CAPTION = "Suppressed: ";
        
        private static final String[] OUR_KEY = new String[] {"com.ke", "com.lianjia"};
        
        private void appendStackTrace(StringBuffer sb, Throwable throwable) {
            // Guard against malicious overrides of Throwable.equals by
            // using a Set with identity equality semantics.
            Set<Throwable> dejaVu =
                    Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
            dejaVu.add(throwable);
            
            // append message
            sb.append("\n👻").append(throwable).append("\n");
            
            boolean needIgnore = true;
            StackTraceElement[] trace = throwable.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                if (containKey(traceElement.toString())) {
                    sb.append("\tat ").append(traceElement).append("\n");
                    needIgnore = true;
                } else {
                    if (needIgnore) {
                        sb.append("\t<lib>...\n");
                        needIgnore = false;
                    }
                }
            }
            
            // append suppressed exceptions, if any
            for (Throwable se : throwable.getSuppressed()) {
                appendEnclosedStackTrace(sb, se, trace, SUPPRESSED_CAPTION, "\t", dejaVu);
            }
            
            // Print cause, if any
            Throwable ourCause = throwable.getCause();
            if (ourCause != null)
                appendEnclosedStackTrace(sb, ourCause, trace, CAUSE_CAPTION, "", dejaVu);
        }
        
        private void appendEnclosedStackTrace(StringBuffer sb,
                                              Throwable selfThrowable,
                                              StackTraceElement[] enclosingTrace,
                                              String caption,
                                              String prefix,
                                              Set<Throwable> dejaVu) {
            if (dejaVu.contains(selfThrowable)) {
                sb.append("\t[CIRCULAR REFERENCE:").append(selfThrowable.toString()).append("]\n");
            } else {
                dejaVu.add(selfThrowable);
                // Compute number of frames in common between this and enclosing trace
                StackTraceElement[] trace = selfThrowable.getStackTrace();
                int m = trace.length - 1;
                int n = enclosingTrace.length - 1;
                while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
                    m--;
                    n--;
                }
                int framesInCommon = trace.length - 1 - m;
                
                sb.append(prefix).append(caption).append(selfThrowable).append("\n");
                boolean needIgnore = false;
                for (int i = 0; i <= m; i++) {
                    if (containKey(trace[i].toString())) {
                        sb.append(prefix).append("\tat ").append(trace[i]).append("\n");
                        needIgnore = true;
                    } else {
                        if (needIgnore) {
                            sb.append("\t.....\n");
                            needIgnore = false;
                        }
                    }
                }
                
                if (framesInCommon != 0)
                    sb.append(prefix).append("\t... ").append(framesInCommon).append(" more\n");
                
                // Print suppressed exceptions, if any
                for (Throwable se : selfThrowable.getSuppressed())
                    appendEnclosedStackTrace(sb, se, trace, SUPPRESSED_CAPTION,
                            prefix + "\t", dejaVu);
                
                // Print cause, if any
                Throwable ourCause = selfThrowable.getCause();
                if (ourCause != null)
                    appendEnclosedStackTrace(sb, ourCause, trace, CAUSE_CAPTION, prefix, dejaVu);
            }
        }
        
        private boolean containKey(String target) {
            for (String key : OUR_KEY) {
                if (target.contains(key))
                    return true;
            }
            return false;
        }
    }
}
