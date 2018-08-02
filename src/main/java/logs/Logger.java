package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private String errorMessage;

    public Logger(String errorMessage){
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss yyyy.MM.dd");
        String currentDate = formatDate.format(date);
        this.errorMessage = "[" + currentDate + "] " + errorMessage + "\n";;
        writeLog();
    }

    private void writeLog(){
        String folderPath = "logs";
        String path = "logs/log.txt";

        try{
            File searchFile = new File(path);
            if (searchFile.exists()) {
                FileWriter writer = new FileWriter(path, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(this.errorMessage);
                bufferWriter.close();
            } else {
                System.out.println("[ ERROR ] Log file lost! I am create this.");
                File folder = new File(folderPath);
                folder.mkdir();
                searchFile.createNewFile();
                FileWriter writer = new FileWriter(path, false);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(this.errorMessage);
                bufferWriter.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
