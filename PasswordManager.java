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
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class PasswordManager {
    
    private String password;
    private static PasswordManager passMgr;
    
    private PasswordManager() {
        password = null;
    }
    
    static PasswordManager instance() {
        if(passMgr == null)
            passMgr = new PasswordManager();
        return passMgr;
    }
    
    boolean init() {
        try(BufferedReader in = new BufferedReader(new FileReader("Password.csv"))) {
            password = in.readLine();
        }
        catch(IOException e) {
            return false;
        }
        return true;
    }
    
    boolean match(char[] input) {
        if(input.length != password.length())
            return false;
        for(int i = 0; i < input.length; ++i)
            if(input[i] != password.charAt(i))
                return false;
        return true;
    }
}
