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
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

class ProgressBar extends JComponent {
        
    private float completionFraction;
    private int fontSize;

    ProgressBar(float completionFraction, int fontSize) {
        this.completionFraction = completionFraction;
        this.fontSize = fontSize;
    }
    
    void setCompletionFraction(float completionFraction) {
        this.completionFraction = completionFraction;
        repaint();
    }
        
    @Override
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font(g2.getFont().getFontName(), Font.BOLD, fontSize);
        String message = String.format("Project Completion : %4.2f%%   ",
                100 * (float)completionFraction);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(message, context);
        int stringWidth = (int)Math.ceil(bounds.getWidth());
        int stringHeight = (int)Math.ceil(bounds.getHeight());
        float ascent = (float)(-bounds.getY());
        setPreferredSize(new Dimension(stringWidth + 16, stringHeight + 16));
        RoundRectangle2D.Float roundRect1 = new RoundRectangle2D.Float(3, 3,
        (stringWidth + 10) * completionFraction, stringHeight + 10, stringHeight/3, stringHeight/3);
        g2.setPaint(new Color(235, 20, 30));
        g2.fill(roundRect1);
        RoundRectangle2D.Float roundRect2 = new RoundRectangle2D.Float(3, 3,
        stringWidth + 10, stringHeight + 10, stringHeight/3, stringHeight/3);
        g2.setPaint(Color.BLACK);
        g2.draw(roundRect2);
        g2.setFont(font);
        g2.drawString(message, 8, ascent + 8);
    }
}

class Separator extends JComponent {
    
    private boolean horizontal;
    private Color color;
    
    Separator(boolean horizontal, Color color) {
        this.horizontal = horizontal;
        this.color = color;
    }
    
    @Override
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D)g;
        int width = getSize().width;
        int height = getSize().height;
        if(horizontal) {
            Rectangle2D.Float rect = new Rectangle2D.Float(0, height - 10, width, 10);
            g2.setPaint(color);
            g2.fill(rect);
        }
        else {
            Rectangle2D.Float rect = new Rectangle2D.Float(0, 0, 5, height);
            g2.setPaint(color);
            g2.fill(rect);
        }
    }
}

class TaskDetailsInputUI extends JFrame {
    
    private ProjectDetailsUI projectDetailsUI;
    
    TaskDetailsInputUI(ProjectDetailsUI projectDetailsUI) {
        
        this.projectDetailsUI = projectDetailsUI;
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        
        JButton button1 = new JButton("Enter");
        var constraints1 = new GridBagConstraints();
        constraints1.weightx = 100;
        constraints1.weighty = 100;
        constraints1.gridx = 1;
        constraints1.gridy = 2;
        constraints1.gridwidth = 1;
        constraints1.gridheight = 1;
        button1.setBackground(new Color(255, 210, 225));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        add(button1, constraints1);
        
        JTextField textField1 = new JTextField(20);
        var constraints2 = new GridBagConstraints();
        constraints2.weightx = 100;
        constraints2.weighty = 100;
        constraints2.gridx = 2;
        constraints2.gridy = 2;
        constraints2.gridwidth = 4;
        constraints2.gridheight = 1;
        constraints2.insets.right = 30;
        constraints2.anchor = GridBagConstraints.WEST;
        add(textField1, constraints2);
        
        JLabel label1 = new JLabel("Enter task name :");
        var constraints3 = new GridBagConstraints();
        constraints3.weightx = 100;
        constraints3.weighty = 100;
        constraints3.gridx = 2;
        constraints3.gridy = 1;
        constraints3.gridwidth = 4;
        constraints3.gridheight = 1;
        constraints3.anchor = GridBagConstraints.WEST;
        label1.setFont(label1.getFont().deriveFont(Font.BOLD, 16.0f));
        add(label1, constraints3);
        
        button1.addActionListener(event -> {
            String inputDesc = textField1.getText().strip();
            if(inputDesc != null && inputDesc.length() > 0)
                projectDetailsUI.addTask(false + "|" + inputDesc);
        });
        
        setSize(400, 200);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CustomWindowTerminator(null, null, -1));
    }
}

class EmployeeDetailsInputUI extends JFrame {
    
    private JFrame projectDetailsUI;
    
