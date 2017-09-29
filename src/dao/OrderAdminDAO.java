package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import carrie.bean.OrderAdmin;
import carrie.bean.User;
import carrie.util.DBUtil;
import carrie.util.DateUtil;

public class OrderAdminDAO {
	public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";
    
    public void add(OrderAdmin bean) {   	 
        String sql = "insert into order_admin values(null,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, bean.getBuyer());
            ps.setString(2, bean.getMobile());
            ps.setString(3, bean.getAddress());
            ps.setString(4, bean.getZipcode());
            ps.setString(5, bean.getMessage());
            ps.setString(6, bean.getStatus());
            
            if(bean.getCreateDate() == null)
            	ps.setTimestamp(7,  null);
            else
            	ps.setTimestamp(7,  DateUtil.d2t(bean.getCreateDate()));
            
            if(bean.getPayDate() == null)
            	ps.setTimestamp(8,  null);
            else
            	ps.setTimestamp(8,  DateUtil.d2t(bean.getPayDate()));
            
            if(bean.getDeliveryDate() == null)
            	ps.setTimestamp(9,  null);
            else
            	ps.setTimestamp(9,  DateUtil.d2t(bean.getDeliveryDate()));
            
            if(bean.getConfirmDate() == null)
            	ps.setTimestamp(10,  null);
            else
            	ps.setTimestamp(10,  DateUtil.d2t(bean.getConfirmDate()));
            
            ps.setInt(11, bean.getUser().getId());            
 
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
  
    public void update(OrderAdmin bean) { 
        String sql = "update order_admin set address= ?,buyer=?,mobile=?,message=? ,createDate = ? , payDate =? , deliveryDate =?, confirmDate = ? , zipcode =?, uid=?, status=? where id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, bean.getAddress());
            ps.setString(2, bean.getBuyer());
            ps.setString(3, bean.getMobile());
            ps.setString(4, bean.getMessage());
            
            if(bean.getCreateDate() == null)
            	ps.setTimestamp(5,  null);
            else
            	ps.setTimestamp(5,  DateUtil.d2t(bean.getCreateDate()));
            
            if(bean.getPayDate() == null)
            	ps.setTimestamp(6,  null);
            else
            	ps.setTimestamp(6,  DateUtil.d2t(bean.getPayDate()));
            
            if(bean.getDeliveryDate() == null)
            	ps.setTimestamp(7,  null);
            else
            	ps.setTimestamp(7,  DateUtil.d2t(bean.getDeliveryDate()));
            
            if(bean.getConfirmDate() == null)
            	ps.setTimestamp(8,  null);
            else
            	ps.setTimestamp(8,  DateUtil.d2t(bean.getConfirmDate()));
            
            ps.setString(9, bean.getZipcode());
            ps.setInt(10, bean.getUser().getId());
            ps.setString(11, bean.getStatus());
            ps.setInt(12, bean.getId());
            ps.execute();
  
        } catch (SQLException e) { 
            e.printStackTrace();
        } 
    }
  
    public void delete(int id) {
    	String sql = "delete from order_admin where id = " + id; 
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {             
            s.execute(sql);
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
  
    public OrderAdmin get(int id) {
    	OrderAdmin bean = new OrderAdmin();
    	String sql = "select * from order_admin where id = " + id;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String zipcode =rs.getString("zipcode");
                String address = rs.getString("address");
                String Buyer = rs.getString("Buyer");
                String mobile = rs.getString("mobile");
                String message = rs.getString("message");
                String status = rs.getString("status");
                int uid =rs.getInt("uid");
                
                Date createDate;
                if(rs.getTimestamp("createDate") == null)
                	createDate = null;
                else
                	createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                
                Date payDate;
                if(rs.getTimestamp("payDate") == null)
                	payDate = null;
                else
                	payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                
                Date deliveryDate;
                if(rs.getTimestamp("deliveryDate") == null)
                	deliveryDate = null;
                else
                	deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                
                Date confirmDate;
                if(rs.getTimestamp("confirmDate") == null)
                	confirmDate = null;
                else
                	confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                 
                bean.setZipcode(zipcode);
                bean.setAddress(address);
                bean.setBuyer(Buyer);
                bean.setMobile(mobile);
                bean.setMessage(message);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);                 
                bean.setId(id);
            }  
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return bean;
    }
  
    public List<OrderAdmin> list() {
        return list(0, Short.MAX_VALUE);
    }
  
