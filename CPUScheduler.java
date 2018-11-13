package cpu;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class CPUScheduler
{
    private final int MAX_NUMBER_OF_PROCESSES = 10;
    private Process[] processes; //An array containing the individual processes in the ready queue. Each process has an ID Number, a Burst Time, and an Arrival Time. 
    private int[] processWaitTimes, processTurnaroundTimes, processOutputNumber; //The individual arival times of each process. This array will be parallel to the array of processes. 
    private int numberOfProcesses; //The number of processes initially in the ready queue. 
    private double averageWaitTime, averageTurnaroundTime; //The average wait time and the average turnaround time, respectively. 
    
    public CPUScheduler()
    {
        this.numberOfProcesses = 0;
        this.processes = new Process[10];
        this.processTurnaroundTimes = new int[numberOfProcesses];
        this.processWaitTimes = new int[numberOfProcesses];
    }
    
    //CPU SCHEDULING ALGORITHS GO HERE!
    
     //This method invokes the First-Come-First-Served (FCFS) CPU Scheduling Algorithm, in which the processes are ordered by shortest arrival time and "executed" accordingly.
    public void FCFS()
    {
        resetTimes();
        int[] order = new int[numberOfProcesses]; //This array specifies the order of processes to execute. 
        Process[] remainingProcesses = new Process[numberOfProcesses]; //This array will hold the processes which has yet to be observed; a null value in a given positon means that the process in that position has already been used. This array will be parallel to the original list of processes. 
        System.arraycopy(processes, 0, remainingProcesses, 0, remainingProcesses.length); //Initially, every process can be observed, and so the original array of process is copied into the array fo remaining processes. 
        
        //Now, the order of processes to execute is determined. In this case, the processes are sorted by the shortest arrival times. 
        int minimum; //The minimum value. 
        int minIndex; //The index where the minimum value was achieved. 
        for (int i = 0; i < numberOfProcesses; i++)
        {
            //First, the minimum from the remaining processes is found. 
            minimum = Integer.MAX_VALUE; //Initially, the minimum value is set to the lagest value that an integer can be. 
            minIndex = -1; //Initially, the index at which the minimum value was achieved is set to -1. 
            for (int j = 0; j < numberOfProcesses; j++)
            {
                if (remainingProcesses[j] != null && remainingProcesses[j].getArrivalTime() < minimum) //If the current process is not null and its arrival time is less than the smallest arrival time found so far...
                {
                    minimum = processes[j].getArrivalTime(); //The minimum value is set to the arrival time of the process currently being observed. 
                    minIndex = j; //The index of the array of processes at which the minimum arrival time has been located is set to the value of the inner loop index. 
                }
            }
            
            remainingProcesses[minIndex] = null; //Then, the process which has the shortest burst time among the remaining processes is "eliminated" by setting the contents of the array at the minIndex to null. 
            order[i] = minIndex; //The next process to execute will be the one with the shortest burst time among the remaining processes. 
            
        }
        //Now, the wait and turnaround times for each process is identified. 
        processWaitTimes[order[0]] = processes[order[0]].getArrivalTime(); //The waiting time of the first process in the specified order is equal to the arrival time of the first process in that order. 
        processTurnaroundTimes[order[0]] = processes[order[0]].getArrivalTime() + processes[order[0]].getBurstTime(); //Given the first process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
        
        //This for loop will calculate and store the wait times and turnaround times for the other processes. 
        for (int i = 1; i < numberOfProcesses; i++) 
        {
            if (processes[order[i]].getArrivalTime() < processes[order[i-1]].getBurstTime() + processes[order[i-1]].getArrivalTime()) //If the arrival time of the current process is equal to the sum between the burst and arrival times of the previous process...
            {
                processWaitTimes[order[i]] = processTurnaroundTimes[order[i-1]]; //Since the algorithm is non-preemptive, the current process gets executed immediately after the previous one finishes, and so the waiting time of the current process is equal to the turnaround time of the process that came before it. 
                processTurnaroundTimes[order[i]] = processWaitTimes[order[i]] + processes[order[i]].getBurstTime(); //The turnaround time of the current process is equal to its wait time plus its burst time. 
            }
            else //Otherwise...
            {
                processWaitTimes[order[i]] = processes[order[i]].getArrivalTime(); //The waiting time of the current process in the specified order is equal to the arrival time of the first process in that order. 
                processTurnaroundTimes[order[i]] = processes[order[i]].getArrivalTime() + processes[order[i]].getBurstTime(); //Given the current process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
            }
        }
        
        //Finally, the average wait time and the average turnaround time are computed. 
        for (int i = 0; i < numberOfProcesses; i++)
        {
            averageWaitTime += processWaitTimes[i];
            averageTurnaroundTime += processTurnaroundTimes[i]; 
        }        
        averageWaitTime = averageWaitTime / numberOfProcesses; //The average waiting time is computed by dividing the sum by the number of processes in the ready queue. 
        averageTurnaroundTime = averageTurnaroundTime / numberOfProcesses; //The average turnaround time is computed by dividing the sum by the number of processes in the ready queue. 
        processOutputNumber = order;
        
        //System.out.println("Avg. Wait Time: " + averageWaitTime + " time units. "); //Output the average waiting time. 
        //System.out.println("Avg. Turnaround Time: " + averageTurnaroundTime + " time units. "); //Output the average turnaround time. 
        //System.out.println(""); //USED AS A SPACE
    }
    
    //This method invokes the Shortest-Job-First (SJF) CPU Scheduling Algorithm, in which the processes are ordered by shortest burst time and "executed" accordingly. Note that this algorithm is non-preempive. 
    public void SJF()
    {
        resetTimes();
        int[] order = new int[numberOfProcesses]; //This array specifies the order of processes to execute. 
        Process[] remainingProcesses = new Process[numberOfProcesses]; //This array will hold the processes which has yet to be observed; a null value in a given positon means that the process in that position has already been used. This array will be parallel to the original list of processes. 
        System.arraycopy(processes, 0, remainingProcesses, 0, remainingProcesses.length); //Initially, every process can be observed, and so the original array of process is copied into the array fo remaining processes. 
        
        //Now, the order of processes to execute is determined. In this case, the processes are sorted by the shortest burst times. 
        int minimum; //The minimum burst time. 
        int minIndex; //The index where the minimum value was achieved. 
        for (int i = 0; i < numberOfProcesses; i++)
        {
            //First, the minimum from the remaining processes is found. 
            minimum = Integer.MAX_VALUE; //Initially, the minimum value is set to the lagest value that an integer can be. 
            minIndex = -1; //Initially, the index at which the minimum value was achieved is set to -1. 
            for (int j = 0; j < remainingProcesses.length; j++)
            {
                if (remainingProcesses[j] != null && remainingProcesses[j].getBurstTime() < minimum) //If the current process is not null and its burst time is less than the smallest burst time found so far...
                {
                    minimum = processes[j].getBurstTime(); //The minimum value is set to the burst time of the process currently being observed. 
                    minIndex = j; //The index of the array of processes at which the minimum burst time has been located is set to the value of the inner loop index. 
                }
            }
            
            remainingProcesses[minIndex] = null; //Then, the process which has the shortest burst time from the remaining processes is "eliminated" by setting the contents of the array at the minIndex to null. 
            
            order[i] = minIndex; //The next process to execute will be the one with the shortest burst time among the remaining processes. 
            
        }
        
        //Now, the wait and turnaround times for each process is identified. 
        processWaitTimes[order[0]] = processes[order[0]].getArrivalTime(); //The waiting time of the first process in the specified order is equal to the arrival time of the first process in that order. 
        processTurnaroundTimes[order[0]] = processes[order[0]].getArrivalTime() + processes[order[0]].getBurstTime(); //Given the first process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
        
        //This for loop will calculate and store the wait times and turnaround times for the other processes. 
        for (int i = 1; i < numberOfProcesses; i++) 
        {
            if (processes[order[i]].getArrivalTime() < processes[order[i-1]].getBurstTime() + processes[order[i-1]].getArrivalTime()) //If the arrival time of the current process is equal to the sum between the burst and arrival times of the previous process...
            {
                processWaitTimes[order[i]] = processTurnaroundTimes[order[i-1]]; //Since the algorithm is non-preemptive, the current process gets executed immediately after the previous one finishes, and so the waiting time of the current process is equal to the turnaround time of the process that came before it. 
                processTurnaroundTimes[order[i]] = processWaitTimes[order[i]] + processes[order[i]].getBurstTime(); //The turnaround time of the current process is equal to its wait time plus its burst time. 
            }
            else //Otherwise...
            {
                processWaitTimes[order[i]] = processes[order[i]].getArrivalTime(); //The waiting time of the current process in the specified order is equal to the arrival time of the first process in that order. 
                processTurnaroundTimes[order[i]] = processes[order[i]].getArrivalTime() + processes[order[i]].getBurstTime(); //Given the current process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
            }
        }
        
        //Finally, the average wait time and the average turnaround time are computed. 
        for (int i = 0; i < numberOfProcesses; i++)
        {
            averageWaitTime += processWaitTimes[i];
            averageTurnaroundTime += processTurnaroundTimes[i]; 
        }        
        averageWaitTime = averageWaitTime / numberOfProcesses; //The average waiting time is computed by dividing the sum by the number of processes in the ready queue. 
        averageTurnaroundTime = averageTurnaroundTime / numberOfProcesses; //The average turnaround time is computed by dividing the sum by the number of processes in the ready queue. 
        this.processOutputNumber = order;
        //System.out.println("Avg. Wait Time: " + averageWaitTime + " time units. "); //Output the average waiting time. 
        //System.out.println("Avg. Turnaround Time: " + averageTurnaroundTime + " time units. "); //Output the average turnaround time. 
        //System.out.println(""); //USED AS A SPACE
    }
    
    public void PSJF()
    {
        resetTimes();
        ArrayList<Integer> order = new ArrayList<>();
        Process[] processTemp = processes.clone();
        
    }
    
    /*
    Anything afterwards was added by Jude
    */

    public void addProcess()
    {
        Process p = new Process(numberOfProcesses + 1);
        p.input();
        if(p.getBurstTime() > 0)
        {
            this.processes[numberOfProcesses] = p;
            this.numberOfProcesses++;
            updateArrays();
        }
        else
        {
            return;
        }
    }
    
    public void addProcess(Process p)
    {
        this.processes[numberOfProcesses] = p.deepCopy();
        this.numberOfProcesses++;
        updateArrays();
    }
    
    public Process getProcessWithNumber(int index)
    {
        return this.processes[index];
    }
    
    public void deleteProcessWithNumber(int index)
    {
        for(int i = index; i < numberOfProcesses - 1; i++)
        {
            this.processes[i] = this.processes[i - 1];
        }
        this.numberOfProcesses--;
        updateArrays();
    }
    
    public void reset()
    {
        this.numberOfProcesses = 0;
        updateArrays();
    }
    
    public int getNumberOfProcesses()
    {
        return this.numberOfProcesses;
    }
    
    public int[] getOutputOrder()
    {
        return this.processOutputNumber.clone();
    }
    
    public int getWaitTimeForProcess(int index)
    {
        return processWaitTimes[index];
    }
    
    public int getTurnaroundTimeForProcess(int index)
    {
        return processTurnaroundTimes[index];
    }
    
    public void updateArrays()
    {
        this.processTurnaroundTimes = new int[numberOfProcesses];
        this.processWaitTimes = new int[numberOfProcesses];
    }
    
    public double getAverageWaitTime()
    {
        return this.averageWaitTime;
    }
    
    public double getAverageTurnaroundTime()
    {
        return this.averageTurnaroundTime;
    }
    
    public void resetTimes()
    {
        this.averageTurnaroundTime = 0;
        this.averageWaitTime = 0;
    }
}
