package servlets;

import db.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GroupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String options = request.getParameter("option");
        String result = "";
        if (options.equals("all")) {
            DBService dbService;
            dbService = new DBService();
            result = dbService.getValues("group");

        } else if (options.equals("only")){
            String group = request.getParameter("group");
            String date = request.getParameter("date");
            if ((group == null) || (group.equals(""))) {
                result = "Error - Incorrect format GET request! Check format from documentation!";
            } else {
                DBService dbService;
                dbService = new DBService();
                result = dbService.getDataPost("group", group, date);
                if (result.equals("")) {
                    result = "no data";
                }
            }

        } else {
            result = "Error - Incorrect format GET request! Check format from documentation!";
        }
        response.getWriter().println(result);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
