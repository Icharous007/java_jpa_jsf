package filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.Pessoa;
import jpautil.JPAUTIL;

@WebFilter(urlPatterns = {"/*"})
public class FiltroDeAutenticacao implements Serializable, Filter{

	private static final long serialVersionUID = 8634216766090208089L;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		//System.out.println(req.getContextPath());
		//System.out.println(req.getRequestURI());
		//System.out.println(req.getServletPath());
		HttpSession sesson = req.getSession();
		Pessoa logedUser = (Pessoa) sesson.getAttribute("logedUser");
		if(!"/index.xhtml".equalsIgnoreCase(req.getServletPath()) &&( logedUser == null ) ) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.xhtml");
			dispatcher.forward(request, response);
			return;
		}else {
			//System.out.println("Ivocando Filtro.");
			chain.doFilter(request, response);		
		}		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		JPAUTIL.getEntityManager();
    }
}
