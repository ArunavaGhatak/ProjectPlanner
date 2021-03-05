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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.EventQueue;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;

/*class ProjectDetailsInputUI extends JFrame {
    
    private ProjectListUI projectListUI;
    
    ProjectDetailsInputUI(ProjectListUI projectListUI) {
        
        this.projectListUI = projectListUI;
        setLayout(new GridBagLayout());
        
        JLabel label1 = new JLabel("Enter project name : ");
        label1.setFont(label1.getFont().deriveFont(20.0f));
        var constraints1 = new GridBagConstraints();
        constraints1.weightx = 100;
        constraints1.weighty = 100;
        constraints1.gridx = 2;
        constraints1.gridy = 0;
        constraints1.gridwidth = 4;
        constraints1.gridheight = 1;
        constraints1.anchor = GridBagConstraints.SOUTHWEST;
        constraints1.insets.bottom = 10;
        constraints1.insets.left = 10;
        add(label1, constraints1);
        
        JButton b1 = new JButton("Enter");
        var constraints2 = new GridBagConstraints();
        constraints2.weightx = 100;
        constraints2.weighty = 0;
        constraints2.gridx = 1;
        constraints2.gridy = 1;
        constraints2.gridwidth = 1;
        constraints2.gridheight = 1;
        constraints2.anchor = GridBagConstraints.EAST;
        constraints2.insets.left = 20;
        b1.setBackground(new Color(255, 210, 225));
        b1.setFocusPainted(false);
        b1.setBorderPainted(false);
        add(b1, constraints2);
        
        JPasswordField passField = new JPasswordField(20);
        var constraints3 = new GridBagConstraints();
        constraints3.weightx = 100;
        constraints3.weighty = 0;
        constraints3.gridx = 2;
        constraints3.gridy = 1;
        constraints3.gridwidth = 4;
        constraints3.gridheight = 1;
        constraints3.anchor = GridBagConstraints.WEST;
        constraints3.insets.left = 10;
        constraints3.insets.right = 20;
        add(passField, constraints3);
        
        JLabel label2 = new JLabel(" ");
        var constraints4 = new GridBagConstraints();
        label1.setFont(label1.getFont().deriveFont(16.0f));
        constraints4.weightx = 100;
        constraints4.weighty = 100;
        constraints4.gridx = 0;
        constraints4.gridy = 2;
        constraints4.gridwidth = 6;
        constraints4.gridheight = 1;
        constraints4.anchor = GridBagConstraints.SOUTH;
        constraints4.insets.bottom = 10;
        constraints4.insets.top = 20;
        add(label2, constraints4);
        
        b1.addActionListener(event -> {
            char inputPass[] = passField.getPassword();
            if(inputPass != null && inputPass.length > 0) {
                if(PasswordManager.instance().match(inputPass)){
                    label2.setText("");
                    setVisible(false);
                    //splashPage.passwordMatched();
                }
                else
                    label2.setText("Wrong Password");
                passField.setText("");
            }
        });
        
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CustomWindowTerminator(null, null, -1));
        setSize(400, 150);
    }
}*/


class DetailsInputUI extends JFrame {
    
    protected JTextField textField;
    protected JLabel label2;
    protected JButton button;
    
    DetailsInputUI(String prompt, boolean password) {
        
        setLayout(new GridBagLayout());
        
        JLabel label1 = new JLabel(prompt);
        label1.setFont(label1.getFont().deriveFont(20.0f));
        var constraints1 = new GridBagConstraints();
        add(label1, new GBCHelper(1, 0, 5, 1).setInsets(0, 10, 0, 0).setAnchor(
                GridBagConstraints.SOUTHWEST).setWeight(100, 100));
        
        button = new JButton("Enter");
        button.setBackground(new Color(255, 210, 225));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        add(button, new GBCHelper(0, 1, 1, 1).setInsets(10).setAnchor(
                GridBagConstraints.NORTHEAST).setWeight(100, 0));
        
        if(password)
            textField = new JPasswordField(20);
        else
            textField = new JTextField(20);
        add(textField, new GBCHelper(1, 1, 5, 1).setInsets(10).setAnchor(
                GridBagConstraints.NORTHWEST).setWeight(100, 0));
        
        label2 = new JLabel(" ");
        label1.setFont(label1.getFont().deriveFont(16.0f));
        add(label2, new GBCHelper(0, 2, 6, 1).setInsets(20, 10, 10, 10).setAnchor(
                GridBagConstraints.SOUTH).setWeight(100, 100));
        
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CustomWindowTerminator(null, null, -1));
        int textFieldWidth = textField.getPreferredSize().width;
        setSize(textFieldWidth * 7 / 4, textFieldWidth * 4 / 5);
    }
}

class ProjectDetailsInputUI extends DetailsInputUI {
    
    private ProjectListUI projectListUI;
    
