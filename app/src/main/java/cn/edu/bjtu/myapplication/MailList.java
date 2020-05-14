package cn.edu.bjtu.myapplication;

public class MailList {

    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public String getNumber(){
        return number;
    }
    public MailList(String name,String number) {
        this.name = name;
        this.number = number;
    }
}
