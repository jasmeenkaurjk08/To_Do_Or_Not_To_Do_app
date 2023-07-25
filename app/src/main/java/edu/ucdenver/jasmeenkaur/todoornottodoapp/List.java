package edu.ucdenver.jasmeenkaur.todoornottodoapp;

public class List {
    private String id;
    private String name;
    private String taskSort;
    private String taskCompleteHandle;
    private String backgroundColor;

    public List(String id, String name, String taskSort, String taskCompleteHandle, String backgroundColor){
        this.id = id;
        this.name = name;
        this.taskSort = taskSort;
        this.taskCompleteHandle = taskCompleteHandle;
        this.backgroundColor = backgroundColor;
    }
    public List(String name){
        this.name = name;
        this.taskSort = "due_date";
        this.taskCompleteHandle = "show";
        this.backgroundColor = "white";

    }

    // ======================================== getters ========================================
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTaskSort() {
        return taskSort;
    }
    public String getTaskCompleteHandle() {
        return taskCompleteHandle;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }

    // ======================================== setters ========================================
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTaskSort(String taskSort) {
        this.taskSort = taskSort;
    }
    public void setTaskCompleteHandle(String taskCompleteHandle) {
        this.taskCompleteHandle = taskCompleteHandle;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
