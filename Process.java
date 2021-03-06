package cpu;



import javax.swing.JOptionPane;

/**
 * @author Jude Andre II
 */

public class Process implements Comparable{
    private int idNum;
    private int burstTime;
    private int arrivalTime;
    
    public Process()
    {
        this.idNum = 0;
        this.burstTime = 0;
        this.arrivalTime = 0;
    }
    
    public Process(int idNum)
    {
        this.idNum = idNum;
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
        try
        {
            int newArrivalTime = Integer.parseInt(JOptionPane.showInputDialog("Please insert the arrival time of the process. \n(If there already exists an arrival time and you don't want to change it, input '-1')"));
            if(newArrivalTime != -1)
            {
                setArrivalTime(newArrivalTime);
            }
            int newBurstTime = Integer.parseInt(JOptionPane.showInputDialog("Please insert the burst time of the process. \n(If there already exists a burst time and you don't want to change it, input '-1')"));
            if(newBurstTime != -1)
            {
                setBurstTime(newBurstTime);
            }
        }
        catch(NumberFormatException e)
        {
            System.out.println("Error, empty input.");
        }
        
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
    
    public String toString()
    {
        String s = "";
        s += "Thread # " + this.getID() + "\n";
        s += "Arrival Time: " + getArrivalTime() + "\n";
        s += "Burst Time: " + getBurstTime() + "\n";
        return s;
    }
    
    public void show()
    {
        System.out.println(toString());
    }

    @Override
    public int compareTo(Object t) {
        return Integer.compare(this.arrivalTime, ((Process)(t)).getArrivalTime());
    }
}
