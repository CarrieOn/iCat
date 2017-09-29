package carrie.bean;

import java.util.Date;
import java.util.List;

public class OrderAdmin {
	private int id;
	private String buyer;
	private String mobile;
	private String address;
	private String zipcode;
	private String message;
	private String status;
	private Date createDate;
	private Date payDate;
	private Date deliveryDate;
	private Date confirmDate;
	private User user;
	private List<OrderUser> orderUser;
	private int totalNumber;
	private float totalPrice;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public String getBuyer() {
		return buyer;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	public Date getPayDate() {
		return payDate;
	}
	
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	public Date getConfirmDate() {
		return confirmDate;
	}
	
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	public int getTotalNumber() {
		return totalNumber;
	}
	
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public float getTotalPrice() {
		return totalPrice;
	}
	
	public void setOrderUser(List<OrderUser> orderUser) {
		this.orderUser = orderUser;
	}
	
	public List<OrderUser> getOrderUser(){
		return orderUser;
	}
}
