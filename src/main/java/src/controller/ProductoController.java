package src.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import src.dao.ProductoDAO;
import com.aprendec.model.Producto;

/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = {"/productos"})
public class ProductoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        String opcion = request.getParameter("opcion");

        if (opcion.equals("crear")) {
            System.out.println("Usted a presionado la opcion crear");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
            requestDispatcher.forward(request, response);
        } else if (opcion.equals("listar")) {

            try {
                ProductoDAO productoDAO = new ProductoDAO();
                List<Producto> lista = new ArrayList<>();
                lista = productoDAO.obtenerProductos(); // TODO Auto-generated catch block
                for (Producto producto : lista) {
                    System.out.println(producto);
                }
                request.setAttribute("lista", lista);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
                requestDispatcher.forward(request, response);
                
                System.out.println("Usted a presionado la opcion listar");
            } catch (SQLException ex) {
                System.getLogger(ProductoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } else if (opcion.equals("meditar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                System.out.println("Editar id: " + id);
                ProductoDAO productoDAO = new ProductoDAO();
                Producto p = new Producto();
                p = productoDAO.obtenerProducto(id); // TODO Auto-generated catch block
                System.out.println(p);
                request.setAttribute("producto", p);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.getLogger(ProductoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        } else if (opcion.equals("eliminar")) {
            try {
                ProductoDAO productoDAO = new ProductoDAO();
                int id = Integer.parseInt(request.getParameter("id"));
                productoDAO.eliminar(id); // TODO Auto-generated catch block
                System.out.println("Registro eliminado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.getLogger(ProductoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        }
        // response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String opcion = request.getParameter("opcion");
        Date fechaActual = new Date();

        if (opcion.equals("guardar")) {
            try {
                ProductoDAO productoDAO = new ProductoDAO();
                Producto producto = new Producto();
                producto.setNombre(request.getParameter("nombre"));
                producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
                producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                producto.setFechaCrear(new java.sql.Date(fechaActual.getTime()));
                productoDAO.guardar(producto); // TODO Auto-generated catch block
                System.out.println("Registro guardado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.getLogger(ProductoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        } else if (opcion.equals("editar")) {
            try {
                Producto producto = new Producto();
                ProductoDAO productoDAO = new ProductoDAO();
                
                producto.setId(Integer.parseInt(request.getParameter("id")));
                producto.setNombre(request.getParameter("nombre"));
                producto.setCantidad(Double.parseDouble(request.getParameter("cantidad")));
                producto.setPrecio(Double.parseDouble(request.getParameter("precio")));
                producto.setFechaActualizar(new java.sql.Date(fechaActual.getTime()));
                productoDAO.editar(producto); // TODO Auto-generated catch block
                System.out.println("Registro editado satisfactoriamente...");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                requestDispatcher.forward(request, response);
            } catch (SQLException ex) {
                System.getLogger(ProductoController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

        // doGet(request, response);
    }

}
