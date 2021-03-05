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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Window;
import java.util.ArrayList;

class CustomWindowTerminator extends WindowAdapter {
    
    private ArrayList<Window> windows;
    private final DisplayManager mgr;
    private final int nextWindow;
    
    CustomWindowTerminator(ArrayList<Window> windows,
            DisplayManager mgr, int nextWindow) {
        this.windows = windows;
        this.mgr = mgr;
        this.nextWindow = nextWindow;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        
        if(windows != null)
            for(Window window : windows)
                if(window != null && window.isVisible())
                    return;
        
        switch(nextWindow) {
            case 0:
                mgr.terminate();
                break;
            case 1:
                mgr.showSplashPage(e.getWindow());
                break;
            case 2:
                mgr.showProjectList(e.getWindow());
                break;
            default:
                e.getWindow().setVisible(false);
        }
    }
    
    void addWindow(Window window) {
        if(windows == null)
            windows = new ArrayList<>();
        windows.add(window);
    }
}
