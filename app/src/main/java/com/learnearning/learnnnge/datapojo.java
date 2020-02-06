package com.learnearning.learnnnge;

public class datapojo {
    private String Name;
    private  int point;
    public datapojo(){

    }
    public datapojo(int point, String Name){
        this.point=point;
        this.Name=Name;

    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return Name;
    }

    public int getpoint() {
        return point;
    }

    public void setpoint(int point) {
        this.point = point;
    }
}
