package carrie.bean;

import java.util.Date;
import java.util.List;

public class Product {
	private int id;
	private String name;
	private String subTitle;
	private float originalPrice;
	private float promotePrice;
	private int stock;
	private Category category;
	private Date createDate;
	private Picture firstPic;
	private List<Picture> detailPics;
	private List<Picture> simplePics;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public float getOriginalPrice() {
		return originalPrice;
	}
	
	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}
	
	public float getPromotePrice() {
		return promotePrice;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setDetailPics(List<Picture> detailPics) {
		this.detailPics = detailPics;
	}
	
	public List<Picture> getDetailPics(){
		return detailPics;
	}
	
	public void setSimplePics(List<Picture> simplePics) {
		this.simplePics = simplePics;
	}
	
	public List<Picture> getSimplePics(){
		return simplePics;
	}
	
	public void setFirstPic(Picture firstPic) {
		this.firstPic = firstPic;
	}
	
	public Picture getFirstPic() {
		return firstPic;
	}
}
