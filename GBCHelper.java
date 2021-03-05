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
import java.awt.GridBagConstraints;
import java.awt.Insets;

class GBCHelper extends GridBagConstraints {
    
    GBCHelper(int gridx, int gridy, int gridwidth, int gridheight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridwidth;
        this.gridheight = gridheight;
    }
    
    GBCHelper setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }
    
    GBCHelper setFill(int fill) {
        this.fill = fill;
        return this;
    }
    
    public GBCHelper setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }
    
    public GBCHelper setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }
    
    public GBCHelper setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }
}