package party.msdg.renova.database.source;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import party.msdg.renova.base.Re;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/api/v1/source")
public class SourceController {

    @Autowired
    private SourceService sourceService;
    
    @GetMapping("")
    public Re<List<Source>> sources() {
        List<Source> sources = sourceService.all(StpUtil.getLoginId(0));
        return Re.ok(sources);
    }
    
    @GetMapping("/{id}")
    public Re<Source> one(@PathVariable("id") int id) {
        Source source = sourceService.one(id);
        return Re.ok(source);
    }
    
    @PostMapping("")
    public void add(@RequestBody Source source) {
        // 放置登录用户
        source.setCuser(StpUtil.getLoginId(0));
        sourceService.add(source);
    }
}
