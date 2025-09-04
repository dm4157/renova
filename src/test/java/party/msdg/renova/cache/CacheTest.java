package party.msdg.renova.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import party.msdg.renova.user.model.User;

@ActiveProfiles("dev")
@SpringBootTest
public class CacheTest {

    @Autowired
    private CacheTestService cacheTestService;

    @Test
    public void test() {
        User user = cacheTestService.testCacheManager(2);
        System.out.println(user);
        user = cacheTestService.testCacheManager(2);
        System.out.println(user);
    }
}
