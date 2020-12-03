package com.kim.covid19;

public class ListData implements Comparable<ListData>{
    private int tv_num;
    private String tv_name;
    private String tv_cases;
    private String tv_cases_p;
    private String tv_deaths;
    private String tv_deaths_p;
    private String tv_recovered;

    @Override
    public int compareTo(ListData o) {

       if(this.tv_num > o.tv_num){
            return 1;
        }else if (this.tv_num < o.tv_num){
            return -1;
        }else {
            return 0;
        }
    }

    public ListData(int tv_num, String tv_name, String tv_cases, String tv_cases_p, String tv_deaths, String tv_deaths_p, String tv_recovered){
        this.tv_num = tv_num;
        this.tv_name = tv_name;
        this.tv_cases = tv_cases;
        this.tv_cases_p = tv_cases_p;
        this.tv_deaths = tv_deaths;
        this.tv_deaths_p = tv_deaths_p;
        this.tv_recovered = tv_recovered;
    }

    public int getTv_num() {
        return tv_num;
    }

    public void setTv_num(int tv_num) {
        this.tv_num = tv_num;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_cases() {
        return tv_cases;
    }

    public void setTv_cases(String tv_cases) {
        this.tv_cases = tv_cases;
    }

    public String getTv_cases_p() {
        return tv_cases_p;
    }

    public void setTv_cases_p(String tv_cases_p) {
        this.tv_cases_p = tv_cases_p;
    }

    public String getTv_deaths() {
        return tv_deaths;
    }

    public void setTv_deaths(String tv_deaths) {
        this.tv_deaths = tv_deaths;
    }

    public String getTv_deaths_p() {
        return tv_deaths_p;
    }

    public void setTv_deaths_p(String tv_deaths_p) {
        this.tv_deaths_p = tv_deaths_p;
    }

    public String getTv_recovered() {
        return tv_recovered;
    }

    public void setTv_recovered(String tv_recovered) {
        this.tv_recovered = tv_recovered;
    }
}
