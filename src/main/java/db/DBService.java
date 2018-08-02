package db;

import com.mysql.jdbc.Driver;
import conf.ConfigReader;
import logs.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;


public class DBService {

    private final Connection connection;

    public DBService() {
        this.connection = getMysqlConnection();
    }

    public String getValues(String searchValue){

        String buildResult = "";
        String sqlStr = "SELECT `" + searchValue + "` FROM `scheduleTable`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStr);
            String currentValue;
            while (resultSet.next()) {
                currentValue = resultSet.getString(searchValue);
                if (notContains(currentValue, buildResult)){
                    buildResult += currentValue + ";";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Logger(e.getMessage());
        }
        try {
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildResult;
    }

    private boolean notContains(String currentValue, String buildResult) {
        String[] findValue = buildResult.split(";");
        for (int i = 0; i < findValue.length; i++) {
            if (findValue[i].equals(currentValue)) return false;
        }
        return true;
    }

    public String getDataPost(String key, String value, String searchDate){
        String result = ""; //!
        String sql;
        if ((key.equals("date"))&&(value.equals("date"))) {
            sql = "SELECT * FROM `scheduleTable` WHERE `" + key + "`='" + searchDate + "'";
        } else {
            sql = "SELECT * FROM `scheduleTable` WHERE `"+ key +"`='" + value +"' AND `date`='"+ searchDate +"'";
        }
            System.out.println("Preparation to sql request: " + sql);

        List<String> buildString = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String group = resultSet.getString("group");
                String predm = resultSet.getString("predm");
                String prepod = resultSet.getString("prepod");
                String kab = resultSet.getString("kab");
                String dp = resultSet.getString("dp");
                String date = resultSet.getString("date");

                String temporal = "id=" + id + ";group="
                        + group + ";predm=" + predm + ";prepod="
                        + prepod + ";kab=" + kab
                        + ";dp=" + dp + ";date=" + date;
                System.out.println(temporal);
                buildString.add(temporal);
            }
        }catch (SQLException e){
            e.printStackTrace();
            new Logger(e.getMessage());
        }

        for (int i = 0; i < buildString.size(); i++) {
            result += buildString.get(i) + ";";
        }
        return result;
    }

    public void addDataToBD(List<String> data) throws SQLException {
        String sqlStr = "INSERT INTO `scheduleTable` (`id`,`group`,`predm`,`prepod`,`kab`,`dp`,`date`) VALUES (NULL,?,?,?,?,?,?)";
        System.out.println("Preparation to sql request: " + sqlStr);
        PreparedStatement preparedStatement;
        if (data.size() % 6 == 0) { //проверка на целостность данных
            try {
            for (int i = 0; i < data.size(); i += 6) {//в строке 6 параметров, data.size()/6 - так можно получить количество строк
                preparedStatement = connection.prepareStatement(sqlStr);
                preparedStatement.setString(1, data.get(i));
                preparedStatement.setString(2, data.get(i + 1));
                preparedStatement.setString(3, data.get(i + 2));
                preparedStatement.setString(4, data.get(i + 3));
                preparedStatement.setString(5, data.get(i + 4));
                preparedStatement.setString(6, data.get(i + 5).replaceAll("na ", ""));
                preparedStatement.execute();
            }
            } catch (SQLException e) {
                e.printStackTrace();
                new Logger(e.getMessage());
            } finally {
                closeConnection();
                }
            } else {
                System.out.println("[ ERROR ] Integrity of schedule files is broken! Operation not execute!");
            }
    }

    public Connection getMysqlConnection() {
        ConfigReader config = new ConfigReader();
        config.readConfig();

        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").                                        //db type
                    append(config.getDbServer()+":").                               //host name
                    append("3306/").                                                //port
                    append(config.getDbName()+"?").                                 //db name
                    append("user="+config.getDbUsername()+"&").                     //login
                    append("password="+config.getDbPassword());                     //password

            Connection connection = DriverManager.getConnection(url.toString());

            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            new Logger(e.getMessage());
        }
        return null;
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    public void addNewDevice(String value) {
        //todo: прежде, чем записывать нужно убедиться, что такого устройства в базе нет
        if (value != null){
            String sqlSearch = "SELECT * INTO deviceInfo WHERE `device`='" + value + "'";
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSetForSearchDevice = null;
                resultSetForSearchDevice = statement.executeQuery(sqlSearch);
                if (resultSetForSearchDevice == null){
                    String insertSql = "INSERT INTO `deviceInfo` (`id`,`device`,`date`) VALUES (NULL,?,?)";
                    Date date = new Date();
                    SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
                    String currentDate = formatDate.format(date);
                    PreparedStatement preparedStatement = null;

                    try {
                        preparedStatement = connection.prepareStatement(insertSql);
                        preparedStatement.setString(1, value);
                        preparedStatement.setString(2, currentDate);
                        preparedStatement.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        new Logger(e.getMessage());
                    }
                } else {
                    String errorMsg = "[ WARNING ] Find one or more matches. Operation SQL INSERT not permitted";
                    System.out.println(errorMsg);
                    new Logger(errorMsg);

                }

            } catch (SQLException e){
                e.printStackTrace();
                new Logger(e.getMessage());
            }


        }
    }
}
