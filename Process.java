

import javax.swing.JOptionPane;

/**
 * @author Jude Andre II
 */

public class Process {
    private int idNum;
    private int burstTime;
    private int arrivalTime;
    private int priority;
    private boolean priorityIncluded;
    
    public Process()
    {
        this.idNum = 0;
        this.burstTime = 0;
        this.arrivalTime = 0;
        this.priority = 0;
        this.priorityIncluded = false;
    }
    
    public Process(int idNum)
    {
        this.idNum = idNum;
        this.burstTime = 0;
        this.arrivalTime = 0;
        this.priority = 0;
        this.priorityIncluded = false;
    }
    
    public Process(int idNum, int burstTime, int arrivalTime, int priority, boolean priorityIncluded)
    {
        this.idNum = idNum;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.priorityIncluded = priorityIncluded;
    }
    
    public void input()
    {
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
        Object[] trueOrFalse = {"Yes", "No"};
        String newPriorityIncluded = (String)(JOptionPane.showInputDialog(null, "Will priority be included in this process?", "Add Priority", JOptionPane.QUESTION_MESSAGE, null, trueOrFalse, trueOrFalse[0]));
        if(newPriorityIncluded.equals("Yes"))
        {
            setPriorityInclude(true);
        }
        else
        {
            setPriorityInclude(false);
        }
        
        if(priorityIncluded)
        {
            int newPriority = Integer.parseInt(JOptionPane.showInputDialog("Please insert the priority of the process. \n(If there already exists an ID number and you don't want to change it, input '-1')"));
            if(newPriority != -1)
            {
                setPriority(newPriority);
            }
        }
        
        System.out.println(getID() + " " + getBurstTime() + " " + getArrivalTime());
    }
    
    public Process deepCopy()
    {
        return new Process(idNum, burstTime, arrivalTime, priority, priorityIncluded);
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
    
    public void setPriority(int priority)
    {
        this.priority = priority;
    }
    
    public void setPriorityInclude(boolean priorityIncluded)
    {
        this.priorityIncluded = priorityIncluded;
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
    
    public int getPriority()
    {
        return this.priority;
    }
    
    public boolean getPriorityIncluded()
    {
        return this.priorityIncluded;
    }
    
    public String toString()
    {
        String s = "";
        s += "Thread # " + this.getID() + "\n";
        s += "Arrival Time: " + getArrivalTime() + "\n";
        s += "Burst Time: " + getBurstTime() + "\n";
        s += "Priority: " + getPriority() + "\n";
        return s;
    }
    
    public void show()
    {
        System.out.println(toString());
    }
}
