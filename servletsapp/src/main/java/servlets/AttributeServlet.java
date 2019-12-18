package servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AttributeServlet extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
  {
    ServletContext ctx = req.getServletContext();
    ctx.setAttribute( "user", "Anatolii" );
    String user = (String) ctx.getAttribute( "user" );
    ctx.removeAttribute( "user" );
    PrintWriter out = resp.getWriter();
    doHardWork(1000);
    out.write( "Hi " + user );
  }

  private void doHardWork(int mls) {
    try {
      Thread.sleep(mls);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
