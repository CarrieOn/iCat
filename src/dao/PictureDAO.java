package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import carrie.bean.Picture;
import carrie.bean.Product;
import carrie.util.DBUtil;

public class PictureDAO {
	public static final String type_simple = "type_simple";
    public static final String type_detail = "type_detail";
    
    public void add(Picture bean) {
    	 
        String sql = "insert into picture values(null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, bean.getProduct().getId());
            ps.setString(2, bean.getType());
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
  
    public void update(Picture bean) { 
    }
  
    public void delete(int id) {
    	String sql = "delete from picture where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) { 
            s.execute(sql);  
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    public Picture get(int id) {
        Picture bean = new Picture();
        String sql = "select * from picture where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            ResultSet rs = s.executeQuery(sql);
  
            if (rs.next()) {
                int pid = rs.getInt("pid");
                String type = rs.getString("type");
                Product product = new ProductDAO().get(pid);
                bean.setProduct(product);
                bean.setType(type);
                bean.setId(id);
            }  
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return bean;
    }
    
    public List<Picture> list(Product p, String type){
    	return list(p, type, 0,Short.MAX_VALUE);
    }
    
    public List<Picture> list(Product p, String type, int start, int count){
    	List<Picture> beans = new ArrayList<>();
    	String sql = "select * from picture where pid = ? and type = ? order by id limit ?, ?";
    	try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){		
    		ps.setInt(1, p.getId());
    		ps.setString(2, type);
    		ps.setInt(3, start);
    		ps.setInt(4, count);
    		ResultSet rs = ps.executeQuery();
    		
			while(rs.next()){
				Picture bean = new Picture();
				bean.setId(rs.getInt("id"));
				bean.setType(rs.getString("type"));
				int pid = rs.getInt(2);
				ProductDAO productDAO = new ProductDAO();
				Product product = productDAO.get(pid);
				bean.setProduct(product);
				beans.add(bean);
			}   		
    	}catch (SQLException e) { 
            e.printStackTrace();
        }
    	return beans;
    }
}
