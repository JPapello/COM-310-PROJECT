/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// @Author Jude Andre II
package cpu.scheduler;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
public class CPUPanel extends JPanel implements ActionListener{
    JTable inputTable, outputTable;
    JScrollPane inputScrollPane, outputScrollPane;
    JButton beginButton, oneStepButton, randomProcessButton, addProcessButton, removeProcessButton, resetButton;
    JLabel inputTableLabel, outputTableLabel, averageWaitTime, averageTurnaroundTime;
    JRadioButton firstComeFirstServed, shortestJobFirst, roundRobin;
    ButtonGroup methods = new ButtonGroup();
    JCheckBox priority;
    String[] inputColumnNames = {"Thread Number", "Arrival Time", "Burst Time", "Priority"};
    String[] outputColumnNames = {"Thread Number", "Time Arrived", "Time Used"};
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
        beginButton.setPreferredSize(new Dimension(100, 50));
        beginButton.setFont(new Font("Arial", Font.PLAIN, 12));
        beginButton.addActionListener(this);
        randomProcessButton = new JButton("<html><center>Add Random<br>Process</center></html>");
        randomProcessButton.setPreferredSize(new Dimension(100, 50));
        randomProcessButton.setFont(new Font("Arial", Font.PLAIN, 12));
        randomProcessButton.addActionListener(this);
        oneStepButton = new JButton("Next Step");
        oneStepButton.setPreferredSize(new Dimension(100, 50));
        oneStepButton.setFont(new Font("Arial", Font.PLAIN, 12));
        oneStepButton.addActionListener(this);
        addProcessButton = new JButton("<html><center>Add New<br>Process</center></html>");
        addProcessButton.setPreferredSize(new Dimension(100, 50));
        addProcessButton.setFont(new Font("Arial", Font.PLAIN, 12));
        addProcessButton.addActionListener(this);
        removeProcessButton = new JButton("<html><center>Remove<br>Process</center></html>");
        removeProcessButton.setPreferredSize(new Dimension(100, 50));
        removeProcessButton.setFont(new Font("Arial", Font.PLAIN, 12));
        removeProcessButton.addActionListener(this);
        resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100, 50));
        resetButton.setFont(new Font("Arial", Font.PLAIN, 12));
        resetButton.addActionListener(this);
        
        inputTable = new JTable(inputData, inputColumnNames);
        inputScrollPane = new JScrollPane(inputTable, JScrollPane.VERTICAL_SCROLLBAR_NEVER , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollPane.setPreferredSize(new Dimension(640, 182));
        outputTable = new JTable(outputData, outputColumnNames);
        outputScrollPane = new JScrollPane(outputTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputScrollPane.setPreferredSize(new Dimension(640, 200));
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
        add(removeProcessButton);
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
    
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == resetButton)
        {
            inputData = new Object[0][3];
            updateTable(inputTable);
        }
        else if(e.getSource() == randomProcessButton)
        {
            if(inputData.length == 10)
            {
                JOptionPane.showMessageDialog(this, "Sorry, you can only have a maximum of 10 processes.", "Too many Processes", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Random rand = new Random();
                int num = inputData.length + 1;
                int burstTime = rand.nextInt(50) + 1;
                int arrivalTime = rand.nextInt(201);
                int priorityNum = 0;
                if(priority.isSelected())
                {
                    priorityNum = rand.nextInt(20);
                }
                Object[] process = {num, arrivalTime, burstTime, priorityNum};
                Object[][] inputDataTemp = new Object[inputData.length + 1][3];
                for(int i = 0; i < inputData.length; i++)
                {
                    inputDataTemp[i] = inputData[i];
                }
                inputDataTemp[inputDataTemp.length - 1] = process;
                inputData = inputDataTemp;
                updateTable(inputTable);
            }
        }
        else if(e.getSource() == removeProcessButton)
        {
            Object[] processNum = new Object[inputData.length];
            for(int i = 0; i < processNum.length; i++)
            {
                processNum[i] = i + 1;
            }
            int i = (int)(JOptionPane.showInputDialog(this, "Please specify the process number to delete.", "Delete Process", JOptionPane.QUESTION_MESSAGE, null, processNum, processNum[0]));
            Object[][] inputDataTemp = new Object[inputData.length - 1][3];
            for(int j = 0; j < inputDataTemp.length; j++)
            {
                System.out.println(j);
                if(j >= i - 1)
                {
                    inputDataTemp[j] = inputData[j + 1];
                    inputDataTemp[j][0] = (int)(inputDataTemp[j][0]) - 1;
                }
                else
                {
                    inputDataTemp[j] = inputData[j];
                }
            }
            inputData = inputDataTemp;
            updateTable(inputTable);
        }
        else if(e.getSource() == addProcessButton)
        {
            int num = inputData.length + 1;
            int burstTime = Integer.parseInt(JOptionPane.showInputDialog(this, "What is the burst time of this process?.", "Add Process", JOptionPane.QUESTION_MESSAGE));
            int arrivalTime = Integer.parseInt(JOptionPane.showInputDialog(this, "What is the arrival time of this process?.", "Add Process", JOptionPane.QUESTION_MESSAGE));
            int priorityNum = 0;
            if(priority.isSelected())
            {
                priorityNum = Integer.parseInt(JOptionPane.showInputDialog(this, "What is the arrival time of this process?.", "Add Process", JOptionPane.QUESTION_MESSAGE));
            }
            Object[] process = {num, arrivalTime, burstTime, priorityNum};
            Object[][] inputDataTemp = new Object[inputData.length + 1][3];
            for(int i = 0; i < inputData.length; i++)
            {
                inputDataTemp[i] = inputData[i];
            }
            inputDataTemp[inputDataTemp.length - 1] = process;
            inputData = inputDataTemp;
            updateTable(inputTable);
        }
        else
        {
            Object[] process = {8, 0.0, 8, 135};
            Object[][] inputDataTemp = new Object[inputData.length + 1][3];
            for(int i = 0; i < inputData.length; i++)
            {
                inputDataTemp[i] = inputData[i];
            }
            inputDataTemp[inputDataTemp.length - 1] = process;
            inputData = inputDataTemp;
            updateTable(inputTable);
        }
    }
    
    public void updateTable(JTable table)
    {
        Object[][] data;
        String[] names;
        if (table == inputTable)
        {
            data = inputData;
            names = inputColumnNames;
        }
        else
        {
            data = outputData;
            names = outputColumnNames;
        }
        inputTable.setModel(new AbstractTableModel() {
            public int getRowCount() {
                return data.length;
            }

            public int getColumnCount() {
                return names.length;
            }

            public String getColumnName(int column)
            {
                return names[column];
            }

            public Object getValueAt(int i, int i1) {
                return data[i][i1];
            }
        });
        ((AbstractTableModel)(table.getModel())).fireTableDataChanged();
    }
}
