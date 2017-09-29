package carrie.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrie.bean.Picture;
import carrie.bean.Product;
import carrie.dao.PictureDAO;

public class PictureServlet extends BaseBackServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	public String add(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		return null;
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
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.get(pid);
		List<Picture> pics_simple = pictureDAO.list(product, PictureDAO.type_simple);
		List<Picture> pics_detail = pictureDAO.list(product, PictureDAO.type_detail);
		
		request.setAttribute("product", product);
		request.setAttribute("pics_simple", pics_simple);
		request.setAttribute("pics_detail", pics_detail);
		return "admin/listPicture.jsp";
	}

}
