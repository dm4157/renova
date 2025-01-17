package party.msdg.renova.database.source;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/source")
public class SourceController {

    @Autowired
    private SourceService sourceService;
    
    @GetMapping("/v1/all")
    public List<Source> sources() {
        return sourceService.allSources(StpUtil.getLoginId(0));
    }
    
    @GetMapping("/v1/one/{id}")
    public Source one(
        @PathVariable("id") int id
    ) {
        return sourceService.one(id);
    }
    
    @PostMapping("/v1/add")
    public void add(@RequestBody Source source) {
        // 放置登录用户
        source.setCuser(StpUtil.getLoginId(0));
        sourceService.add(source);
    }
}
