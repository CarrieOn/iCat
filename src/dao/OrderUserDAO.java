package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import carrie.bean.OrderAdmin;
import carrie.bean.OrderUser;
import carrie.bean.Product;
import carrie.bean.User;
import carrie.util.DBUtil;

public class OrderUserDAO {
	public void add(OrderUser bean) {		 
        String sql = "insert into order_user values(null,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
        	ps.setInt(1, bean.getUser().getId());
            ps.setInt(2, bean.getProduct().getId());
            if(null == bean.getOrderAdmin())
                ps.setInt(3, -1);
            else
                ps.setInt(3, bean.getOrderAdmin().getId());            
            ps.setInt(4, bean.getNumber());
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
  
    public void update(OrderUser bean) { 
        String sql = "update order_user set pid= ?, oid=?, uid=?,number=?  where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, bean.getProduct().getId());
            if(null==bean.getOrderAdmin())
                ps.setInt(2, -1);
            else
                ps.setInt(2, bean.getOrderAdmin().getId());              
            ps.setInt(3, bean.getUser().getId());
            ps.setInt(4, bean.getNumber());
             
            ps.setInt(5, bean.getId());
            ps.execute();  
        } catch (SQLException e) {  
            e.printStackTrace();
        }  
    }
  
    public void delete(int id) {
    	String sql = "delete from order_user where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) { 
            s.execute(sql);  
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
  
    public OrderUser get(int id) {
    	OrderUser bean = new OrderUser();
    	String sql = "select * from order_user where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) { 
            ResultSet rs = s.executeQuery(sql);
  
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                 
                if(-1!=oid){
                	OrderAdmin order= new OrderAdminDAO().get(oid);
                    bean.setOrderAdmin(order);                   
                }
                 
                bean.setId(id);
            }  
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return bean;
    }
  
    public List<OrderUser> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }
  
    public List<OrderUser> listByUser(int uid, int start, int count) {
        List<OrderUser> beans = new ArrayList<>();  
        String sql = "select * from order_user where uid = ? and oid=-1 order by id desc limit ?,? "; 
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
            	OrderUser bean = new OrderUser();
                int id = rs.getInt(1);
 
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");
                 
                Product product = new ProductDAO().get(pid);
                if(-1!=oid){
                	OrderAdmin order= new OrderAdminDAO().get(oid);
                    bean.setOrderAdmin(order);                   
                }
 
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
 
                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);                
                beans.add(bean);
            }
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return beans;
    }
    public List<OrderUser> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }
     
    public List<OrderUser> listByOrder(int oid, int start, int count) {
        List<OrderUser> beans = new ArrayList<>();         
        String sql = "select * from order_user where oid = ? order by id desc limit ?,? ";       
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {           
            ps.setInt(1, oid);
            ps.setInt(2, start);
            ps.setInt(3, count);
             
            ResultSet rs = ps.executeQuery();
             
            while (rs.next()) {
            	OrderUser bean = new OrderUser();
                int id = rs.getInt(1);
                 
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");
                 
                Product product = new ProductDAO().get(pid);
                if(-1!=oid){
                    OrderAdmin order= new OrderAdminDAO().get(oid);
                    bean.setOrderAdmin(order);                   
                }
                 
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                 
                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);                
                beans.add(bean);
            }
        } catch (SQLException e) {            
            e.printStackTrace();
        }
        return beans;
    }
    
    public void fill(List<OrderAdmin> os_admin) {
    	for(OrderAdmin o_admin : os_admin) {
    		this.fill(o_admin);
    	}
    }
 
    public void fill(OrderAdmin o_admin) {
    	List<OrderUser> os_user = this.listByOrder(o_admin.getId());
    	float totalPrice = 0;
    	int totalNumber = 0;
    	for(OrderUser o_user : os_user) {
    		totalPrice += o_user.getProduct().getPromotePrice() * o_user.getNumber();
    		totalNumber += o_user.getNumber();
    		if(o_user.getProduct().getFirstPic() == null)
    			new ProductDAO().setFirstPic(o_user.getProduct());
    	}
    	o_admin.setOrderUser(os_user);
    	o_admin.setTotalNumber(totalNumber);
    	o_admin.setTotalPrice(totalPrice);
    }
    
}