    public List<OrderAdmin> list(int start, int count) {
        List<OrderAdmin> beans = new ArrayList<>();  
        String sql = "select * from order_admin order by id limit ?,?";  
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {  
            ps.setInt(1, start);
            ps.setInt(2, count);  
            ResultSet rs = ps.executeQuery();
  
            while (rs.next()) {
            	OrderAdmin bean = new OrderAdmin();
            	int id = rs.getInt(1);
                String zipcode =rs.getString("zipcode");
                String address = rs.getString("address");
                String buyer = rs.getString("buyer");
                String mobile = rs.getString("mobile");
                String message = rs.getString("message");
                String status = rs.getString("status");
                
                Date createDate;
                if(rs.getTimestamp("createDate") == null)
                	createDate = null;
                else
                	createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                
                Date payDate;
                if(rs.getTimestamp("payDate") == null)
                	payDate = null;
                else
                	payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                
                Date deliveryDate;
                if(rs.getTimestamp("deliveryDate") == null)
                	deliveryDate = null;
                else
                	deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                
                Date confirmDate;
                if(rs.getTimestamp("confirmDate") == null)
                	confirmDate = null;
                else
                	confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                
                int uid =rs.getInt("uid");
                User user = new UserDAO().get(uid);   
                
                bean.setId(id);
                bean.setZipcode(zipcode);
                bean.setAddress(address);
                bean.setBuyer(buyer);
                bean.setMobile(mobile);
                bean.setMessage(message);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                bean.setUser(user);
                bean.setStatus(status);
                beans.add(bean);
            }
        } catch (SQLException e) {  
            e.printStackTrace();
        }
        return beans;
    }
     
    public List<OrderAdmin> list(int uid,String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }
      
    public List<OrderAdmin> list(int uid, String excludedStatus, int start, int count) {
        List<OrderAdmin> beans = new ArrayList<>();         
        String sql = "select * from order_admin where uid = ? and status != ? order by id desc limit ?,? ";         
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {           
            ps.setInt(1, uid);
            ps.setString(2, excludedStatus);
            ps.setInt(3, start);
            ps.setInt(4, count);
             
            ResultSet rs = ps.executeQuery();
             
            while (rs.next()) {
            	OrderAdmin bean = new OrderAdmin();
                String zipcode =rs.getString("zipcode");
                String address = rs.getString("address");
                String buyer = rs.getString("buyer");
                String mobile = rs.getString("mobile");
                String message = rs.getString("message");
                String status = rs.getString("status");
                
                Date createDate;
                if(rs.getTimestamp("createDate") == null)
                	createDate = null;
                else
                	createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                
                Date payDate;
                if(rs.getTimestamp("payDate") == null)
                	payDate = null;
                else
                	payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                
                Date deliveryDate;
                if(rs.getTimestamp("deliveryDate") == null)
                	deliveryDate = null;
                else
                	deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                
                Date confirmDate;
                if(rs.getTimestamp("confirmDate") == null)
                	confirmDate = null;
                else
                	confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setZipcode(zipcode);
                bean.setAddress(address);
                bean.setBuyer(buyer);
                bean.setMobile(mobile);
                bean.setMessage(message);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setStatus(status);
                bean.setUser(user);
                beans.add(bean);
            }
        } catch (SQLException e) {           
            e.printStackTrace();
        }
        return beans;
    }
    
    public List<OrderAdmin> list(int uid) {
        return list(uid,0, Short.MAX_VALUE);
    }
      
    public List<OrderAdmin> list(int uid, int start, int count) {
        List<OrderAdmin> beans = new ArrayList<>();         
        String sql = "select * from order_admin where uid = ? order by id desc limit ?,? ";         
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {           
            ps.setInt(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
             
            ResultSet rs = ps.executeQuery();
             
            while (rs.next()) {
            	OrderAdmin bean = new OrderAdmin();
                String zipcode =rs.getString("zipcode");
                String address = rs.getString("address");
                String buyer = rs.getString("buyer");
                String mobile = rs.getString("mobile");
                String message = rs.getString("message");
                String status = rs.getString("status");
                
                Date createDate;
                if(rs.getTimestamp("createDate") == null)
                	createDate = null;
                else
                	createDate = DateUtil.t2d(rs.getTimestamp("createDate"));
                
                Date payDate;
                if(rs.getTimestamp("payDate") == null)
                	payDate = null;
                else
                	payDate = DateUtil.t2d(rs.getTimestamp("payDate"));
                
                Date deliveryDate;
                if(rs.getTimestamp("deliveryDate") == null)
                	deliveryDate = null;
                else
                	deliveryDate = DateUtil.t2d(rs.getTimestamp("deliveryDate"));
                
                Date confirmDate;
                if(rs.getTimestamp("confirmDate") == null)
                	confirmDate = null;
                else
                	confirmDate = DateUtil.t2d(rs.getTimestamp("confirmDate"));
                
                
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setZipcode(zipcode);
                bean.setAddress(address);
                bean.setBuyer(buyer);
                bean.setMobile(mobile);
                bean.setMessage(message);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setStatus(status);
                bean.setUser(user);
                beans.add(bean);
            }
        } catch (SQLException e) {           
            e.printStackTrace();
        }
        return beans;
    }
}
