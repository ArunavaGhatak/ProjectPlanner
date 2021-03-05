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
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collections;
import java.util.List;

class TaskManager {
    
    private static TaskManager taskMgr;
    private ArrayList<Task> tasks;
    private int lastTaskId;
    
    private TaskManager() {
        tasks = new ArrayList<>();
        lastTaskId = 0;
    }
    
    static TaskManager instance() {
        if(taskMgr == null)
            taskMgr = new TaskManager();
        return taskMgr;
    }
    
    private boolean init(String filename) {
        
        tasks.clear();
        File file = new File(filename);
        try {
            if(file.createNewFile())
                try(BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                    out.write('0');
                }
            else
                try(BufferedReader in = new BufferedReader(new FileReader(file))) {
                    lastTaskId = Integer.parseInt(in.readLine());
                    String taskDetails;
                    while((taskDetails = in.readLine()) != null) {
                        tasks.add(new Task(taskDetails));
                    }
                }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    boolean init() {
        return init("Tasks.csv");
    }
    
    void deleteTask(int projId, int taskId) {
        tasks.removeIf(task -> task.getId() == taskId);
        Mediator.instance().taskDeleted(projId, taskId);
    }
    
    int addTask(int projId, String taskDetails) {
        Task newTask = new Task(++lastTaskId + "|" + taskDetails);
        deleteTask(projId, newTask.getId());
        tasks.add(newTask);
        Mediator.instance().taskCreated(projId, newTask.getId());
        return newTask.getId();
    }
    
    void markTaskAsCompleted(int taskId) {
        tasks.forEach(task -> {
            if(task.getId() == taskId)
                task.markAsCompleted();
        });
    }
    
    List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
    
    private boolean save(String filename) {
        try(PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(filename)))) {
            out.println(lastTaskId);
            tasks.forEach(task -> {
                out.println(task);
            });
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    boolean save() {
        return save("Tasks.csv");
    }
}