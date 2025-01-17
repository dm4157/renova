package party.msdg.renova;

import cn.dev33.satoken.SaManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class RenovaApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(RenovaApplication.class, args);
        
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }
}
