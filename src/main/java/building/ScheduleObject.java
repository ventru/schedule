package building;

public class ScheduleObject {
    private String group;
    private String predm;
    private String prepod;
    private String cab;
    private String dp;
    private String date;

    public ScheduleObject(String group, String predm, String prepod, String cab, String dp, String date) {
        this.group = group;
        this.predm = predm;
        this.prepod = prepod;
        this.cab = cab;
        this.dp = dp;
        this.date = date;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPredm() {
        return predm;
    }

    public void setPredm(String predm) {
        this.predm = predm;
    }

    public String getPrepod() {
        return prepod;
    }

    public void setPrepod(String prepod) {
        this.prepod = prepod;
    }

    public String getCab() {
        return cab;
    }

    public void setCab(String cab) {
        this.cab = cab;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
