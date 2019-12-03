package filters;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class PreFilter implements Filter {

  public void init(FilterConfig filterConfig) throws ServletException {
  }

  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    System.out.println(((HttpServletRequest)servletRequest).getRequestURL() + " at " + LocalDate.now());
    filterChain.doFilter(servletRequest, servletResponse);
  }

  public void destroy() {
  }
}
