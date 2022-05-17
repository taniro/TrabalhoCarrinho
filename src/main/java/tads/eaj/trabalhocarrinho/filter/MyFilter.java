package tads.eaj.trabalhocarrinho.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin/*")
public class MyFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {


        HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);

        if (session == null){
            ((HttpServletResponse) servletResponse).sendRedirect("http://www.google.com");
        }else{
            System.out.println("O usuario logado é " + session.getAttribute("login"));
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
