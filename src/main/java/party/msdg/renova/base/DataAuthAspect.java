package party.msdg.renova.base;

import cn.dev33.satoken.stp.StpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.base.work.WorkCode;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataAuthAspect {

    private static final String CUSER_METHOD = "getCuser";

    @Around("@annotation(DataAuth)")
    public Object dataAuthCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        Method method = proceed.getClass().getMethod(CUSER_METHOD);
        int cuser = (int) method.invoke(proceed);
        WorkAssert.equalBasic(cuser, StpUtil.getLoginId(0)).http(HttpStatus.FORBIDDEN).just(WorkCode.NO_DATA_AUTH);
        return proceed;
    }
}
