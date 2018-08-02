package building;

import conf.ConfigReader;
import db.DBService;
import logs.Logger;
import translate.TranslateText;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parser {

    private File folder;
    private String debugMessage;

    public Parser() {
        ConfigReader config = new ConfigReader();
        config.readConfig();
        if (!config.getFileDirInput().equals("")){
            this.folder = new File(config.getFileDirInput());
        } else {
            this.debugMessage = ("[ ERROR ] Not correct path to schedules folder. Check config or available files schedules please!");
            System.out.println(debugMessage);
            new Logger(debugMessage);
        }
    }

    private File[] scanFolder(){
        File[] listFiles = this.folder.listFiles();
        if (listFiles == null){
            this.debugMessage = ("[ ERROR ] Schedule files not found! Today is holiday?");
            System.out.println(debugMessage);
            new Logger(debugMessage);
        } else {
            return listFiles;
        }
        return null;
    }

    public void parseFiles(){
        File[] listFiles = scanFolder();
        List<String> data = new ArrayList<>();

        //на входе csv файл с расписанием, разделитель точка с запятой, разбираем на части
        for (int i = 0; i < listFiles.length; i++) {
            try {
                System.err.println("[ INFO ] Reading file .. " + listFiles[i]);
                FileInputStream fstream = new FileInputStream(listFiles[i]);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String tmp;
                String[] readArr;
                while ((tmp = br.readLine()) != null) {
                        readArr = tmp.split(";");
                        for (int j = 0; j < readArr.length; j++) {
                            data.add(readArr[j].substring(1, readArr[j].length() - 1)); //каждое слово в кавычках, при добавлении их нужно убирать
                        }
                }
            } catch (IOException e) {
                    e.printStackTrace();
                    new Logger(e.getMessage());
            }
            if (data.size()!=0){
                    //убрать заголовки, нужны только данные
                    data.remove("Группа");
                    data.remove("Дисциплина");
                    data.remove("Фамилия");
                    data.remove("Кабинет");
                    data.remove("ПараДень");
                    data.remove("ar_name");
            }
        }

        if (listFiles.length == 0){
            Date currentDate = new Date();
            this.debugMessage = ("[ INFO ] Not found files. Just waiting .. " + currentDate);
            System.out.println(debugMessage);
            new Logger(debugMessage);
        }
        transformDataAndGiveDB(data);
        cleanFiles(listFiles); //Файлы свое отработали и больше не нужны
    }

    private void transformDataAndGiveDB(List<String> data) {
        List<String> buildingListSchedule = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            TranslateText translateText = new TranslateText(data.get(i));
            buildingListSchedule.add(translateText.getTransformedResult());
        }
        try {
            DBService dbService = new DBService();
            dbService.addDataToBD(buildingListSchedule);
        } catch(SQLException e){
            e.printStackTrace();
            new Logger(e.getMessage());
        }
    }

    private void cleanFiles(File[] listFiles){
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].delete()){
                System.out.println("[ INFO ]File " + listFiles[i] + " was removed");
            } else {
                System.out.println("[ ERROR ]File " + listFiles[i] + " not found. Deleting error!");
            }
        }
    }
}