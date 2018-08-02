package servlets;

import db.DBService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DateServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String options = request.getParameter("option");
        String result = "";
        if (options.equals("all")) {
            DBService dbService;
            dbService = new DBService();
            result = dbService.getValues("date");

        }
        if (options.equals("only")){
            String date = request.getParameter("date");
            DBService dbService;
            dbService = new DBService();
            result = dbService.getDataPost("date","date",date);
        }
        if ((options == null) || (options.equals(""))){
            result = "Error - Incorrect format GET request! Check format from documentation!";
        }
        response.getWriter().println(result);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
