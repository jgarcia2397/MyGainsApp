package com.josh2.mygains;


public class Exercise {
    private String id;
    private String name;
    private int sets;
    private int reps;
    private int restTime;
    private String day;

    public Exercise() {}

    public Exercise(String name, int sets, int reps, int restTime, String day) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.restTime = restTime;
        this.day = day;
    }

    public Exercise(String id, String name, int sets, int reps, int restTime, String day) {
        this.id = id;
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.restTime = restTime;
        this.day = day;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public int getRestTime() {
        return restTime;
    }

    public String getDay() {
        return day;
    }

    /*public List<Boolean> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<Boolean> list) {
        this.checkBoxes = list;
    }*/
}
