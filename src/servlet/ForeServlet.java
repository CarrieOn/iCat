package carrie.servlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import carrie.bean.Category;
import carrie.bean.OrderAdmin;
import carrie.bean.OrderUser;
import carrie.bean.Picture;
import carrie.bean.Product;
import carrie.bean.Review;
import carrie.bean.User;
import carrie.comparator.ProductDateComparator;
import carrie.comparator.ProductPriceComparator;
import carrie.dao.OrderAdminDAO;
import carrie.dao.PictureDAO;

public class ForeServlet extends BaseForeServlet{
	private static final long serialVersionUID = 1L;
	
	public String home(HttpServletRequest request, HttpServletResponse response) {
		List<Category> cs = categoryDAO.list();
		productDAO.classify(cs);
		productDAO.classifyInRows(cs);
		request.setAttribute("cs", cs);
		return "user/home.jsp";
	}
	
	public String search(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		List<Product> ps_search = productDAO.search(keyword);
		request.setAttribute("ps_search", ps_search);
		return "user/searchResult.jsp";
	}
	
	public String category(HttpServletRequest request, HttpServletResponse response) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		String sort = request.getParameter("sort");
		Category c = categoryDAO.get(cid);
		productDAO.classify(c);
		
		if(sort != null) {
			switch(sort) {
				case "date":
					Collections.sort(c.getProducts(), new ProductDateComparator());
					break;
				case "price":
					Collections.sort(c.getProducts(), new ProductPriceComparator());
					break;
				default:
					break;
			}
		}
		
