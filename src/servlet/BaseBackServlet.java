package carrie.servlet;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import carrie.dao.CategoryDAO;
import carrie.dao.OrderAdminDAO;
import carrie.dao.OrderUserDAO;
import carrie.dao.PictureDAO;
import carrie.dao.ProductDAO;
import carrie.dao.ReviewDAO;
import carrie.dao.UserDAO;

public abstract class BaseBackServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public abstract String add(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String list(HttpServletRequest request, HttpServletResponse response) ;
     
    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderAdminDAO orderAdminDAO = new OrderAdminDAO();
    protected OrderUserDAO orderUserDAO = new OrderUserDAO();
    protected PictureDAO pictureDAO = new PictureDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();
    
    @Override
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
    
    @SuppressWarnings("rawtypes")
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
    	InputStream is = null;
    	try {
    		DiskFileItemFactory factory = new DiskFileItemFactory();
    		factory.setSizeThreshold(1024 * 1024);
    		ServletFileUpload upload = new ServletFileUpload(factory);
    		    		
			List items = upload.parseRequest(request);
    		Iterator iter = items.iterator();
    		while(iter.hasNext()) {
    			FileItem item = (FileItem) iter.next();
    			if(! item.isFormField()) {
    				is = item.getInputStream();
    			}else {
    				String paraName = item.getFieldName();
    				String paraValue = item.getString();
    				paraValue = new String(paraValue.getBytes("ISO-8859-1"), "UTF-8");
    				params.put(paraName, paraValue);
    			}
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return is;
    }
}
