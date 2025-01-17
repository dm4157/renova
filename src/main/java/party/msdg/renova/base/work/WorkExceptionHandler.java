package party.msdg.renova.base.work;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import party.msdg.renova.base.Re;
import party.msdg.renova.toolkit.LittleTrick;

import java.util.List;
import java.util.stream.Collectors;

import static party.msdg.renova.base.work.WorkContext.*;

@RestControllerAdvice
public class WorkExceptionHandler extends ResponseEntityExceptionHandler {
    private final WorkLog log = Work.getLogger(WorkExceptionHandler.class);

    @ExceptionHandler(WorkException.class)
    public final ResponseEntity<Object> handWork(Exception ex, WebRequest request) {
        WorkException workEx = (WorkException) ex;
        WorkContext wc = workEx.getWc();

        // 打印日志
        if (! wc.getLogLevel().equals(LOG_LEVEL_NONE)) {
            // 获得默认Key的log
            WorkLog.WorkLogEvent workLog = log.tag("W.E");

            // 设置自定义log key
            if (LittleTrick.notEmpty(wc.getTag())) {
                workLog.tag(wc.getTag());
            }

            // 设置现场信息
            if (LittleTrick.notEmpty(wc.getScene())) {
                workLog.scene(wc.getScene());
            }

            // 设置堆栈情况
            switch (wc.getLogMode()) {
                case LOG_STACK_WHOLE:
                    workLog.ex(workEx).stackWhole();
                    break;
                case LOG_STACK_PART:
                    workLog.ex(workEx);
                    break;
                case LOG_STACK_NONE:
                    // 不配置异常信息到LOG中
                    break;
            }

            // 确定日志消息内容
            String logMessage = workEx.getMessage();
            if (null == logMessage && null != wc.getCode()) {
                logMessage = wc.getCode().text();
            }

            // 设置日志级别，并输出日志
            switch (wc.getLogLevel()) {
                case LOG_LEVEL_DEBUG:
                    workLog.debug(logMessage);
                    break;
                case LOG_LEVEL_INFO:
                    workLog.info(logMessage);
                    break;
                case LOG_LEVEL_WARN:
                    workLog.warn(logMessage);
                    break;
                case LOG_LEVEL_ERROR:
                    workLog.error(logMessage);
                    break;
                case LOG_LEVEL_NONE:
                    // 不输出日志
                    break;
            }
        }

        // 构造响应内容
        Re<Object> re = new Re<>(
                wc.getCode().code(),
                LittleTrick.isEmpty(workEx.getMessage()) ? wc.getCode().text() : workEx.getMessage(),
                wc.getScene(),
                wc.getData());
        return new ResponseEntity<>(re, new HttpHeaders(), wc.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "";
        BindingResult bs = ex.getBindingResult();
        if (bs.hasErrors()) {
            List<String> validationList = bs.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            message = String.join(";", validationList);
        }

        Re<Object> exRe = new Re<>(status.value(), message, "", null);
        return new ResponseEntity<>(exRe, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = "缺少参数。类型：" + ex.getParameterType() + "，名称：" + ex.getParameterName();
        return new ResponseEntity<>(new Re<>(HttpStatus.BAD_REQUEST.value(), message, "", null), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return new ResponseEntity<>(new Re<>(statusCode.value(), ex.getMessage(), "", null), headers, statusCode);
    }
}
