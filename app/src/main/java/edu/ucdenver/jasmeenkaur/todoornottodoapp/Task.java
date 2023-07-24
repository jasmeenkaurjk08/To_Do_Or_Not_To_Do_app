package edu.ucdenver.jasmeenkaur.todoornottodoapp;

public class Task {
    private String id;
    private String name;
    private String dueDate;
    private String dueTime;
    private String priority;
    private String category;
    private String parentTask;
    private String completed;
    private String notes;
    private String listID;

    public Task(String id, String name, String dueDate, String dueTime, String priority, String category,
                String parentTask, String completed, String notes, String listID){
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.priority = priority;
        this.category = category;
        this.parentTask = parentTask;
        this.completed = completed;
        this.notes = notes;
        this.listID = listID;
    }

    // ======================================== getters ========================================
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDueDate() {
        return dueDate;
    }
    public String getDueTime() {
        return dueTime;
    }
    public String getPriority() {
        return priority;
    }
    public String getCategory() {
        return category;
    }
    public String getParentTask() {
        return parentTask;
    }
    public String getCompleted() {
        return completed;
    }
    public String getNotes() {
        return notes;
    }
    public String getListID() {
        return listID;
    }

    // ======================================== setters ========================================
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setParentTask(String parentTask) {
        this.parentTask = parentTask;
    }
    public void setCompleted(String completed) {
        this.completed = completed;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setListID(String listID) {
        this.listID = listID;
    }
}
