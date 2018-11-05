/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu.scheduler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
public class CPUPanel extends JPanel{
    JTable inputTable, outputTable;
    JScrollPane inputScrollPane, outputScrollPane;
    JScrollBar inputScrollBar, outputScrollBar;
    JButton beginButton, oneStepButton, randomProcessButton, addProcessButton, resetButton;
    JLabel inputTableLabel, outputTableLabel, averageWaitTime, averageTurnaroundTime;
    JRadioButton firstComeFirstServed, shortestJobFirst, roundRobin;
    ButtonGroup methods = new ButtonGroup();
    JCheckBox priority;
    Object[][] inputData = {{1, 40, 10, 4}, {2, 4, 7, 2}};
    Object[][] outputData = {{2, 4, 11},{1, 40, 50}};
    
    public CPUPanel()
    {
        firstComeFirstServed = new JRadioButton("First Come, First Served");
        shortestJobFirst = new JRadioButton("Shortest Job First");
        roundRobin = new JRadioButton("Round Robin");
        priority = new JCheckBox("Priority");
        firstComeFirstServed.setPreferredSize(new Dimension(190, 20));
        shortestJobFirst.setPreferredSize(new Dimension(190, 20));
        roundRobin.setPreferredSize(new Dimension(280, 20));
        priority.setPreferredSize(new Dimension(110, 20));
        methods.add(firstComeFirstServed);
        methods.add(shortestJobFirst);
        methods.add(roundRobin);
        
        beginButton = new JButton("Run");
        beginButton.setPreferredSize(new Dimension(150, 50));
        beginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        randomProcessButton = new JButton("<html><center>Add Random<br>Process</center></html>");
        randomProcessButton.setPreferredSize(new Dimension(150, 50));
        randomProcessButton.setFont(new Font("Arial", Font.PLAIN, 12));
        oneStepButton = new JButton("Next Step");
        oneStepButton.setPreferredSize(new Dimension(150, 50));
        oneStepButton.setFont(new Font("Arial", Font.PLAIN, 12));
        addProcessButton = new JButton("<html><center>Add New<br>Process</center></html>");
        addProcessButton.setPreferredSize(new Dimension(150, 50));
        addProcessButton.setFont(new Font("Arial", Font.PLAIN, 12));
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(150, 50));
        resetButton.setFont(new Font("Arial", Font.PLAIN, 12));
        
        String[] inputColumnNames = {"Thread Number", "Arrival Time", "Burst Time", "Priority"};
        String[] outputColumnNames = {"Thread Number", "Time Arrived", "Time Used"};
        inputTable = new JTable(inputData, inputColumnNames);
        inputScrollPane = new JScrollPane(inputTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollBar = inputScrollPane.getVerticalScrollBar();
        inputScrollPane.setPreferredSize(new Dimension(700, 200));
        inputScrollBar.setPreferredSize(new Dimension(10, 0));
        outputTable = new JTable(outputData, outputColumnNames);
        outputScrollPane = new JScrollPane(outputTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputScrollBar = outputScrollPane.getVerticalScrollBar();
        outputScrollPane.setPreferredSize(new Dimension(700, 200));
        outputScrollBar.setPreferredSize(new Dimension(10, 0));
        inputTableLabel = new JLabel("Inputed Processes");
        outputTableLabel = new JLabel("Wait Times and Turnaround Times");
        
        averageWaitTime = new JLabel("Average Wait Time: 0.0", SwingConstants.CENTER);
        averageTurnaroundTime = new JLabel("Average Turnaround Time: 0.0", SwingConstants.CENTER);
        averageWaitTime.setPreferredSize(new Dimension(800, 20));
        averageTurnaroundTime.setPreferredSize(new Dimension(800, 20));
        
        add(beginButton);
        add(randomProcessButton);
        add(oneStepButton);
        add(addProcessButton);
        add(resetButton);
        add(firstComeFirstServed);
        add(shortestJobFirst);
        add(roundRobin);
        add(priority);
        add(inputTableLabel);
        add(inputScrollPane);
        add(outputTableLabel);
        add(outputScrollPane);
        add(averageWaitTime);
        add(averageTurnaroundTime);
    }
    
}
