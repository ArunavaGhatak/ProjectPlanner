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
import java.util.List;
import java.util.Collections;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

class ProjectManager {
    
    private static ProjectManager projMgr;
    private ArrayList<Project> projects;
    private int lastProjId;
    
    private ProjectManager() {
        projects = new ArrayList<>();
        lastProjId = 0;
    }
    
    static ProjectManager instance() {
        if(projMgr == null)
            projMgr = new ProjectManager();
        return projMgr;
    }
    
    private boolean init(String filename) {
        
        projects.clear();
        File file = new File(filename);
        try {
            if(file.createNewFile())
                try(BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                    out.write('0');
                }
            else
                try(BufferedReader in = new BufferedReader(new FileReader(file))) {
                    lastProjId = Integer.parseInt(in.readLine());
                    String projDetails;
                    while((projDetails = in.readLine()) != null) {
                        projects.add(new Project(projDetails));
                    }
                }
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    boolean init() {
        return init("Projects.csv");
    }
    
    void deleteProject(int projId) {
        projects.removeIf(project -> project.getId() == projId);
        Mediator.instance().projectDeleted(projId);
    }
    
    int addProject(String projDetails) {
        Project proj = new Project(++lastProjId + "|" + projDetails);
        deleteProject(proj.getId());
        projects.add(proj);
        Mediator.instance().projectCreated(proj.getId());
        return proj.getId();
    }
    
    List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }
    
    private boolean save(String filename) {
        try(PrintWriter out = new PrintWriter(
                new BufferedWriter(new FileWriter(filename)))) {
            out.println(lastProjId);
            projects.forEach(project -> {
                out.println(project);
            });
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    boolean save() {
        return save("Projects.csv");
    }
}
