                
package carrie.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrie.bean.Category;
import carrie.util.ImageUtil;

public class CategoryServlet extends BaseBackServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		String name = params.get("name");
		Category c = new Category();
		c.setName(name);
		categoryDAO.add(c);
		
		File picFolder = new File(request.getSession().getServletContext().getRealPath("pic/category"));
		File file = new File(picFolder, c.getId() + ".jpg");
		try {
			if(is != null && is.available() != 0) {
				try(FileOutputStream fos = new FileOutputStream(file)) {
					byte[] b = new byte[1024 * 1024];
					int len = 0;
					while( (len = is.read(b)) != -1 ) {
						fos.write(b, 0, len);
					}
					fos.flush();
					
					BufferedImage pic = ImageUtil.change2jpg(file);
					ImageIO.write(pic, "jpg", file);
				}catch(Exception e) {
		    		e.printStackTrace();
		    	}
			}
		}catch(Exception e) {
    		e.printStackTrace();
    	}
		
		return "@admin_category_list";
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));
		categoryDAO.delete(id);
		return "@admin_category_list";
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("cid"));
		Category c = categoryDAO.get(id);
		request.setAttribute("c", c);
		return "admin/editCategory.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<>();
		InputStream is = super.parseUpload(request, params);
		
		int id = Integer.parseInt(params.get("id"));
		String name = params.get("name");
		Category c = new Category();
		c.setId(id);
		c.setName(name);
		categoryDAO.update(c);
		
		File picFolder = new File(request.getSession().getServletContext().getRealPath("pic/category"));
		File file = new File(picFolder, c.getId() + ".jpg");
		try {
			if(is != null && is.available() != 0) {
				try(FileOutputStream fos = new FileOutputStream(file)) {
					byte[] b = new byte[1024 * 1024];
					int len = 0;
					while( (len = is.read(b)) != -1 ) {
						fos.write(b, 0, len);
					}
					fos.flush();
					
					BufferedImage pic = ImageUtil.change2jpg(file);
					ImageIO.write(pic, "jpg", file);
				}catch(Exception e) {
		    		e.printStackTrace();
		    	}
			}
		}catch(Exception e) {
    		e.printStackTrace();
    	}
		
		return "@admin_category_list";
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<Category> cs = categoryDAO.list();		
		request.setAttribute("cs", cs);
		return "admin/listCategory.jsp";
	}

}
