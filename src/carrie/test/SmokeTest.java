package carrie.test;

import java.util.Date;
import java.util.List;

import carrie.bean.OrderAdmin;
import carrie.bean.Product;
import carrie.bean.Review;
import carrie.bean.User;
import carrie.dao.OrderAdminDAO;
import carrie.dao.ProductDAO;
import carrie.dao.ReviewDAO;
import carrie.dao.UserDAO;

public class SmokeTest {
	
	public static void main(String[] args) {
		//order_admin_dao_update();
		//order_admin_dao_add();
		//order_admin_dao_list();
		//review_dao_add();
	}
	
	public static void order_admin_dao_update() {
		OrderAdminDAO test = new OrderAdminDAO();
		int id = 1;
		OrderAdmin bean = test.get(id);
		bean.setStatus(OrderAdminDAO.delete);
		test.update(bean);
		System.out.println(bean.getStatus());
	}
	
	public static void order_admin_dao_add() {
		OrderAdminDAO test = new OrderAdminDAO();
		
		OrderAdmin bean = new OrderAdmin();
	    bean.setAddress("Bellevue");
	    bean.setZipcode("98002");
	    bean.setBuyer("Tom");
	    bean.setMobile("1234567");
	    bean.setMessage("nothing");
	    User user = new UserDAO().get("Tom");
	    bean.setUser(user);
	    bean.setCreateDate(null);
	    bean.setPayDate(null);
	    bean.setDeliveryDate(new Date());
	    bean.setConfirmDate(new Date());
	    bean.setStatus(OrderAdminDAO.waitPay);
	    
	    test.add(bean);
		System.out.println(bean.getConfirmDate());
	}
	
	public static void order_admin_dao_list() {
		List<OrderAdmin> orders =new OrderAdminDAO().list();
		for(OrderAdmin order : orders)
			System.out.println(order.getBuyer());
	}
	
	public static void review_dao_add() {
		Review review = new Review();
		String content = "free fast shipping";
		review.setContent(content);
		review.setCreateDate(new Date());
		User user = new UserDAO().get(2);
		review.setUser(user);
		int id = 1;
		Product product = new ProductDAO().get(id);
		review.setProduct(product);
		new ReviewDAO().add(review);
	}
}
