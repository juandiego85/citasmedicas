package ec.edu.ups.appDis.Controller;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class FiltroAutorizacion implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("llegooooooooooooooooooooooooooooooo Autorizacion");
		HttpSession session=((HttpServletRequest) request).getSession();
		String usrID= (String) session.getAttribute("usrid");
		String URI1=((HttpServletRequest) request).getRequestURI();
		
		if (
				(URI1.endsWith(".xhtml")||URI1.endsWith("/")) &&
				usrID==null &&
				URI1.endsWith("login.xhtml")==false
			) {
			session.setAttribute("error", " ");
			((HttpServletResponse) response).sendRedirect(request.getServletContext().getContextPath() + "/login.xhtml");
		}else {
		
		chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	

	
}
