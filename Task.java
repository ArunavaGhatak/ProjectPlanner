/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectplanner;

/**
 *
 * @author WINDOWS 10
 */
class Task {
    
    private final int id;
    private boolean completed;
    private String desc;
    
    Task(String taskDetails) {
        String[] details = taskDetails.strip().split("\s*[|]\s*", 3);
        id = Integer.parseInt(details[0]);
        completed = Boolean.valueOf(details[1]);
        desc = details[2];
    }
    
    @Override
    public String toString() {
        return id + "|" + completed + "|" + desc;
    }
    
    int getId() {
        return id;
    }
    
    boolean isCompleted() {
        return completed;
    }
    
    String getDescription() {
        return desc;
    }
    
    void markAsCompleted() {
        completed = true;
    }
}
