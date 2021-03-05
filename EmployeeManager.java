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

class EmployeeManager {
    
    private static EmployeeManager empMgr;
    private ArrayList<Employee> employees;
    
    private EmployeeManager() {
        employees = new ArrayList<>();
    }
    
    static EmployeeManager instance() {
        if(empMgr == null)
            empMgr = new EmployeeManager();
        return empMgr;
    }
    
    private boolean init(String filename) {
        
        employees.clear();
        File file = new File(filename);
        try {
            if(!file.createNewFile())
                try(BufferedReader in = new BufferedReader(new FileReader(file))) {
                    String empDetails;
                    while((empDetails = in.readLine()) != null) {
                        employees.add(new Employee(empDetails));
                    }
                }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    boolean init() {
        return init("Employees.csv");
    }
    
    void removeEmployee(int taskId, int empId) {
        Mediator.instance().employeeRemoved(taskId, empId);
    }
    
    int addEmployee(int taskId, String empDetails) {
        Employee emp = new Employee(empDetails);
        employees.removeIf(employee -> employee.getId() == emp.getId());
        Mediator.instance().employeeRemoved(taskId, emp.getId());
        employees.add(emp);
        Mediator.instance().employeeAdded(taskId, emp.getId());
        return emp.getId();
    }
    
    List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }
    
    private boolean save(String filename) {
        try(PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(filename)))) {
            employees.forEach(task -> {
                out.println(task);
            });
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    boolean save() {
        return save("Employees.csv");
    }
}
