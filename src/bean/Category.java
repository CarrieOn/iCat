package carrie.bean;

import java.util.List;

public class Category {
	private int id;
	private String name;
	private List<List<Product>> productRows;
	private List<Product> products;
	
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
	
	public void setProductRows(List<List<Product>> productRows) {
		this.productRows = productRows;
	}
	
	public List<List<Product>> getProductRows(){
		return productRows;
	}
	
	public void setProducts(List<Product> products){
		this.products = products;
	}
	
	public List<Product> getProducts(){
		return products;
	}	

}
