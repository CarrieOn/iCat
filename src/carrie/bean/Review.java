package carrie.bean;

import java.util.Date;

public class Review {
	private int id;
	private String content;
	private Date createDate;
	private User user;
	private Product product;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getCreateDate() {
		return createDate;
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
}
