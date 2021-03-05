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

class Employee {
    
    private final int id;
    private String name;
    
    Employee(String empDetails) {
        String[] details = empDetails.strip().split("\s*[|]\s*", 2);
        id = Integer.parseInt(details[0]);
        name = details[1];
    }
    
    @Override
    public String toString() {
        return id + "|" + name;
    }
    
    int getId() {
        return id;
    }
    
    String getName() {
        return name;
    }
}
