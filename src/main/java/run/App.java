package run;

import building.Planner;
import logs.Copyright;
import logs.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.DateServlet;
import servlets.DeviceServlet;
import servlets.GroupServlet;
import servlets.PrepodServlet;

public class App {
    public static void main(String[] args) {
        DateServlet dateServlet = new DateServlet();
        GroupServlet groupServlet = new GroupServlet();
        PrepodServlet prepodServlet = new PrepodServlet();
        DeviceServlet deviceServlet = new DeviceServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(dateServlet), "/date");
        context.addServlet(new ServletHolder(groupServlet), "/group");
        context.addServlet(new ServletHolder(prepodServlet), "/prepod");
        context.addServlet(new ServletHolder(deviceServlet), "/device");

        Server server = new Server(2198);
        server.setHandler(context);

        new Copyright();
        new Logger("[ INFO ] Web application is run");
        new Planner().runPlanner();

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            new Logger(e.getMessage());
        }

    }
}
