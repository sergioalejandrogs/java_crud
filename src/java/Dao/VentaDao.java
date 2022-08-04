/*
 * @version 1.0.0 DataAccessObject ventaController with methods 
 * connecting with DB, making sql queries, storing and deleting rows from DB. 
*/
package Dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Sergio González
 * @since 23/03/2022
 * @version 1.0.0 DataAccessObject ventaController with methods 
 * connecting with DB, making sql queries, storing and deleting rows from DB.
 */
public class VentaDao {
    
    JdbcTemplate jdbcTemplate;
    ConectarDB conectar = new ConectarDB();
    
    /**
     * 
     * @return a list (based on a sql query) with all sells stored on DB.  
     */
    public List consultarVentas(){
        List ventas = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
        String sql = "select * from ventas";
        ventas = this.jdbcTemplate.queryForList(sql);
        return ventas;
    }
    
    /**
     * 
     * @return a number (based on a sql query) which shows next sell id 
     * when registering a new sell.
     */
    public int consultarCodigo(){
        int id_venta = 0;
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
        String sql = "select max(id_venta)+1 as codigo from ventas";
        id_venta = this.jdbcTemplate.queryForObject(sql, Integer.class);
        return id_venta;
    }
}
