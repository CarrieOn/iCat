package carrie.bean;

public class OrderUser {
	private int id;
	private int number;
	private User user;
	private Product product;
	private OrderAdmin orderAdmin;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setOrderAdmin(OrderAdmin orderAdmin) {
		this.orderAdmin = orderAdmin;
	}
	
	public OrderAdmin getOrderAdmin() {
		return orderAdmin;
	}
}
