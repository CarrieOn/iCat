package carrie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class BackServletFilter implements Filter{

	@Override
	public void destroy() {	
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		
		String contextPath = request.getServletContext().getContextPath();	//contextPath = /carrie
		String uri = request.getRequestURI();												//uri = /carrie/admin_category_list
		uri = StringUtils.remove(uri, contextPath);											//uri = /admin_category_list
		
		if(uri.startsWith("/admin_")) {
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			request.getRequestDispatcher("/"+servletPath).forward(request, response);
			return;
		}
		chain.doFilter(request, response); //send request to next Filter in the filter-chain
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
