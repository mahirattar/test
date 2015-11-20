package ma.kapisoft.gjurd.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ma.kapisoft.gjurd.controller.Authentification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


@Component
public class PasswordExpirationFilter extends GenericFilterBean {
	private static final Log log = LogFactory.getLog(PasswordExpirationFilter.class);
	   
	
	private final static String BEAN_AUTHENTIFICATION="authentification";
	private final static String SUFFIXE=".xhtml";
	private final static String PAGE_MODIF_PASS="modifpassword";
	
	private static final String NotificationPasswordExpired="NotificationPasswordExpired";

	public PasswordExpirationFilter() {
		// TODO Auto-generated constructor stub
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest r = (HttpServletRequest) request;
		
		
		HttpSession session = ((HttpServletRequest) request).getSession(false);
Authentification auth = (session != null) ? (Authentification) session
	.getAttribute(BEAN_AUTHENTIFICATION) : null;
	log.trace(r.getRequestURL());
	
	if (r.getRequestURL().indexOf(PAGE_MODIF_PASS) == -1 && auth != null && auth.isAuthenticated() && auth.haveNotification(NotificationPasswordExpired))
	{
		
		((HttpServletResponse) response).sendRedirect(getRootPath(r)+"/"+PAGE_MODIF_PASS+SUFFIXE);
         return;
		

	}
	chain.doFilter(request, response);
	
		
		
	}
	
	private String getRootPath(HttpServletRequest request)
	{
		String url = request.getRequestURL().toString();
		return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

	}

}
