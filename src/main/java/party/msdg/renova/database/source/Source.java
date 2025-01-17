package party.msdg.renova.database.source;

import lombok.Data;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.Date;

/**
 * 数据源
 *
 * @author   msdg
 * @version  v1
 * @summary  数据源
 * @since 2023/12/1 18:19
 */
@Data
public class Source {
    
    private static final String TYPE_MYSQL = "mysql";

    private int id;
    private String name;
    
    private String type;
    private String host;
    private int port;
    private String dbname;
    private String user;
    private String password;
    private String remark;
    
    private int cuser;
    private int muser;
    private Date ctime;
    private Date mtime;
    
    public DataSource toDataSource() {
        DataSource ds = new DataSource();
        ds.setName(name);
        ds.setUrl(url());
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setDriverClassName(driver());
        return ds;
    }
    
    
    public String url() {
        StringBuilder sb = new StringBuilder();
        if (TYPE_MYSQL.equals(type)) {
            sb.append("jdbc:mysql://").append(host).append(":").append(port).append("/").append(dbname);
        }
        
        return sb.toString();
    }
    
    private String driver() {
        String driver = null;
        if (TYPE_MYSQL.equals(type)) {
            driver = "com.mysql.jdbc.Driver";
        }
        
        return driver;
    }
}
