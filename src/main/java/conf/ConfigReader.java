package conf;

import logs.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigReader {

    private String dbServer;
    private String dbUsername;
    private String dbPassword;
    private String dbName;
    private String firstRun;
    private String fileDirInput;
    private String periodicity;

    public void readConfig(){
        String[] configsInput = new String[10];
        try{
            FileInputStream fstream = new FileInputStream("config.conf");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String tmp;
            int i=0;
            while ((tmp=br.readLine()) != null){
                configsInput[i] = tmp;
                i++;
            }
        }catch (IOException e) {
            e.printStackTrace();
            new Logger(e.getMessage());
        }
        dbServer = configsInput[0].replaceAll("server=","");
        dbName = configsInput[1].replaceAll("database=","");
        dbUsername = configsInput[2].replaceAll("login=","");
        dbPassword = configsInput[3].replaceAll("password=","");
        firstRun = configsInput[4].replaceAll("first_run=","");
        fileDirInput = configsInput[5].replaceAll("file_dir_schedule=","");
        periodicity = configsInput[6].replaceAll("periodicity=","");
        initParam(dbServer, dbName, dbUsername, dbPassword, firstRun, fileDirInput, periodicity);
    }

    public void initParam(String dbServer, String dbName, String dbUsername, String dbPassword, String emptyDb, String fileDirInput, String periodicity){
        this.dbServer = dbServer;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.dbName = dbName;
        this.firstRun = emptyDb;
        this.fileDirInput = fileDirInput;
        this.periodicity = periodicity;
    }

    public String getFileDirInput() {
        return fileDirInput;
    }

    public String getDbServer() {
        return dbServer;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public String getPeriodicity() {
        return periodicity;
    }

}
