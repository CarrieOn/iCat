package carrie.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrie.bean.Category;
import carrie.bean.Product;

public class ProductServlet extends BaseBackServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(cid);
		       
         
        String name= request.getParameter("name");
        String subTitle= request.getParameter("subTitle");
        float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        Date now = new Date();        
 
        Product p = new Product();

        p.setCategory(c);
        p.setName(name);
        p.setSubTitle(subTitle);
        p.setOriginalPrice((float)originalPrice);
        p.setPromotePrice((float)promotePrice);
        p.setStock(stock);
        p.setCreateDate(now);
         
        productDAO.add(p);
        return "@admin_product_list?cid="+cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int id =Integer.parseInt(request.getParameter("id"));		
		Product p = productDAO.get(id);
		productDAO.delete(id);		
		return "@admin_product_list?cid="+p.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		List<Product> products = productDAO.list(cid);
		request.setAttribute("products", products);
		request.setAttribute("cid", cid);
		return "admin/listProduct.jsp";
	}
}