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
    
    public static final String TYPE_MYSQL = "mysql";

    /**
     * 自增id
     */
    private int id;
    /**
     * 数据源名称（自定义）
     */
    private String name;

    /**
     * 数据库类型
     */
    private String type;
    /**
     * 访问地址
     */
    private String host;
    /**
     * 端口
     */
    private int port;

    /**
     * 数据库名称
     */
    private String dbname;

    /**
     * 账号用户
     */
    private String user;
    /**
     * 访问密码
     */
    private String password;

    private int status;

    /**
     * 备注
     */
    private String remark;


    /**
     * 常规信息
     */
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
