package servlets;

import db.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeviceServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String value = request.getParameter("device");
        if ((!value.equals("")) || (value != null)) {
            System.out.println("[New device] " + value);
            DBService dbService = new DBService();
            dbService.addNewDevice(value);
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.getWriter().println("Parameter device is null or empty");
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
