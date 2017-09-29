# iCat
Some notes for the __iCat__ java web application.

## Database

1. __product__ : _first of all, we need products to sell_

	* name
	* price
	* stock
	* belongs to a certain category
	* one product contains many pictures

2. __picture__ : _products needs pictures to show_

	* name
	* belongs to a certain product

3. __category__ : _products better be categorized_

	* name
	* one category contains many products

4. __user__ : _we need customers_

	* name
	* password

5. __order_user__ : _track order for user_

	* which user has ordered
	* which product to buy
	* how many to buy
	
6. __order_admin__ : _track order for administrator_

	* detailed info provided to administrator
	* buyer, address, mobile
	* createDate, payDate, deliveryDate, confirmDate...

7. __review__ : _customer review_

	* user
	* comments
	* about which product
	* review Date
	
## JavaBean

* private attributes
* public setters and getters

## DAO
* initialize database driver and connect to database

```java
import java.sql.Connetion;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	static String ip = "127.0.0.1";
	static int port = 3306;
	static String database = "db_name";
	static String encoding = "UTF-8";
	static String name = "username";
	static String password = "password";
	
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		String url = String.format("jdbc:mysql://%s:%s/%s?characterEncoding=%s", ip, port, database, encoding);
		return DriverManager.getConnection(url, name, password);
	}
	
	public static void main(String[] args){
		try(Connection c = DBUtil.getConnection();){
			System.out.println("DB connects successfully" + c);
		}catch(){
			e.printStackTrace();
		}
	}
}
```
* basic CRUD 

```sql
create table user(
	id int(11) not null auto_increment,
	name var(255) default null,
	password var(255) default null,
	primary key (id)
)engine = InnoDB default charset = utf8;


insert into user values(null,"Toby","cat");
delete from user where id = 1;

insert into user values(null,"Toby","cat");
update user set name = "CatToby" where id = 2;

select * from user order by id desc limit 1,10;
select * from user where name like "%Cat%";
```

* export and import database

```
mysqldump -u username -p name_database > iCat.sql;
source iCat.sql;
```

##Servlet

* UserServlet

```java
package carrie.servlet;

public class UserServlet extends BaseBackServlet{
	@Override
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<User> users = userDAO.list();
		request.setAttribute("users", users);
		return "admin/listUser.jsp";
	}
}
```

* BaseBackServlet

```java
package carrie.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public abstract class BaseBackServlet extends HttpServlet{

	public abstract String add(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response) ;
    public abstract String list(HttpServletRequest request, HttpServletResponse response) ;
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		String method = (String) request.getAttribute("method");
    		Method m = this.getClass.getMethod(method,java.servlet.http.HttpServletRequest.class,javax.servlet.http.HttpServletResponse.class);
    		String redirect = m.invoke(this, request, response).toString();
    		
    		if(redirect.startsWith("@"))
    			response.sendRedirect(redirect.substring(1));
    		else if(redirect.startsWith("%"))
    			response.getWriter().print(redirect.substring(1));
    		else
    			request.getRequestDispatcher(redirect).forward(request,response);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
```

* Upload image 

	* parseUpload

	```java
	public InputStream parseUpload(HttpServletRequest request, Map<String,String> params){
		InputStream is = null;
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024*1024);
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while(iter.hasNext()){
				FileItem item = (FileItem)iter.next();
				if(! item.isFormField)
					is = item.getInputStream();
				else{
					String paraName = item.getFieldName();
					String paraValue = item.getString();
					paraValue = new String(paraValue.getBytes("ISO-8859-1"),"UTF-8");
					params.put(paraName,paraValue);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return is;
	}
	```

	* saveUpload	

	```java
	public void saveUpload(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> params = new HashMap<>();
		InputStream is = parseUpload(request, params);
		
		File folder = new File(request.getSession().getServletContext().getRealPath("pic/products"));
		File file = new File(folder, product.getId() + ".jpg");
		if(is != null && is.available != 0){
			try(FileOutputStream fos = new FileOutputStream(file)){
				byte[] b = new byte[1024*1024];
				int len = 0;
				while( (len = is.read(b)) != -1 )
					fos.write(b,0,len);
				fos.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	```

