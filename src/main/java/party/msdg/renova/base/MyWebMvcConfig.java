package party.msdg.renova.base;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkCode;

@Configuration
class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> {
                    try {
                        StpUtil.checkLogin();
                    } catch (NotLoginException nle) {
                        throw Work.ex().http(HttpStatus.UNAUTHORIZED).just(WorkCode.NOT_LOGIN);
                    }
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/api/uc/**", "/error");
    }
}