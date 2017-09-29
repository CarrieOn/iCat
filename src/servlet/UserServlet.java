package carrie.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrie.bean.User;

public class UserServlet extends BaseBackServlet{
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
		List<User> users = userDAO.list();
		request.setAttribute("users", users);
		return "admin/listUser.jsp";
	}

}
