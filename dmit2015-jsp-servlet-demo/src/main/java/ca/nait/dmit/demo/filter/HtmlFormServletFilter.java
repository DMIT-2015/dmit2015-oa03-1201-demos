package ca.nait.dmit.demo.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class ServletFitler
 */
@WebFilter("/servlet/form/*")
public class HtmlFormServletFilter implements Filter {

    /**
     * Default constructor. 
     */
    public HtmlFormServletFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// Log all parameter name/value pairs for debugging		
		ServletContext application = request.getServletContext();
		application.log("Processing HTML Form data");
		Enumeration<String> iterator = request.getParameterNames();
		while ( iterator.hasMoreElements() ) {
			String parameterName = iterator.nextElement();
			application.log(parameterName + ":" + request.getParameter(parameterName));
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
