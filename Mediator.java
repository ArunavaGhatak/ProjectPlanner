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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Mediator {
    
    private static Mediator mediator;
    private HashMap<Integer, ArrayList<Integer>> tasksAssigned;
    private HashMap<Integer, ArrayList<Integer>> employeesAssigned;
    
    private Mediator() {
        tasksAssigned = new HashMap<>();
        employeesAssigned = new HashMap<>();
    }
    
    static Mediator instance() {
        if(mediator == null)
            mediator = new Mediator();
        return mediator;
    }
    
    private boolean init(String filename, HashMap<Integer, ArrayList<Integer>> map) {
        
        map.clear();
        File file = new File(filename);
        try {
            if(!file.createNewFile())
                try(BufferedReader in = new BufferedReader(new FileReader(file))) {
                    String mappingString;
                    while((mappingString = in.readLine()) != null) {
                        String[] mapping = mappingString.strip().split("\s*", 2);
                        int key = Integer.parseInt(mapping[0]);
                        String[] values = mapping[1].strip().split("\s*");
                        ArrayList<Integer> valuesList = new ArrayList<>();
                        for(String value : values)
                            if(value != "")
                                valuesList.add(Integer.parseInt(value));
                        map.put(key, valuesList);
                    }
                }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    private boolean init(String tasksAssignmentFilename, String employeesAssignmentFilename) {
        return init(tasksAssignmentFilename, tasksAssigned) |
                init(employeesAssignmentFilename, employeesAssigned);
    }
    
    boolean init() {
        return init("TasksAssignments.csv", "EmployeesAssignments.csv") |
                ProjectManager.instance().init() | TaskManager.instance().init() |
                EmployeeManager.instance().init() | PasswordManager.instance().init();
    }
    
    void employeeRemoved(int taskId, int empId) {
        if(employeesAssigned.get(taskId) != null)
            employeesAssigned.get(taskId).removeIf(id -> id == empId);
    }
    
    void employeeAdded(int taskId, int empId) {
        if(employeesAssigned.get(taskId) == null)
            employeesAssigned.put(taskId, new ArrayList<>());
        var empIdList = employeesAssigned.get(taskId);
        if(!empIdList.contains(empId))
            empIdList.add(empId);
    }
    
    void taskDeleted(int projId, int taskId) {
        if(tasksAssigned.get(projId) != null)
            tasksAssigned.get(projId).removeIf(id -> id == taskId);
        employeesAssigned.remove(taskId);
    }
    
    void taskCreated(int projId, int taskId) {
        if(tasksAssigned.get(projId) == null)
            tasksAssigned.put(projId, new ArrayList<>());
        var taskIdList = tasksAssigned.get(projId);
        if(!taskIdList.contains(taskId))
            taskIdList.add(taskId);
        employeesAssigned.put(taskId, new ArrayList<>());
    }
    
    void projectDeleted(int projId) {
        var taskIdList = tasksAssigned.get(projId);
        if(taskIdList != null)
            while(!taskIdList.isEmpty())
                TaskManager.instance().deleteTask(projId, taskIdList.get(0));
        tasksAssigned.remove(projId);
    }
    
    void projectCreated(int projId) {
        tasksAssigned.put(projId, new ArrayList<>());
    }
    
    Map<Integer, ArrayList<Integer>> getTaskAssignments() {
        return Collections.unmodifiableMap(tasksAssigned);
    }
    
    Map<Integer, ArrayList<Integer>> getEmployeesAssignments() {
        return Collections.unmodifiableMap(employeesAssigned);
    }
    
    private boolean save(String filename, HashMap<Integer, ArrayList<Integer>> map) {
        
        try(PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(filename)))) {
            String mappingString;
            for(var mapEntry : map.entrySet()) {
                mappingString = String.valueOf(mapEntry.getKey());
                for(int value : mapEntry.getValue()) {
                    mappingString += " " + value;
                }
                out.println(mappingString);
            }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    private boolean save(String tasksAssignmentFilename, String employeesAssignmentFilename) {
        return save(tasksAssignmentFilename, tasksAssigned) |
                save(employeesAssignmentFilename, employeesAssigned);
    }
    
    boolean save() {
        return save("TasksAssignments.csv", "EmployeesAssignments.csv") |
                ProjectManager.instance().save() | TaskManager.instance().save() |
                EmployeeManager.instance().save();
    }
    
    public static void main(String args[]) {
        Mediator.instance().init();
        /*int projId1 = ProjectManager.instance().addProject("Project 1");
        int projId2 = ProjectManager.instance().addProject("Project 2");
        int projId3 = ProjectManager.instance().addProject("Project 3");
        
        int taskId1 = TaskManager.instance().addTask(projId1, "false|Task 1");
        int taskId2 = TaskManager.instance().addTask(projId1, "false|Task 2");
        int taskId3 = TaskManager.instance().addTask(projId2, "false|Task 3");
        int taskId4 = TaskManager.instance().addTask(projId2, "false|Task 4");
        int taskId5 = TaskManager.instance().addTask(projId3, "false|Task 5");
        int taskId6 = TaskManager.instance().addTask(projId3, "false|Task 6");
        
        int empId1 = EmployeeManager.instance().addEmployee(taskId1, "1|Emp1");
        int empId2 = EmployeeManager.instance().addEmployee(taskId2, "2|Emp2");
        int empId3 = EmployeeManager.instance().addEmployee(taskId3, "3|Emp3");
        int empId4 = EmployeeManager.instance().addEmployee(taskId4, "4|Emp4");
        int empId5 = EmployeeManager.instance().addEmployee(taskId5, "5|Emp5");
        int empId6 = EmployeeManager.instance().addEmployee(taskId6, "6|Emp6");
        int empId7 = EmployeeManager.instance().addEmployee(taskId1, "2|Emp2");
        int empId8 = EmployeeManager.instance().addEmployee(taskId2, "3|Emp3");
        
        ProjectManager.instance().deleteProject(projId2);
        
        Mediator.instance().save();*/
    }
}
