package time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DayOfWeek {

    private Date date;

    private String year;
    private String month;

    private int day;
    private int monthCode;
    private int yearCode;
    private int dayWeek;

    private boolean leapYear = false;

    public DayOfWeek(Date date, String year, String month){
        this.date = date;
        this.year = year;
        this.month = month;

        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        String currentDay = formatDay.format(date);
        this.day = Integer.parseInt(currentDay);
    }

    private int monthCode(){
        int codeOfMonth = 0;
        switch(this.month){
            case "01":
                codeOfMonth = 1; // Январь
                break;
            case "02":
                codeOfMonth = 4; // Февраль
                break;
            case "03":
                codeOfMonth = 4; // Март
                break;
            case "04":
                codeOfMonth = 0; // Апрель
                break;
            case "05":
                codeOfMonth = 2; // Май
                break;
            case "06":
                codeOfMonth = 5; // Июнь
                break;
            case "07":
                codeOfMonth = 0; // Июль
                break;
            case "08":
                codeOfMonth = 3; // Август
                break;
            case "09":
                codeOfMonth = 6; // Сентябрь
                break;
            case "10":
                codeOfMonth = 1; // Октябрь
                break;
            case "11":
                codeOfMonth = 4; // Ноябрь
                break;
            case "12":
                codeOfMonth = 6; // Декабрь
                break;
                default:
                    System.out.println("Non numeric month or missing value");
                    break;
        }
        return codeOfMonth;
    }

    private int yearCode(){
        int shortYear = Integer.parseInt(this.year.substring(2)); // посл. две цифры года
        if (shortYear % 4 == 0) {
            this.leapYear = true;
        }
        return (6 + shortYear + (shortYear/4)) % 7;
    }

    private int currentHour(){
        SimpleDateFormat formatHour = new SimpleDateFormat("hh");
        String currentHour = formatHour.format(date);
        return Integer.parseInt(currentHour);
    }

    private int dayOfWeek(int monthCode, int yearCode){
        int dayOfWeek = 0;
        if (this.leapYear == true && (this.month == "01" || this.month == "02")){ // Если високосный год и месяц либо январь, либо февраль
            dayOfWeek = ((this.day + monthCode + yearCode) % 7) - 1; // Отнимаем 1
            if (dayOfWeek < 0) { // Если меньше 0, переводим на позицию 6
                dayOfWeek = 6;
            }
        }else{
            dayOfWeek = (this.day + monthCode + yearCode) % 7;
        }
        return dayOfWeek;
    }

    public int getDayWeek() {
        this.monthCode = monthCode();
        this.yearCode = yearCode();
        this.dayWeek = dayOfWeek(this.monthCode,this.yearCode);
        return dayWeek;
    }

    // RunOrNot

    public boolean runOrNot() {
        int currentDay = getDayWeek();
        int currentHour = currentHour();
        if (((day >= 1 || day <= 9) && this.month == "01") || (this.month == "07" || this.month == "08")){
            // (Первое условие = 01.01 - 09.01 - Новогодние каникулы) || (Второе условие = 07 и 08 месяца - Летние каникулы)
            return false;
        }
        else if (currentDay != 1  && (currentHour >= 8 && currentHour <= 17)){
            return true;
        }
        return false;
    }
}