    EmployeeDetailsInputUI(JFrame projectDetailsUI) {
        
        this.projectDetailsUI = projectDetailsUI;
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        addWindowListener(new CustomWindowTerminator(null, null, -1));
        
        JButton button1 = new JButton("Enter");
        var constraints1 = new GridBagConstraints();
        constraints1.weightx = 100;
        constraints1.weighty = 100;
        constraints1.gridx = 1;
        constraints1.gridy = 2;
        constraints1.gridwidth = 1;
        constraints1.gridheight = 1;
        button1.setBackground(new Color(255, 210, 225));
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        add(button1, constraints1);
        
        JTextField textField1 = new JTextField(20);
        var constraints2 = new GridBagConstraints();
        constraints2.weightx = 100;
        constraints2.weighty = 100;
        constraints2.gridx = 2;
        constraints2.gridy = 2;
        constraints2.gridwidth = 4;
        constraints2.gridheight = 1;
        constraints2.insets.right = 30;
        constraints2.anchor = GridBagConstraints.WEST;
        add(textField1, constraints2);
        
        JLabel label1 = new JLabel("Enter employee name :");
        var constraints3 = new GridBagConstraints();
        constraints3.weightx = 100;
        constraints3.weighty = 100;
        constraints3.gridx = 2;
        constraints3.gridy = 1;
        constraints3.gridwidth = 4;
        constraints3.gridheight = 1;
        constraints3.anchor = GridBagConstraints.SOUTHWEST;
        label1.setFont(label1.getFont().deriveFont(Font.BOLD, 16.0f));
        add(label1, constraints3);
        
        JButton button2 = new JButton("Enter");
        var constraints4 = new GridBagConstraints();
        constraints4.weightx = 100;
        constraints4.weighty = 100;
        constraints4.gridx = 1;
        constraints4.gridy = 5;
        constraints4.gridwidth = 1;
        constraints4.gridheight = 1;
        button2.setBackground(new Color(180, 255, 180));
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        add(button2, constraints4);
        
        JTextField textField2 = new JTextField(20);
        var constraints5 = new GridBagConstraints();
        constraints5.weightx = 100;
        constraints5.weighty = 100;
        constraints5.gridx = 2;
        constraints5.gridy = 5;
        constraints5.gridwidth = 4;
        constraints5.gridheight = 1;
        constraints5.insets.right = 30;
        constraints5.anchor = GridBagConstraints.WEST;
        add(textField2, constraints5);
        
        JLabel label2 = new JLabel("Enter employee ID :");
        var constraints6 = new GridBagConstraints();
        constraints6.weightx = 100;
        constraints6.weighty = 100;
        constraints6.gridx = 2;
        constraints6.gridy = 4;
        constraints6.gridwidth = 4;
        constraints6.gridheight = 1;
        constraints6.anchor = GridBagConstraints.SOUTHWEST;
        label2.setFont(label1.getFont().deriveFont(Font.BOLD, 16.0f));
        add(label2, constraints6);
        
        setSize(400, 200);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
}

class ProjectDetailsUI extends JFrame {
    
    private DisplayManager mgr;
    private float completionFraction;
    private ProgressBar progressBar;
    private Project project;
    private JList<Task> taskList;
    private DefaultListModel<Task> taskListModel;
    private JList<Employee> employeesList;
    private DefaultListModel<Employee> employeeListModel;
    private TaskDetailsInputUI taskDetailsWindow;
    private EmployeeDetailsInputUI empDetailsWindow;
    private CustomWindowTerminator windowListener;
    
    
    ProjectDetailsUI(Project project, DisplayManager mgr) {
        
        this.mgr = mgr;
        progressBar = null;
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        employeeListModel = new DefaultListModel<>();
        employeesList = new JList<>(employeeListModel);
        windowListener = new CustomWindowTerminator(null, mgr, 2);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents(project);
    }
    
    private void initWindows() {
        
        if(taskDetailsWindow == null) {
            taskDetailsWindow = new TaskDetailsInputUI(this);
            windowListener.addWindow(taskDetailsWindow);
        }
        if(empDetailsWindow == null) {
            empDetailsWindow = new EmployeeDetailsInputUI(this);
            windowListener.addWindow(empDetailsWindow);
        }
    }
    
    void initComponents(Project project) {
        
        if(project == null)
            return;
        
        getContentPane().removeAll();
        completionFraction = 0.0f;
        taskListModel.clear();
        employeeListModel.clear();
        this.project = project;
        populateTasks(TaskManager.instance().getTasks(),
                Mediator.instance().getTaskAssignments());
        
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(5, 5));
        addWindowListener(windowListener);
        
        JPanel projectStatusPanel = new JPanel();
        projectStatusPanel.setBackground(Color.WHITE);
        projectStatusPanel.setLayout(new BorderLayout(5, 5));
        
        var separator1 = new Separator(true, new Color(150, 255, 255));
        separator1.setPreferredSize(new Dimension(5, 10));
        projectStatusPanel.add(separator1, BorderLayout.SOUTH);
        
        JPanel progressBarPanel = new JPanel();
        progressBarPanel.setBackground(Color.WHITE);
        if(progressBar == null)
            progressBar = new ProgressBar(completionFraction, 26);
        else
            progressBar.setCompletionFraction(completionFraction);
        progressBarPanel.add(progressBar);
        projectStatusPanel.add(progressBarPanel, BorderLayout.CENTER);
        
