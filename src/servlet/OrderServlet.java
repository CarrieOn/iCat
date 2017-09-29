package carrie.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carrie.bean.OrderAdmin;

public class OrderServlet extends BaseBackServlet{
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
		List<OrderAdmin> orders = orderAdminDAO.list();
		request.setAttribute("orders", orders);
		return "admin/listOrder.jsp";
	}

	public String delivery(HttpServletRequest request, HttpServletResponse response) {
		return "@admin_order_list";
	}
}
