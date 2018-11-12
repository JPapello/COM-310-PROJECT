package cpu;

// @Author Jude Andre II


import cpu.CPUScheduler;
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
    
    //Java Components
    JTable inputTable, outputTable;
    JScrollPane inputScrollPane, outputScrollPane;
    JButton runButton, randomProcessButton, addProcessButton, removeProcessButton, resetButton;
    JLabel inputTableLabel, outputTableLabel, averageWaitTime, averageTurnaroundTime;
    JRadioButton firstComeFirstServed, shortestJobFirst;
    ButtonGroup methods = new ButtonGroup();
    
    //Table Data
    String[] inputColumnNames = {"Thread Number", "Arrival Time", "Burst Time"};
    String[] outputColumnNames = {"Thread Number", "Wait Time", "Turnaround Time"};
    Object[][] inputData = {};
    Object[][] outputData = {};
    
    //CPU Simulator
    CPUScheduler cpu = new CPUScheduler();
    
    public CPUPanel()
    {
        
        //Initializes radio buttons
        firstComeFirstServed = new JRadioButton("First Come, First Served");  //First Come First Served 
        firstComeFirstServed.setPreferredSize(new Dimension(300, 20));
        methods.add(firstComeFirstServed);
        shortestJobFirst = new JRadioButton("Shortest Job First"); //Shortest Job First
        shortestJobFirst.setPreferredSize(new Dimension(300, 20));
        methods.add(shortestJobFirst);
        firstComeFirstServed.setSelected(true);
        
        //Initializes Buttons
        runButton = new JButton("Run");
        runButton.setPreferredSize(new Dimension(100, 50));
        runButton.setFont(new Font("Arial", Font.PLAIN, 12));
        runButton.addActionListener(this);
        randomProcessButton = new JButton("<html><center>Add Random<br>Process</center></html>");
        randomProcessButton.setPreferredSize(new Dimension(100, 50));
        randomProcessButton.setFont(new Font("Arial", Font.PLAIN, 12));
        randomProcessButton.addActionListener(this);
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
        
        add(runButton);
        add(randomProcessButton);
        add(addProcessButton);
        add(removeProcessButton);
        add(resetButton);
        add(firstComeFirstServed);
        add(shortestJobFirst);
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
            cpu.reset();
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
                cpu.addProcess(new Process(inputData.length + 1, rand.nextInt(50) + 1, rand.nextInt(201)));
                Process p = cpu.getProcessWithNumber(cpu.getNumberOfProcesses() - 1).deepCopy();
                Object[] process = {p.getID(), p.getArrivalTime(), p.getBurstTime()};
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
            cpu.deleteProcessWithNumber(i);
            Object[][] inputDataTemp = new Object[inputData.length - 1][3];
            for(int j = 0; j < inputDataTemp.length; j++)
            {
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
            if(inputData.length == 10)
            {
                JOptionPane.showMessageDialog(this, "Sorry, you can only have a maximum of 10 processes.", "Too many Processes", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                cpu.addProcess();
                Process p = cpu.getProcessWithNumber(cpu.getNumberOfProcesses() - 1).deepCopy();
                Object[] process = {p.getID(), p.getArrivalTime(), p.getBurstTime()};
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
        else if(e.getSource() == runButton)
        {
            if(firstComeFirstServed.isSelected())
            {
                cpu.FCFS();
            }
            else if(shortestJobFirst.isSelected())
            {
                cpu.SJF();
            }
            
            int[] order = cpu.getOutputOrder();
            Object[][] outputDataTemp = new Object[order.length][3];
            for(int i = 0; i < order.length; i++)
            {
                outputDataTemp[i][0] = cpu.getProcessWithNumber(order[i]).getID();
                outputDataTemp[i][1] = cpu.getWaitTimeForProcess(order[i]);
                outputDataTemp[i][2] = cpu.getTurnaroundTimeForProcess(order[i]);
            }
            outputData = outputDataTemp;
            updateTable(outputTable);
            averageWaitTime.setText("Average Wait Time: " + cpu.getAverageWaitTime());
            averageTurnaroundTime.setText("Average Turnaround Time: " + cpu.getAverageTurnaroundTime());
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
        table.setModel(new AbstractTableModel() {
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
