/*
 * @version 1.0.0 Controller created to load model and views supporting 'ventas' 
 * (addVenta, listarVentas).
*/
package controller;

import Dao.ArticleDao;
import Dao.ConectarDB;
import Dao.UsuarioDao;
import Dao.VentaDao;
import java.util.List;
import models.VentaBean;
import models.VentaValidation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Sergio
 * @since 23/03/2022
 * @version 1.0.0 Controller created to load model and views supporting 'ventas' 
 * (addVenta, listarVentas).
 */
public class ventaController {

    private VentaValidation ventaValidar;
    private JdbcTemplate jdbcTemplate;
    
    public ventaController(){
        this.ventaValidar = new VentaValidation();
        ConectarDB conectar = new ConectarDB();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
    }
    
    /**
     * 
     * @return page from listarVentas.jsp which is loaded based on model from VentaBean
     * and list the information from data taken from a sql query listing information of 
     * users, articles and sells.
     */
    @RequestMapping(value="listarVentas.htm")
    public ModelAndView listarVentas(){
        ModelAndView mav = new ModelAndView();
        String sql = "select v.id_venta, v.id_usuario, u.nombre, u.apellido,"
                + "v.fecha_venta fecha, v.id_articulo, a.titulo articulo "
                + "from ventas v, usuarios u, articulos a "
                + "where u.id_usuario = v.id_usuario "
                + "and v.id_articulo = a.id_articulo";
        List ventas = this.jdbcTemplate.queryForList(sql);
        mav.addObject("venta", ventas);
        mav.setViewName("views/listarVentas");
        return mav;
    }
    
    /**
     * 
     * @return loads view from addVenta.jsp and preload form select options for user and 
     * article fields as well as the sell id.
     */
    @RequestMapping(value="addVenta.htm", method=RequestMethod.GET)
    public ModelAndView addVenta(){
        ModelAndView mav = new ModelAndView();
        VentaDao ventasDao = new VentaDao();
        int id_venta = ventasDao.consultarCodigo();
        //-----ventas----
        VentaBean venta = new VentaBean();
        //Set sell id so it preloads the id of the new sell we're going to add
        venta.setId_venta(id_venta);
        //-----usuarios-----
        UsuarioDao userDao = new UsuarioDao();
        //Lists the users on the DB so we can select the one we need
        List id_usuario = userDao.consultarUsuarios();
        //-----articulos----
        ArticleDao articleDao = new ArticleDao();
        //Lists the articles on the DB so we can select the one we need
        List id_articulo = articleDao.consultarArticulos();
         System.out.print("------");
        mav.addObject("venta", venta);    
        mav.addObject("id_usuario", id_usuario);
        mav.addObject("id_articulo", id_articulo);
        mav.setViewName("views/addVenta");
        return mav;
    }
    
    @RequestMapping(value="addVenta.htm", method=RequestMethod.POST)
    public ModelAndView addVenta(VentaBean v){
        ModelAndView mav = new ModelAndView();
        String sql = "insert into ventas (id_usuario, id_articulo, fecha_venta) "
                + "values(?,?,?)";
        jdbcTemplate.update(sql, v.getId_usuario(), v.getId_articulo(), v.getFecha_venta());
        mav.setViewName("redirect:/listarVentas.htm");
        return mav;
    }
}

