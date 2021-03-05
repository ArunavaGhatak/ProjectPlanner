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
import java.awt.Window;

class DisplayManager {
    
    private Window splashPage;
    private Window projectListUI;
    private ProjectDetailsUI projectDetailsUI;
    
    void showSplashPage(Window window) {
        if(!(window instanceof SplashPage))
            Mediator.instance().save();
        if(splashPage == null)
            splashPage = new SplashPage(this);
        splashPage.setLocationRelativeTo(window);
        window.setVisible(false);
        splashPage.setVisible(true);
    }
    
    void showProjectList(Window window) {
        if(!(window instanceof SplashPage))
            Mediator.instance().save();
        if(projectListUI == null)
            projectListUI = new ProjectListUI(this);
        projectListUI.setLocationRelativeTo(window);
        window.setVisible(false);
        projectListUI.setVisible(true);
    }
    
    void showProjectDetails(Window window, Project project) {
        if(!(window instanceof SplashPage))
            Mediator.instance().save();
        if(projectDetailsUI == null)
            projectDetailsUI = new ProjectDetailsUI(project, this);
        projectDetailsUI.setLocationRelativeTo(window);
        window.setVisible(false);
        projectDetailsUI.initComponents(project);
        projectDetailsUI.setVisible(true);
    }
    
    void terminate() {
        System.exit(0);
    }
    
    void start() {
        projectListUI = null;
        projectDetailsUI = null;
        Mediator.instance().init();
        splashPage = new SplashPage(this);
        splashPage.setVisible(true);
    }
    
    public static void main(String args[]) {
        DisplayManager mgr = new DisplayManager();
        mgr.start();
    }
}
