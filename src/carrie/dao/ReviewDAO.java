package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import carrie.bean.Product;
import carrie.bean.Review;
import carrie.bean.User;
import carrie.util.DBUtil;
import carrie.util.DateUtil;

public class ReviewDAO {
	public void add(Review bean) {		 
        String sql = "insert into review values(null,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setInt(1, bean.getUser().getId());
            ps.setInt(2, bean.getProduct().getId());
            ps.setString(3, bean.getContent());
            ps.setTimestamp(4, DateUtil.d2t(bean.getCreateDate()));
             
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
  
    public void update(Review bean) { 
        String sql = "update review set content= ?, uid=?, pid=? , createDate = ? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, bean.getContent());
            ps.setInt(2, bean.getUser().getId());
            ps.setInt(3, bean.getProduct().getId());
            ps.setTimestamp(4, DateUtil.d2t( bean.getCreateDate()) );
            ps.setInt(5, bean.getId());
            ps.execute(); 
        } catch (SQLException e) { 
            e.printStackTrace();
        }  
    }
  
    public void delete(int id) {
    	String sql = "delete from review where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) { 
            s.execute(sql);  
        } catch (SQLException e) {  
            e.printStackTrace();
        }
    }
  
    public Review get(int id) {
        Review bean = new Review();
        String sql = "select * from review where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {  
            ResultSet rs = s.executeQuery(sql);
 
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));                 
                String content = rs.getString("content");                 
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                 
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setId(id);
            }  
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return bean;
    }
  
    public List<Review> list(int pid){
    	return list(pid, 0, Short.MAX_VALUE);
    }
    		
    public List<Review> list(int pid, int start, int count) {
        List<Review> beans = new ArrayList<Review>();
        String sql = "select * from review where pid = ? order by id desc limit ?,? "; 
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setInt(1, pid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
                Review bean = new Review();
                int id = rs.getInt(1);
 
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                 
                String content = rs.getString("content");
                 
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                 
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setId(id);     
                bean.setProduct(product);
                bean.setUser(user);
                beans.add(bean);
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return beans;
    }
}
