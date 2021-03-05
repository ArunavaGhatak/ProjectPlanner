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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.EventQueue;
import java.awt.Color;

class PasswordPage extends JFrame {
    
    private SplashPage splashPage;
    
    PasswordPage(SplashPage splashPage) {
        
        this.splashPage = splashPage;
        setLayout(new GridBagLayout());
        
        JLabel label1 = new JLabel("Enter password : ");
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
                    splashPage.passwordMatched();
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
}

class SplashPage extends JFrame {
    
    private DisplayManager dispMgr;
    private PasswordPage passPage;
    private CustomWindowTerminator windowListener;
    
    SplashPage(DisplayManager dispMgr) {
        
        this.dispMgr = dispMgr;
        passPage = null;
        windowListener = new CustomWindowTerminator(null, dispMgr, 0);
        
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(windowListener);
        pack();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        
        setLayout(new GridBagLayout());
        
        JLabel header = new JLabel("PROJECT PLANNER");
        header.setFont(header.getFont().deriveFont(32.0f));
        var constraints1 = new GridBagConstraints();
        constraints1.weightx = 100;
        constraints1.weighty = 100;
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.gridwidth = 5;
        constraints1.gridheight = 2;
        constraints1.insets.bottom = 10;
        constraints1.insets.top = 10;
        constraints1.insets.left = 30;
        constraints1.insets.right = 30;
        constraints1.anchor = GridBagConstraints.NORTH;
        add(header, constraints1);
        //header.setBounds(30, 10, 350, 30);
        //header.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));//
        
        JLabel label = new JLabel("What is your role in the project ?");
        label.setFont(label.getFont().deriveFont(16.0f));
        var constraints2 = new GridBagConstraints();
        constraints2.weightx = 100;
        constraints2.weighty = 0;
        constraints2.gridx = 1;
        constraints2.gridy = 3;
        constraints2.gridwidth = 5;
        constraints2.gridheight = 1;
        constraints2.insets.bottom = 10;
        add(label, constraints2);
        
        JButton b1 = new JButton("MANAGER");
        var constraints3 = new GridBagConstraints();
        constraints3.weightx = 100;
        constraints3.weighty = 0;
        constraints3.gridx = 1;
        constraints3.gridy = 4;
        constraints3.gridwidth = 1;
        constraints3.gridheight = 1;
        constraints3.insets.bottom = 10;
        b1.setBackground(new Color(200, 225, 225));
        b1.setFocusPainted(false);
        b1.setBorderPainted(false);
        add(b1, constraints3);
        
        JButton b2 = new JButton("EMPLOYEE");
        var constraints4 = new GridBagConstraints();
        constraints4.weightx = 100;
        constraints4.weighty = 100;
        constraints4.gridx = 1;
        constraints4.gridy = 5;
        constraints4.gridwidth = 1;
        constraints4.gridheight = 1;
        constraints4.insets.bottom = 30;
        constraints4.anchor = GridBagConstraints.NORTH;
        b2.setBackground(new Color(180, 255, 180));
        b2.setFocusPainted(false);
        b2.setBorderPainted(false);
        add(b2, constraints4);
        
        b1.addActionListener(event -> {
            initWindows();
            passPage.setLocationRelativeTo(this);
            passPage.setVisible(true);
        });
    }
    
    private void initWindows() {
        if(passPage == null) {
            passPage = new PasswordPage(this);
            windowListener.addWindow(passPage);
        } 
    }
    
    void passwordMatched() {
        dispMgr.showProjectList(this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UIManager.put( "Label.foreground", Color.BLACK );
        UIManager.put( "Label.background", Color.WHITE );
        PasswordManager.instance().init();
        EventQueue.invokeLater(() -> {
            new SplashPage(new DisplayManager()).setVisible(true);
            //new PasswordPage().setVisible(true);
        });
    }
    
}
