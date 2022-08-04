/*
 * @version 1.0.0 Controller created to load model and views supporting 'articulos' 
 * (formArticulo, vistaArticulo, addArticulo, deleteArticulo, updateArticulo, listarArticulos).
*/
package controller;

import Dao.ArticleDao;
import Dao.ConectarDB;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.ArticuloBean;
import models.ArticuloValidation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Sergio
 * @since 23/03/2022
 * @version 1.0.0 Controller created to load model and views supporting 'articulos' 
 * (formArticulo, vistaArticulo, addArticulo, deleteArticulo, updateArticulo, listarArticulos).
 */
public class articleController {
    
    private ArticuloValidation articuloValidar;
    private JdbcTemplate jdbcTemplate;
    
    //------Copy load images on server
    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
    //---Load configuration
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; //50MB
    
    public articleController(){
        this.articuloValidar = new ArticuloValidation();
        ConectarDB conectar = new ConectarDB();
        this.jdbcTemplate = new JdbcTemplate(conectar.conectar());
    }
    
    /**
     * 
     * @return page from formArticulo.jsp which is loaded based on model from AriculoBean.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView form(){
        return new ModelAndView("views/formArticulo", "articulo", new ArticuloBean());
    }
    
    /**
     * 
     * @param a loads model created based on PersonaBean.
     * @param result based on the information filled on the form from formArticulo.jsp, 
     * it will take that result as a parameter for method ArticuloValidation.validate.
     * @return Depending on the result of the validation, it's going to generate a view 
     * loading once again the form highlinting the errors or vistaArticulo.jsp with the 
     * data taken from the form.
     */
    @RequestMapping(value="formArticulo.htm", method = RequestMethod.POST)
    public ModelAndView form(
            @ModelAttribute("articulo") ArticuloBean a,
            BindingResult result
    ){
        //It takes the result from the form and use it as a parameter form articuloValidar.validate method.
        //Depending on the result from the form it will return to the form highlinting the errors or 
        //it will take user to vistaArticulo.jsp with information from form and list it.
        this.articuloValidar.validate(a, result);
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView();
            mav.setViewName("views/formArticulo");
            mav.addObject("articulo", new ArticuloBean());
            return mav;
        }
        else{
            ModelAndView mav = new ModelAndView();
            mav.setViewName("views/vistaArticulo");
            mav.addObject("titulo", a.getTitulo());
            mav.addObject("descripcion", a.getDescripcion());
            mav.addObject("precio", a.getPrecio());
            mav.addObject("foto", a.getFoto());
            return mav;
        }
    }
    
    /**
     * 
     * @return view of listarArticulos.jsp taking 'articulos' data (datos) from DB and listing it 
     * based on PersonaBean model
     */
    @RequestMapping(value="listarArticulos.htm")
    public ModelAndView listarArticulos(){
        ModelAndView mav = new ModelAndView();
        String sql = "select * from articulos";
        List datos = this.jdbcTemplate.queryForList(sql);
        mav.setViewName("views/listarArticulos");
        mav.addObject("articulo", datos);
        return mav;
    }
    
    /**
     * 
     * @return addArticulo.jsp view taking into account ArticuloBean model for the form fields.
     */
    @RequestMapping(value="addArticulo.htm", method=RequestMethod.GET)
    public ModelAndView addArticulo(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("articulo", new ArticuloBean());
        mav.setViewName("views/addArticulo");
        return mav;
    }
    
    /**
     * 
     * @param a loads model created based on ArticuloBean.
     * @param request takes data from the form once method POST is triggered and use it as a 
     * parameter to verify if the form is MultiPart, to check the path for storing the picture 
     * and saving its path onto the DB.
     * @return view from listarArticulos.jsp loaded based on ArticuloBean model.
     */
    @RequestMapping(value="addArticulo.htm", method=RequestMethod.POST)
    public ModelAndView addArticulo(ArticuloBean a, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        //------------Load img file
        //-----Get file's path
        //String uploadFilePath = request.getSession().getServletContext().getRealPath("images/photos/");
        //-----Check if form has load attribute
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        //-----variable to go through the vector
        ArrayList<String> list = new ArrayList<>();
        if (isMultiPart){
            //file instance 
            DiskFileItemFactory file = new DiskFileItemFactory();
            //Set load parameters
            //Set memory threshold for files which will be created and stored on the directory
            file.setSizeThreshold(MEMORY_THRESHOLD);
            //Set temporary storage directory
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
            //Pass fileitem as parameter to variable
            ServletFileUpload fileUpload = new ServletFileUpload(file);
            //Set maximum size for files
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            //Set maximum request size
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);
            //Build a temp path to store loaded files
            //This is a relative path to the actual project path
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            //Create dir if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            //Create a list with values taken from the form
            List<FileItem> items = null;
            try{
               items = fileUpload.parseRequest(request);
            } catch (FileUploadException ex){
                System.out.print("Load " + ex.getMessage());
            }
            for (int i = 0; i < items.size(); i++){
                //Create fileitem var and parse it from list that covers values from form
                FileItem fileItem = (FileItem) items.get(i);
                //Check which var comes from the form
                if (!fileItem.isFormField()){
                    String fileName = new File(fileItem.getName()).getName();
                    String filePath = uploadPath + File.separator + list.get(0) + "_" + fileName;
                    File uploadFile = new File(filePath);
                    //Get file's name
                    String nameFile = ("images/photos/" + list.get(0) +  "_" + fileName);
                    try{
                        //Store file's name on disk (tomcat's dir)
                        fileItem.write(uploadFile);
                        a.setFoto(nameFile);
                        a.setOldfoto(nameFile);
                    } catch (Exception ex){
                        System.out.printf("write " + ex.getMessage());
                    }
                } else{
                    list.add(fileItem.getString());
                }
            }
            a.setTitulo(list.get(0));
            a.setDescripcion(list.get(1));
            a.setPrecio(Integer.parseInt(list.get(2)));
        }
        
        String sql = "insert into articulos (titulo, descripcion, precio, foto, oldfoto) "
                + "values (?,?,?,?,?)";
        jdbcTemplate.update(sql, a.getTitulo(), a.getDescripcion(), a.getPrecio(), a.getFoto(), a.getOldfoto());
        mav.setViewName("redirect:/listarArticulos.htm");
        return mav;
    }
    
    /**
     * 
     * @param request is used as a parameter which takes the id from the article we want to delete,
     * it also takes the path of the image we need to delete prior to deleting the article from the DB.
     * @return listarUsuarios.jsp view once the image and the user has been deleted from the DB.
     */
    @RequestMapping(value="deleteArticulo.htm")
    public ModelAndView deleteArticulo(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        ArticleDao articleDao = new ArticleDao();
        int id = Integer.parseInt(request.getParameter("id"));
        //Capture file's path to use it as a parameter so we can delete both article and picture
        String deletePath = request.getServletContext().getRealPath("") + File.separator;
        String foto = request.getParameter("foto");
        //Method to delete user and photo imported from ArticleDao
        articleDao.deletePhoto(foto, deletePath, id);
        mav.setViewName("redirect:/listarArticulos.htm");
        return mav;
    }
    
    /**
     * 
     * @param request it takes 'id' parameter from listarArticulos.jsp so we know whcih article we
     * want to modify.
     * @return updateArticulo.jsp view with preloaded information from the article we want to modify, 
     * which is taken from a sql query using the 'id' taken from the request.
     */
    @RequestMapping(value="updateArticulo.htm", method=RequestMethod.GET)
    public ModelAndView updateArticulo(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        int id = Integer.parseInt(request.getParameter("id"));
        //Using the id taken from the previous view, we make a sql query to preload data from the article
        ArticuloBean a = consultaArticuloxId(id);
        mav.addObject("articulo", a);
        mav.setViewName("views/updateArticulo");
        return mav;
    }
    
    /**
     * 
     * @param id in order to preload article data onto updateArticle.jsp we make a sql query 
     * so we need article id as a parameter to make that query.
     * @return ArticuloBean model data taken from the sql query adn send it to the model and view 
     * from updateArticulo(GET) method to preload article data onto the form fields.
     */
    public ArticuloBean consultaArticuloxId(int id){
        ArticuloBean a = new ArticuloBean();
        String sql = "select * from articulos where id_articulo = " + id;
        return (ArticuloBean)jdbcTemplate.query(sql, new ResultSetExtractor<ArticuloBean>()
        {
            public ArticuloBean extractData (ResultSet rs) throws SQLException, DataAccessException{
                if(rs.next()){
                    a.setId(rs.getInt("id_articulo"));
                    a.setTitulo(rs.getString("titulo"));
                    a.setDescripcion(rs.getString("descripcion"));
                    a.setPrecio(rs.getInt("precio"));
                    a.setFoto(rs.getString("foto"));
                }
            return a;
            }
        }
        );
    }
    
    /**
     * 
     * @param a loads model created based on ArticuloBean.
     * @param request takes data from the form once method POST is triggered and use it as a 
     * parameter to verify if the form is MultiPart, to check the path for storing new picture, 
     * deleting previous one and updating fields onto the DB.
     * @return view from listarArticulos.jsp loaded based on ArticuloBean model which will reflect 
     * data updated.
     */
    @RequestMapping(value="updateArticulo.htm", method=RequestMethod.POST)
    public ModelAndView updateArticulo(ArticuloBean a, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        ArticleDao articleDao = new ArticleDao();
        ArrayList<String> list = new ArrayList<>();
        //Check if load attribute is configured on form
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        //Check id user file has been modified
        DiskFileItemFactory file = new DiskFileItemFactory();
        //Pass file item as parameter to variable
        ServletFileUpload fileUpload = new ServletFileUpload(file);
        List<FileItem> items = null;
        try{
            items = fileUpload.parseRequest(request);
            for (int i = 0; i < items.size(); i++){
                FileItem fileItem = (FileItem) items.get(i);
                list.add(fileItem.getString());
            }
        } catch (FileUploadException ex){
            System.out.print("Error en carga de imagen articleController/updateArticulo " + ex.getMessage());
        }
        if (list.get(3).isEmpty() || list.get(3).equals("") || list.get(3).equals(null)){
            articleDao.updateArticleNoPhoto(a, items);
        } else {
            articleDao.updateArticleWithPhoto(a, isMultiPart, request, items);
        }
        mav.setViewName("redirect:/listarArticulos.htm");
        return mav;
    }
}
