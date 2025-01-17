package party.msdg.renova.base;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
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
                        throw Work.ex().just(WorkCode.NOT_LOGIN);
                    }
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/api/uc/**", "/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许所有路径
                .allowedOrigins("http://localhost:5173")  // 允许所有来源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的HTTP方法
                .allowedHeaders("*")  // 允许的请求头
                .allowCredentials(true)  // 是否允许发送cookie
                .maxAge(3600);  // 设置预检请求的缓存时间（单位：秒）
    }
}