        JPanel progressInfoPanel = new JPanel();
        progressInfoPanel.setBackground(Color.WHITE);
        JLabel projectInfoLabel = new JLabel(project.getName());
        projectInfoLabel.setFont(projectInfoLabel.getFont().deriveFont(Font.BOLD, 40.0f));
        progressInfoPanel.add(projectInfoLabel);
        projectStatusPanel.add(progressInfoPanel, BorderLayout.NORTH);
        
        
        JPanel tasksPanel = new JPanel(new BorderLayout(5, 5));
        tasksPanel.setBackground(Color.WHITE);
        
        JPanel taskLabelPanel = new JPanel();
        taskLabelPanel.setBackground(Color.WHITE);
        var taskLabel = new JLabel("Tasks");
        taskLabel.setFont(taskLabel.getFont().deriveFont(Font.BOLD, 28.0f));
        taskLabelPanel.add(taskLabel);
        tasksPanel.add(taskLabelPanel, BorderLayout.NORTH);
        
        taskList.setCellRenderer(new CustomCellRenderer(new Color(180, 255, 180)));
        JScrollPane tasksScrollPane = new JScrollPane(taskList);
        tasksScrollPane.setBorder(BorderFactory.createEmptyBorder());
        tasksPanel.add(tasksScrollPane, BorderLayout.CENTER);
        
        JPanel tasksOptionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tasksOptionsPanel.setBackground(Color.WHITE);
        
        JButton deleteTaskButton = new JButton(new ImageIcon("DeleteImage.png"));
        deleteTaskButton.setContentAreaFilled(false);
        deleteTaskButton.setBorderPainted(false);
        deleteTaskButton.setFocusPainted(false);
        tasksOptionsPanel.add(deleteTaskButton);
        
        JButton addTaskButton = new JButton(new ImageIcon("AddImage.png"));
        addTaskButton.setContentAreaFilled(false);
        addTaskButton.setBorderPainted(false);
        addTaskButton.setFocusPainted(false);
        tasksOptionsPanel.add(addTaskButton);
        
        JButton tickTaskButton = new JButton(new ImageIcon("TickImage.png"));
        tickTaskButton.setContentAreaFilled(false);
        tickTaskButton.setBorderPainted(false);
        tickTaskButton.setFocusPainted(false);
        tasksOptionsPanel.add(tickTaskButton);
        
        
        tasksPanel.add(tasksOptionsPanel, BorderLayout.SOUTH);
        
        
        JPanel outerEmployeesPanel = new JPanel(new BorderLayout(50, 5));
        outerEmployeesPanel.setBackground(Color.WHITE);
        
        var separator2 = new Separator(false, new Color(0, 150, 255));
        separator2.setPreferredSize(new Dimension(5, 10));
        outerEmployeesPanel.add(separator2, BorderLayout.WEST);
        
        JPanel employeesPanel = new JPanel(new BorderLayout(5, 5));
        employeesPanel.setBackground(Color.WHITE);
        outerEmployeesPanel.add(employeesPanel, BorderLayout.CENTER);
        
        JScrollPane empScrollPane = new JScrollPane();
        empScrollPane.setBorder(BorderFactory.createEmptyBorder());
        employeesList.setCellRenderer(new CustomCellRenderer(new Color(175, 175, 225)));
        empScrollPane.getViewport().setView(employeesList);
        employeesPanel.add(empScrollPane, BorderLayout.CENTER);
        
        JPanel employeesInfo = new JPanel(new GridLayout(0, 1));
        employeesInfo.setBackground(Color.WHITE);
        var taskDescLabel = new JLabel(" ");
        var taskCompletionStatusLabel = new JLabel(" ");
        var padding1 = new JLabel(" ");
        var employeesLabel = new JLabel(" ");
        employeesInfo.add(taskDescLabel);
        taskDescLabel.setFont(taskDescLabel.getFont().deriveFont(Font.BOLD, 28.0f));
        employeesInfo.add(taskCompletionStatusLabel);
        taskCompletionStatusLabel.setFont(taskCompletionStatusLabel.getFont().deriveFont(Font.BOLD, 20.0f));
        employeesInfo.add(padding1);
        employeesInfo.add(employeesLabel);
        employeesLabel.setFont(employeesLabel.getFont().deriveFont(Font.BOLD, 20.0f));
        employeesPanel.add(employeesInfo, BorderLayout.NORTH);
        
        var separator3 = new Separator(false, Color.WHITE);
        separator3.setPreferredSize(new Dimension(20, 0));
        tasksPanel.add(separator3, BorderLayout.WEST);
        var separator4 = new Separator(false, Color.WHITE);
        separator4.setPreferredSize(new Dimension(20, 0));
        tasksPanel.add(separator4, BorderLayout.EAST);
        var separator5 = new Separator(false, Color.WHITE);
        separator5.setPreferredSize(new Dimension(0, 0));
        outerEmployeesPanel.add(separator5, BorderLayout.EAST);
        var separator6 = new Separator(false, Color.WHITE);
        separator6.setPreferredSize(new Dimension(40, 0));
        employeesPanel.add(separator6, BorderLayout.WEST);
        