		request.setAttribute("c", c);
		return "user/category.jsp";
	}
	
	public String product(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.get(pid);
		
		List<Picture> simplePics = pictureDAO.list(product, PictureDAO.type_simple);
		List<Picture> detailPics = pictureDAO.list(product, PictureDAO.type_detail);
		product.setSimplePics(simplePics);
		product.setDetailPics(detailPics);
		if(product.getFirstPic() == null)
			productDAO.setFirstPic(product);
		List<Review> reviews = reviewDAO.list(pid);
		
		request.setAttribute("product", product);
		request.setAttribute("reviews", reviews);	
		return "user/product.jsp";
	}
	
	public String register(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		boolean exist = userDAO.isExist(name);
		if(exist) {
			request.setAttribute("msg", "User name has already be taken, sorry about that.");
			return "user/register.jsp";
		}
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		userDAO.add(user);
		return "user/registerSuccess.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		
		User user = userDAO.get(name, password);
		if(user == null) {
			request.setAttribute("msg", "Invalid user name/password");
			return "login.jsp";
		}
		request.getSession().setAttribute("user", user);
		return "@forehome";
	}
	
	public String loginAjax(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User user = userDAO.get(name, password);
		if(user == null) return "%fail";
		request.getSession().setAttribute("user", user);
		return "%success";
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		return "@forehome";
	}
	
	public String checkLogin(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) return "%fail";
		else return "%success";
	}
	
	public String buyone(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num")); 
		Product p_buyone = productDAO.get(pid);
		if(p_buyone.getFirstPic() == null)
			productDAO.setFirstPic(p_buyone);
		
		User user = (User) request.getSession().getAttribute("user");
		List<OrderUser> os_user = orderUserDAO.listByUser(user.getId());
		boolean found = false;
		int oid_user = 0;
		for(OrderUser o_user : os_user) {
			if(o_user.getProduct().getId() == pid) {
				o_user.setNumber(o_user.getNumber() + num);
				orderUserDAO.update(o_user);
				found = true;
				oid_user = o_user.getId();
			}
		}
		
		if(!found) {
			OrderUser o_user = new OrderUser();
			o_user.setNumber(num);
			o_user.setProduct(p_buyone);
			o_user.setUser(user);
			orderUserDAO.add(o_user);
			oid_user = o_user.getId();
		}
		
		return "@forebuy?oid_user=" + oid_user;
	}
	
	public String buy(HttpServletRequest request, HttpServletResponse response) {
		String[] oids_user = request.getParameterValues("oid_user");
		List<OrderUser> os_user = new ArrayList<>();
		
		float totalPrice = 0;
		for(String tmp : oids_user) {
			int oid_user = Integer.parseInt(tmp);
			OrderUser o_user = orderUserDAO.get(oid_user);
			totalPrice += o_user.getProduct().getPromotePrice() * o_user.getNumber();
			os_user.add(o_user);
			if(o_user.getProduct().getFirstPic() == null)
				productDAO.setFirstPic(o_user.getProduct());
		}		
		totalPrice = totalPrice * 100;
		request.getSession().setAttribute("os_user", os_user);
		request.setAttribute("totalPrice", (int)totalPrice);
		return "user/buy.jsp";
	}
	
	public String createOrder(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		@SuppressWarnings("unchecked")
		List<OrderUser> os_user = (List<OrderUser>) request.getSession().getAttribute("os_user");
		if(os_user == null)
			return "login.jsp";
		
		String address = request.getParameter("address");
	    String zipcode = request.getParameter("post");
	    String receiver = request.getParameter("receiver");
	    String mobile = request.getParameter("mobile");
	    String message = request.getParameter("userMessage");
	    
	    OrderAdmin o_admin = new OrderAdmin();
	    o_admin.setAddress(address);
	    o_admin.setZipcode(zipcode);
	    o_admin.setBuyer(receiver);
	    o_admin.setMobile(mobile);
	    o_admin.setMessage(message);
	    o_admin.setUser(user);
	    o_admin.setCreateDate(new Date());
	    o_admin.setStatus(OrderAdminDAO.waitPay);
	    orderAdminDAO.add(o_admin);
	    
	    float total = 0;
	    for(OrderUser o_user : os_user) {
	    	o_user.setOrderAdmin(o_admin);
	    	orderUserDAO.update(o_user);
	    	total = o_user.getProduct().getPromotePrice() * o_user.getNumber();
	    }	    
		return "@forepay?oid_admin=" + o_admin.getId() + "&total=" + total;
	}
	
	public String pay(HttpServletRequest request, HttpServletResponse response) {
		return "user/pay.jsp";
	}
	
	public String payed(HttpServletRequest request, HttpServletResponse response) {
		int oid_admin = Integer.parseInt(request.getParameter("oid_admin"));
		OrderAdmin o_admin = orderAdminDAO.get(oid_admin);
		o_admin.setStatus(OrderAdminDAO.waitDelivery);
		o_admin.setPayDate(new Date());
		orderAdminDAO.update(o_admin);
		
		request.setAttribute("o_admin", o_admin);
		return "user/payed.jsp";
	}
	
	public String confirmPay(HttpServletRequest request, HttpServletResponse response) {
		int oid_admin = Integer.parseInt(request.getParameter("oid_admin"));
		OrderAdmin o_admin = orderAdminDAO.get(oid_admin);
		o_admin.setStatus(OrderAdminDAO.waitReview);
		o_admin.setConfirmDate(new Date());
		orderAdminDAO.update(o_admin);
		
		return "@forebought";
	}
	
	public String addCart(HttpServletRequest request, HttpServletResponse response) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		Product p = productDAO.get(pid);
		User user = (User) request.getSession().getAttribute("user");
		
		boolean found = false;
		List<OrderUser> os_user = orderUserDAO.listByUser(user.getId());
		for(OrderUser o_user : os_user) {
			if(o_user.getProduct().getId() == pid) {
				o_user.setNumber(o_user.getNumber() + num);
				orderUserDAO.update(o_user);
				found = true;
				break;
			}
		}
		
		if( ! found) {
			OrderUser o_user = new OrderUser();
			o_user.setNumber(num);
			o_user.setProduct(p);
			o_user.setUser(user);
			orderUserDAO.add(o_user);
		}
		
		return "%success";
	}
	
	public String cart(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderUser> os_user_cart = orderUserDAO.listByUser(user.getId());
		for(OrderUser o_user_cart : os_user_cart) {
			if(o_user_cart.getProduct().getFirstPic() == null)
				productDAO.setFirstPic(o_user_cart.getProduct());
		}
		request.setAttribute("os_user_cart", os_user_cart);
		return "user/cart.jsp";
	}
	
	public String changeOrderUser(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null)
			return "%fail";
		
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		List<OrderUser> os_user = orderUserDAO.listByUser(user.getId());
		for(OrderUser o_user : os_user) {
			if(o_user.getProduct().getId() == pid) {
				o_user.setNumber(num);
				orderUserDAO.update(o_user);
				break;
			}
		}
		return "%success";
	}
	
	public String deleteOrderUser(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null)
			return "login.jsp";
		int oid_user = Integer.parseInt(request.getParameter("oid_user"));
		orderUserDAO.delete(oid_user);
		return "@forecart";
	}
	
	public String bought(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderAdmin> os_admin_bought = orderAdminDAO.list(user.getId());
		orderUserDAO.fill(os_admin_bought);
		
		request.setAttribute("os_admin_bought", os_admin_bought);
		return "user/bought.jsp";
	}
	
	public String review(HttpServletRequest request, HttpServletResponse response) {
		int oid_admin = Integer.parseInt(request.getParameter("oid_admin"));
		OrderAdmin o_admin = orderAdminDAO.get(oid_admin);
		o_admin.setStatus("done");
		orderAdminDAO.update(o_admin);
		
		orderUserDAO.fill(o_admin);
		Product p = o_admin.getOrderUser().get(0).getProduct();
		List<Review> reviews = reviewDAO.list(p.getId());
		if(p.getFirstPic() == null)
			productDAO.setFirstPic(p);
		
		request.setAttribute("p", p);
		request.setAttribute("o_admin", o_admin);
		request.setAttribute("reviews", reviews);
		return "user/review.jsp";
	}
	
	public String submitReview(HttpServletRequest request, HttpServletResponse response) {
		int oid_admin = Integer.parseInt(request.getParameter("oid_admin"));
		int pid = Integer.parseInt(request.getParameter("pid"));
		User user = (User) request.getSession().getAttribute("user");
		Product product = productDAO.get(pid);
		String content = request.getParameter("content");
		content = HtmlUtils.htmlEscape(content);
		
		Review review = new Review();
		review.setContent(content);
		review.setCreateDate(new Date());
		review.setUser(user);
		review.setProduct(product);
		reviewDAO.add(review);
		
		return "@forereview?oid_admin=" + oid_admin;
	}
}
