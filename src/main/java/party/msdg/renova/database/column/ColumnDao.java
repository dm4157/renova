package party.msdg.renova.database.column;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnDao {

    void batchInsert(int sourceId, int cuser, List<Column> columns);

    void truncate();
}
