/*
 * @version 1.0.0 Dao which sets the necesary information in order to connect to the DB. 
*/

package Dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 Dao which sets the necesary information in order to connect to the DB.
 */

public class ConectarDB {
    
    /**
     * 
     * @see "https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/datasource/DriverManagerDataSource.html"
     * @return JDBC connection to datasource, in this case the DataBase. 
     */
    public DriverManagerDataSource conectar(){
        DriverManagerDataSource datasource = new DriverManagerDataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost:3306/prueba");
        datasource.setUsername("root");
        datasource.setPassword("");
        return datasource;
    }
}
