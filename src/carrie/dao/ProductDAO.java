package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import carrie.bean.Category;
import carrie.bean.Picture;
import carrie.bean.Product;
import carrie.util.DBUtil;
import carrie.util.DateUtil;

public class ProductDAO {
	public void add(Product bean) {
		 
        String sql = "insert into product values(null,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setFloat(3, bean.getOriginalPrice());
            ps.setFloat(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setInt(6, bean.getCategory().getId());
            ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            ps.execute();
  
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
  
    public void update(Product bean) {
        String sql = "update product set name= ?, subTitle=?, originalPrice=?,promotePrice=?,stock=?, cid = ?, createDate=? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) { 
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getSubTitle());
            ps.setFloat(3, bean.getOriginalPrice());
            ps.setFloat(4, bean.getPromotePrice());
            ps.setInt(5, bean.getStock());
            ps.setInt(6, bean.getCategory().getId());
            ps.setTimestamp(7, DateUtil.d2t(bean.getCreateDate()));
            ps.setInt(8, bean.getId());
            ps.execute();
  
        } catch (SQLException e) {  
            e.printStackTrace();
        }  
    }
  
    public void delete(int id) {  
    	String sql = "delete from product where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {  
            s.execute(sql); 
        } catch (SQLException e) {  
            e.printStackTrace();
        }
    }
  
    public Product get(int id) {
        Product bean = new Product();
        String sql = "select * from product where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            ResultSet rs = s.executeQuery(sql);
  
            if (rs.next()) { 
                String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float orignalPrice = rs.getFloat("originalPrice");
                float promotePrice = rs.getFloat("promotePrice");
                int stock = rs.getInt("stock");
                int cid = rs.getInt("cid");
                Date createDate = DateUtil.t2d( rs.getTimestamp("createDate"));
               
                bean.setId(id);
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setOriginalPrice(orignalPrice);
                bean.setPromotePrice(promotePrice);
                bean.setStock(stock);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                //Warning: below would lead to DB interlock since setFirstPic(Product product) calls Product get(int id).     
                //this.setFirstPic(bean);      
            } 
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return bean;
    }
    
    public List<Product> list(int cid) {
        return list(cid,0, Short.MAX_VALUE);
    }
  
    public List<Product> list(int cid, int start, int count) {
    	List<Product> beans = new ArrayList<Product>();
    	Category category = new CategoryDAO().get(cid);
    	String sql = "select * from product where cid = ? order by id limit ?, ?";
    	try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	Product bean = new Product();
            	bean.setId(rs.getInt("id"));
            	bean.setName(rs.getString("name"));
            	bean.setSubTitle(rs.getString("subTitle"));
            	bean.setOriginalPrice(rs.getFloat("originalPrice"));
            	bean.setPromotePrice(rs.getFloat("promotePrice"));
            	bean.setStock(rs.getInt("stock"));
            	bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
            	bean.setCategory(category);
            	this.setFirstPic(bean);
            	beans.add(bean);
            }
    	}catch (SQLException e) {  
            e.printStackTrace();
        }
    	return beans;
    }
    
    public List<Product> search(String keyword){
    	if(keyword == null || keyword.trim().length() == 0) return null;
    	List<Product> beans = new ArrayList<>();
    	String sql = "select * from product where name like ?";
    	
    	try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
    		ps.setString(1, "%" + keyword.trim() + "%");
    		ResultSet rs = ps.executeQuery();
    		
    		while (rs.next()) {
    			Product bean = new Product();
            	bean.setId(rs.getInt("id"));
            	bean.setName(rs.getString("name"));
            	bean.setSubTitle(rs.getString("subTitle"));
            	bean.setOriginalPrice(rs.getFloat("originalPrice"));
            	bean.setPromotePrice(rs.getFloat("promotePrice"));
            	bean.setStock(rs.getInt("stock"));
            	bean.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
            	int cid = rs.getInt("cid");
            	Category category = new CategoryDAO().get(cid);
            	bean.setCategory(category);
            	this.setFirstPic(bean);
            	beans.add(bean);
    		}
    	}catch (SQLException e) {  
            e.printStackTrace();
        }
    	return beans;
    }
    
    public void classify(List<Category> categories) {
    	for(Category category : categories) 
    		classify(category);
    }
    
    public void classify(Category category){
    	List<Product> products = this.list(category.getId());
    	category.setProducts(products);
    }
    
    public void classifyInRows(List<Category> categories) {
    	int numsPerRow = 2;
    	for(Category category : categories) {
    		List<Product> products = list(category.getId());
    		List<List<Product>> productRows = new ArrayList<>();
    		for(int start = 0; start < products.size(); start += numsPerRow) {
    			int end = start + numsPerRow;
    			if(end > products.size()) end = products.size();
    			List<Product> productOneRow = products.subList(start, end);
    			productRows.add(productOneRow);
    		}
    		category.setProductRows(productRows);
    	}
    }
    
    public void setFirstPic(Product product) {
    	List<Picture> pics = new PictureDAO().list(product, PictureDAO.type_simple);
    	if( ! pics.isEmpty())
    		product.setFirstPic(pics.get(0));
    }
}