        taskList.addListSelectionListener(event -> {
            
            Task task = taskList.getSelectedValue();
            if(task == null)
                employeesPanel.setVisible(false);
            else
            {
                employeesPanel.setVisible(true);
                taskDescLabel.setText(task.getDescription());
                if(task.isCompleted())
                    taskCompletionStatusLabel.setText("Status : Complete");
                else
                    taskCompletionStatusLabel.setText("Status : Incomplete");
                employeesLabel.setText("Employees :");
                populateEmployees(task.getId(), EmployeeManager.instance().getEmployees(),
                    Mediator.instance().getEmployeesAssignments());
            }
        });
        
        JPanel EmployeesOptionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        EmployeesOptionsPanel.setBackground(Color.WHITE);
        
        JButton deleteEmployeeButton = new JButton(new ImageIcon("DeleteImage.png"));
        deleteEmployeeButton.setContentAreaFilled(false);
        deleteEmployeeButton.setBorderPainted(false);
        deleteEmployeeButton.setFocusPainted(false);
        EmployeesOptionsPanel.add(deleteEmployeeButton);
        
        JButton addEmployeeButton = new JButton(new ImageIcon("AddImage.png"));
        addEmployeeButton.setContentAreaFilled(false);
        addEmployeeButton.setBorderPainted(false);
        addEmployeeButton.setFocusPainted(false);
        EmployeesOptionsPanel.add(addEmployeeButton);
        
        employeesPanel.add(EmployeesOptionsPanel, BorderLayout.SOUTH);
        
        deleteTaskButton.addActionListener(event -> {
            int index = taskList.getSelectedIndex();
            if(index >= 0)
                taskListModel.remove(index);
            updateProgressBar();
        });
        
        addTaskButton.addActionListener(event -> {
            initWindows();
            taskDetailsWindow.setLocationRelativeTo(this);
            taskDetailsWindow.setVisible(true);
        });
        
        tickTaskButton.addActionListener(event -> {
            Task task;
            if((task = taskList.getSelectedValue()) != null)
                task.markAsCompleted();
                taskCompletionStatusLabel.setText("Status : Complete");
                updateProgressBar();
        });
        
        addEmployeeButton.addActionListener(event -> {
            initWindows();
            empDetailsWindow.setLocationRelativeTo(this);
            empDetailsWindow.setVisible(true);
        });
        
        add(projectStatusPanel, BorderLayout.NORTH);
        add(tasksPanel, BorderLayout.WEST);
        add(outerEmployeesPanel, BorderLayout.CENTER);
        int projectStatusPanelHeight = projectStatusPanel.getPreferredSize().height;
        setSize(projectStatusPanelHeight * 9, projectStatusPanelHeight * 6);
        //repaint();
    }
    
    void addTask(String taskDetails) {
        TaskManager.instance().addTask(project.getId(), taskDetails);
        populateTasks(TaskManager.instance().getTasks(),
                Mediator.instance().getTaskAssignments());
        updateProgressBar();
    }
    
    void updateProgressBar() {
        if(taskListModel.isEmpty()) 
            completionFraction = 0.0f;
        else {
            int completed = 0;
            for(int i = 0; i < taskListModel.getSize(); ++i) {
                if(taskListModel.getElementAt(i).isCompleted())
                    completed++;
                completionFraction = (float)completed/taskListModel.getSize();
            }
        }
        progressBar.setCompletionFraction(completionFraction);
    }
    
    private void populateTasks(List<Task> tasks,
            Map<Integer, ArrayList<Integer>> tasksAssigned) {
        
        taskListModel.clear();
        if(project == null)
            return;
        tasks.forEach(task -> {
            int taskId = task.getId();
            var taskIdList = tasksAssigned.get(project.getId());
            if(taskIdList != null && taskIdList.contains(taskId))
                taskListModel.addElement(task);
        });
    }
    
    private void populateEmployees(int taskId, List<Employee> employees,
            Map<Integer, ArrayList<Integer>> employeesAssigned) {
        
        employeeListModel.clear();
        employees.forEach(employee -> {
            int empId = employee.getId();
            var empIdList = employeesAssigned.get(taskId);
            if(empIdList != null && empIdList.contains(empId))
                employeeListModel.addElement(employee);
        });
    }
    
    public static void main(String args[]) {
        Mediator.instance().init();
        EventQueue.invokeLater(() -> {
        new ProjectDetailsUI(new Project("1|Project 1"), new DisplayManager()).setVisible(true);
        });
    }
}