* web.xml

```xml
<!DOCTYPE xml>
<web-app>
	<servlet>
	    <servlet-name>UserServlet</servlet-name>
	    <servlet-class>carrie.servlet.UserServlet</servlet-class>
	</servlet>
	 
	<servlet-mapping>
	    <servlet-name>UserServlet</servlet-name>
	    <url-pattern>/userServlet</url-pattern>
	</servlet-mapping>
</web-app>
```


##Filter

```java
package carrie.filter;

public class ServletFilter implements Filter{

	public void destroy(){}	
	public void init(FilterConfig arg0) throws ServletException{}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) 
		throws IOException, ServletException{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		String contextPath = request.getServletContext().getContextPath(); //contextPath=/iCat		
		String uri = request.getRequestURI(); //uri=/iCat/admin_category_list
		uri = StringUtils.remove(uri, contextPath); //uri=/admin_category_list
		
		if(uri.startsWith("/admin_")){
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			request.getRequestDispatcher("/"+servletPath).forward(request,response);
			return;
		}
		
		chain.doFilter(request, response); //send request to next Filter in the filter-chain
	}
}
```

* web.xml

```xml
<!DOCTYPE xml>
<web-app>
	<filter>
	    <filter-name>ServletFilter</filter-name>
	    <filter-class>carrie.filter.ServletFilter</filter-class>
	</filter>
	 
	<filter-mapping>
	    <filter-name>ServletFilter</filter-name>
	    <url-pattern>/*</url-pattern>
	</filter-mapping>
</web-app>
```

##JSP

* JSTL -- JSP Standard Tag Library

	* import jstl.jar and standard.jar
	* <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

* EL

	* <%@page isELIgnored="false" %>
	* easy to obtain JavaBean's attribute, like ${user.name}


## Ajax

* __Asynchronous JavaScript And Xml__
 
1. Update a web page without reloading the page
* Request data from a server - after the page has loaded
* Receive data from a server - after the page has loaded
* Send data to a server - in the background

```javascript
<script>
$(function(){

	$(".buyLink").click(function(){
		var page = "forecheckLogin"；
		$.get(
			page,
			function(result){
				if(result == "success"){
					var = $(". productNumberSetting").val();
					location.href = $("buyLink").attr("href") + "&num=" + num;
				}
				else{
					$("#loginModal").modal('show');
				}
			}
		);
		return false;
	});
	
	$("button.loginSubmitButton").click(function(){
		var name = $("#name").val();
		var password = $("#password").val();
		
		if(name.length==0 || password.length==0){
			$("span.errorMessage").html("please input account/password");
			$("div.loginErrorMessageDiv").show();
			return false;
		}
		
		var page = "foreloginAjax";
		$.get(
			page,
			{"name":name, "password":password},
			function(result){
				if(result == "success"){
					location.reload();
				}
				else{
					$("span.errorMessage").html("invalid account/password");
					$("div.loginErrorMessageDiv").show();
				}				
			}
		);
		return true;
	});

});
</script>


<input class="productNumberSetting" type="text" value="1">
<a class="buyLink" href="forebuyone?pid=${product.id}"><button class="buyButton">Buy now</button></a>
```

## Deploy on Azure

* pack 
	1. cd .../tomcat
	* jar -cvf iCat.war *
	* mysqldump -u username -p name_database > iCat.sql;

* send to Azure server
	1. Tool: FileZilla SFTP
	* iCat.war & iCat.sql

* unpack 
	1. source iCat.sql
	* jar -xvf iCat.war
	
* modify server.xml
	1. modify __docBase__
	2. add a new __Host__
	3. modify __defaultHost__