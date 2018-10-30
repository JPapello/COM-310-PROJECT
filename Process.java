package cpu.simulator;

import javax.swing.JOptionPane;

/**
 * @author Jude Andre II
 */

public class Process {
    private int idNum;
    private int burstTime;
    private int arrivalTime;
    
    public Process()
    {
        this.idNum = 0;
        this.burstTime = 0;
        this.arrivalTime = 0;
    }
    
    public Process(int idNum, int burstTime, int arrivalTime)
    {
        this.idNum = idNum;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }
    
    public void input()
    {
        int newID = Integer.parseInt(JOptionPane.showInputDialog("Please insert the ID number of the process. \n(If there already exists an ID number and you don't want to change it, input '-1')"));
        if(newID != -1)
        {
            setID(newID);
        }
        int newBurstTime = Integer.parseInt(JOptionPane.showInputDialog("Please insert the burst time of the process. \n(If there already exists a burst time and you don't want to change it, input '-1')"));
        if(newBurstTime != -1)
        {
            setBurstTime(newBurstTime);
        }
        int newArrivalTime = Integer.parseInt(JOptionPane.showInputDialog("Please insert the arrival time of the process. \n(If there already exists an arrival time and you don't want to change it, input '-1')"));
        if(newArrivalTime != -1)
        {
            setArrivalTime(newArrivalTime);
        }
        System.out.println(getID() + " " + getBurstTime() + " " + getArrivalTime());
    }
    
    public Process deepCopy()
    {
        return new Process(idNum, burstTime, arrivalTime);
    }
    
    public void setID(int idNum)
    {
        this.idNum = idNum;
    }
    
    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }
    
    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }
    
    public int getID()
    {
        return this.idNum;
    }
    
    public int getBurstTime()
    {
        return this.burstTime;
    }
    
    public int getArrivalTime()
    {
        return this.arrivalTime;
    }
}
