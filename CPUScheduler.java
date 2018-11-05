/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.scheduler;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Jandre
 */
public class CPUScheduler {
    
    public static void CreateAndShowGUI() //creates application
    {
        JFrame frame = new JFrame("CPU Simulator");
        frame.setPreferredSize(new Dimension (800, 620)); //Sets dimension to 800 x 800 px
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allows application to close
        frame.setEnabled(true); //Allows application to accept certain keynotes
        CPUPanel panel = new CPUPanel();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) 
    {
        CreateAndShowGUI();
    }
}
