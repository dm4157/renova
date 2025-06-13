package party.msdg.renova.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.work.WorkLog;
import party.msdg.renova.user.User;

@Service
public class CacheTestService {
    private final WorkLog log = WorkLog.init(this.getClass());

    @Cacheable(cacheNames = "test", key = "'test'+#i")
    public User testCacheManager(int i) {
        log.tag("testCacheManager").info("无缓存");
        return new User("test", "password"+i);
    }
}
