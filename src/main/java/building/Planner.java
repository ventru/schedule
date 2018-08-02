package building;

import conf.ConfigReader;
import time.DayOfWeek;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс отвечающий за запуск процесса выгрузки CSV в базу данных
 * Запускать только в рабочее время (с 8:00 утра до 17:00),
 * только в рабочие будни (1-6) + суббота.
 * Учитывать праздники и выходной - воскресение.
 */

public class Planner {

    private int periodicity;
    private int[] pmTime = {8,9,10,11,0,1,2,3,4,5,6};
    private final int ONE_HOUR_MS = 3_600_000;

    public Planner(){
        ConfigReader config = new ConfigReader();
        config.readConfig();
        this.periodicity = Integer.parseInt(config.getPeriodicity());
        if ((this.periodicity < 1) || (this.periodicity > 7)){
            System.out.println("[ WARNING ] Не верно указан параметр Периодичность в конфиге, число вышло за пределы допустимого диапазона (меньше 1 или больше 7)");
            System.out.println("[ WARNING ] Так нельзя! Параметр периодичноть переключен по умолчанию - раз в 3 часа");
            this.periodicity = 3;
        }
    }

    public void runPlanner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Date date = new Date();
                    SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
                    SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
                    SimpleDateFormat formatHour = new SimpleDateFormat("hh");
                    String currentYear = formatYear.format(date);
                    String currentMonth = formatMonth.format(date);
                    int currentHour = Integer.parseInt(formatHour.format(date));
                    DayOfWeek whatIsToday = new DayOfWeek(date, currentYear, currentMonth);
                    if (whatIsToday.runOrNot() && itsTime(currentHour)){
                        //проверить дату, время и запустить парсинг
                        System.out.println(currentYear + "." + currentMonth + "."
                                + "Час:" + currentHour
                                + "[ INFO ] It's my time! I'am running search and parsing operation.");
                        Parser parserStarter = new Parser();
                        parserStarter.parseFiles();
                    } else {
                        System.out.println(currentYear + "." + currentMonth + "."
                                + "Hour:" + currentHour
                                + "[ INFO ] Hm, now is holidays, or outside working hours \n" +
                                "I am do nothing, just waiting\n" +
                                "Yes i'am sit here alone, and just waiting. ...");
                    }
                    try {
                        Thread.sleep(ONE_HOUR_MS * periodicity);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private boolean itsTime(int hour){
        for (int i = 0; i < pmTime.length; i++) {
            if (pmTime[i] == hour) return true;
        }
        return false;
    }

}
