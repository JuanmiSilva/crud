
package src;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {

    private static BasicDataSource dataSource = null;

    private static DataSource getDataSource() {

        if (dataSource == null) {
            dataSource = new BasicDataSource();

            dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("0607");
            dataSource.setUrl(
                "jdbc:mariadb://localhost:3307/tienda"
            );

            dataSource.setInitialSize(5);
            dataSource.setMaxIdle(5);
            dataSource.setMaxTotal(20);
            dataSource.setMaxWaitMillis(5000);
        }

        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
