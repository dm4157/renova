package party.msdg.renova.database.source;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceDao {

    List<Source> all(@Param("userId") int userId);

    Source one(@Param("id") int id);

    void add(Source source);
}
