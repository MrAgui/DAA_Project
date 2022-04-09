package com.bsu.JustDoIt.Model;

public class ToDoModel {
    // Status is a boolean variable but we use int 1 for true and 0 for false because of the database Issues
    private int id, status;
    private String task;
    // getters and setters for the variables to be used in the recyclerView
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
