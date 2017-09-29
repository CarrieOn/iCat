package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import carrie.bean.Category;
import carrie.util.DBUtil;

public class CategoryDAO {
	public void add(Category bean) {		  
        String sql = "insert into category values(null,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setString(1, bean.getName());  
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
  
    public void update(Category bean) { 
        String sql = "update category set name= ? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setString(1, bean.getName());
            ps.setInt(2, bean.getId());
  
            ps.execute(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
  
    public void delete(int id) {  
    	String sql = "delete from category where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {   
            s.execute(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();
        }
    }
  
    public Category get(int id) {
        Category bean = null;
        String sql = "select * from category where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {            
            ResultSet rs = s.executeQuery(sql);
  
            if (rs.next()) {
                bean = new Category();
                String name = rs.getString(2);
                bean.setName(name);
                bean.setId(id);
            }  
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return bean;
    }
    
    public int getTotal() {
    	int total = 0;
    	String sql = "select count(*) from category";
    	try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) { 
    		ResultSet rs = s.executeQuery(sql);
    		if(rs.next())
    			total = rs.getInt(1);
    	}catch (SQLException e) {  
            e.printStackTrace();
        }
    	return total;
    }
    
    public List<Category> list(){
    	return this.list(0, Integer.MAX_VALUE);
    }
    
    public List<Category> list(int start, int count) {
    	List<Category> beans = new ArrayList<>();
    	String sql = "select * from category order by id desc limit ?, ?";
    	try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setInt(1, start);  
            ps.setInt(2, count);   
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	Category bean = new Category();
            	bean.setId(rs.getInt("id"));
            	bean.setName(rs.getString("name"));
            	beans.add(bean);
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    	return beans;
    }
}
