package carrie.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrie.dao.CategoryDAO;
import carrie.dao.OrderAdminDAO;
import carrie.dao.OrderUserDAO;
import carrie.dao.PictureDAO;
import carrie.dao.ProductDAO;
import carrie.dao.ReviewDAO;
import carrie.dao.UserDAO;

public class BaseForeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderAdminDAO orderAdminDAO = new OrderAdminDAO();
    protected OrderUserDAO orderUserDAO = new OrderUserDAO();
    protected PictureDAO pictureDAO = new PictureDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();
    
    public void service(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String method = (String) request.getAttribute("method");
    		Method	m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class);
    		String redirect = m.invoke(this, request, response).toString();
    		
    		if(redirect.startsWith("@"))
    			response.sendRedirect(redirect.substring(1));
    		else if(redirect.startsWith("%"))
    			response.getWriter().print(redirect.substring(1));
    		else
    			request.getRequestDispatcher(redirect).forward(request, response);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
