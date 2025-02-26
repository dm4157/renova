package party.msdg.renova.database.table;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableDao {

    void add(Table table);

    void batchInsert(int sourceId, int cuser, List<Table> tables);

    void truncate();
}
