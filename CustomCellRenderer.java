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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.JList;

class CustomCellRenderer extends JLabel implements ListCellRenderer<Object> {
    
    private Color cellColor;
    
    public CustomCellRenderer(Color cellColor) {
        setOpaque(true);
        this.cellColor = cellColor;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list,
                                            Object value,
                                            int index,
                                            boolean isSelected,
                                            boolean cellHasFocus) {
        
        setFont(getFont().deriveFont(Font.BOLD, 16.0f));
        
        if(value instanceof Project)
            setText(((Project)value).getName());
        else if(value instanceof Task)
            setText(((Task)value).getDescription());
        else if(value instanceof Employee)
            setText(((Employee)value).getName());
        
        if (isSelected) {
            setBackground(cellColor);
        }
        else {
            setBackground(Color.WHITE);
        }
        return this;
    }
}