    ProjectDetailsInputUI(ProjectListUI projectListUI) {
        
        super("Enter project name :", false);
        this.projectListUI = projectListUI;
        
        button.addActionListener(event -> {
            String inputName = textField.getText().strip();
            if(inputName != null && inputName.length() > 0)
                projectListUI.addProject(inputName);
        });
    }
}

class ProjectListUI extends JFrame {
    
    private DisplayManager dispMgr;
    private ProjectDetailsInputUI projInputPage;
    private CustomWindowTerminator windowListener;
    private JList<Project> projList;
    private DefaultListModel<Project> projListModel;
    
    
    ProjectListUI(DisplayManager dispMgr) {
        
        this.dispMgr = dispMgr;
        projInputPage = null;
        windowListener = new CustomWindowTerminator(null, dispMgr, 1);
        projListModel = new DefaultListModel<>();
        ProjectManager.instance().getProjects().forEach(project -> 
                projListModel.addElement(project));
        projList = new JList<>(projListModel);
        
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        addWindowListener(windowListener);
        pack();
    }
    
    private void initComponents() {
        
        setLayout(new GridBagLayout());
        
        JLabel header = new JLabel("PROJECT  LIST");
        header.setFont(header.getFont().deriveFont(32.0f));
        
        add(header, new GBCHelper(0, 0, 6, 2).setInsets(10));
        
        projList.setCellRenderer(new CustomCellRenderer(new Color(255, 220, 100)));
        projList.setFixedCellWidth(300);
        JScrollPane projScrollPane = new JScrollPane(projList);
        projScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(projScrollPane, new GBCHelper(0, 2, 4, 5).setInsets(10,50,10,10).setAnchor(
                GridBagConstraints.CENTER).setFill(GridBagConstraints.BOTH));
        
        JButton b1 = new JButton("OPEN");
        b1.setBackground(new Color(255, 210, 225));
        b1.setFocusPainted(false);
        b1.setBorderPainted(false);
        add(b1, new GBCHelper(5, 2, 1, 1).setFill(
                GridBagConstraints.HORIZONTAL).setInsets(10, 10, 30, 30));
        
        b1.addActionListener(event -> {
            Project project = null;
            if((project = projList.getSelectedValue()) != null)
                dispMgr.showProjectDetails(this, project);
        });
        
        JButton b2 = new JButton("NEW");
        b2.setBackground(new Color(200, 225, 225));
        b2.setFocusPainted(false);
        b2.setBorderPainted(false);
        add(b2, new GBCHelper(5, 4, 1, 1).setFill(
                GridBagConstraints.HORIZONTAL).setInsets(10, 10, 30, 30));
        
        b2.addActionListener(event -> {
            initWindows();
            projInputPage.setLocationRelativeTo(this);
            projInputPage.setVisible(true);
        });
        
        JButton b3 = new JButton("DELETE");
        b3.setBackground(new Color(175, 175, 225));
        b3.setFocusPainted(false);
        b3.setBorderPainted(false);
        add(b3, new GBCHelper(5, 6, 1, 1).setFill(
                GridBagConstraints.HORIZONTAL).setInsets(10, 10, 30, 30));//.setAnchor(GridBagConstraints.CENTER));
        
        b3.addActionListener(event -> {
            int index = projList.getSelectedIndex();
            if(index >= 0) {
                ProjectManager.instance().deleteProject(projList.getSelectedValue().getId());
                projListModel.clear();
                ProjectManager.instance().getProjects().forEach(project -> 
                projListModel.addElement(project));
            }
        });
        
        JLabel label = new JLabel(" ");
        header.setFont(header.getFont().deriveFont(16.0f));
        add(label, new GBCHelper(0, 7, 6, 1).setInsets(10, 0, 0, 0));
    }
    
    private void initWindows() {
        if(projInputPage == null) {
            projInputPage = new ProjectDetailsInputUI(this);
            windowListener.addWindow(projInputPage);
        }
    }
    
    void addProject(String projDetails) {
        ProjectManager.instance().addProject(projDetails);
        projListModel.clear();
        ProjectManager.instance().getProjects().forEach(project -> 
                projListModel.addElement(project));
    }
    
    /*private void populateProjects(List<Project> projects) {
        
        projListModel.clear();
        if(project == null)
            return;
        projects.forEach(project -> {
            int taskId = project.getId();
            var taskIdList = tasksAssigned.get(project.getId());
            if(taskIdList != null && taskIdList.contains(taskId))
                taskListModel.addElement(task);
        });
    }*/
    
    public static void main(String[] args) {
        UIManager.put( "Label.foreground", Color.BLACK );
        UIManager.put( "Label.background", Color.WHITE );
        Mediator.instance().init();
        EventQueue.invokeLater(() -> {
            new ProjectListUI(new DisplayManager()).setVisible(true);
            //new DetailsInputUI("Enter project name : ", true).setVisible(true);
        });
    }
}
