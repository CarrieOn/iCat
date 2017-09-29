package carrie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import carrie.bean.User;
import carrie.util.DBUtil;

public class UserDAO {

	public void add(User bean) {
		String sql = "insert into user values(null, ?, ?)";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
			ps.setString(2, bean.getPassword());
			
			ps.execute();
			
			ResultSet rs = ps.getGeneratedKeys();
			if(rs.next()) {
				int id = rs.getInt("id");
				bean.setId(id);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete user where id = " + id;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			s.execute(sql);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(User bean) {
		String sql = "update user set name = ?, password = ? where id = ?";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setInt(3, bean.getId());
            
            ps.execute();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User get(int id) {
		User bean = null;
		String sql = "select * from user where id = " + id;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				bean = new User();
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
				bean.setId(id);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public User get(String name) {
		User bean = null;
		String sql = "select * from user where name = " + name;
		try(Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			ResultSet rs = s.executeQuery(sql);
			if(rs.next()) {
				bean = new User();
				bean.setName(name);
				bean.setPassword(rs.getString("password"));
				bean.setId(rs.getInt("id"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public User get(String name, String password) {
		User bean = null;
		String sql = "select * from user where name = ? and password = ?";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				bean = new User();
				bean.setName(name);
				bean.setPassword(rs.getString("password"));
				bean.setId(rs.getInt("id"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public boolean isExist(String name) {
		User user = this.get(name);
		return user != null;
	}
	
	public List<User> list(){
		return list(0, Short.MAX_VALUE);
	}
	
	public List<User> list(int start, int count){
		List<User> beans = new ArrayList<>();
		String sql = "select * from user order by id limit ?, ?";
		try(Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				User bean = new User();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
				beans.add(bean);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}
