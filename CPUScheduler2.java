//Author: Joshua Papello

import javax.swing.JOptionPane;

public class CPUScheduer
{
    static Process[] processes; //An array containing the individual processes in the ready queue. Each process has an ID Number, a Burst Time, and an Arrival Time. 
    static int[] processBurstTimes, processArrivalTimes, processWaitTimes, processTurnaroundTimes; //The individual burst times, arrival times, wait times, and turnaround times of each process, respectively. Each of these arrays will be parallel to the array of processes. 
    static int numberOfProcesses; //The number of processes initially in the ready queue. 
    static double averageWaitTime, averageTurnaroundTime; //The average wait time and the average turnaround time, respectively. 
    
    public static void main(String[] args)
    {
        int choice; //This variable is an indicator as to what opertion to perform. 
        int inputManually; //This variable is a flag which states whether or not the user wants to initialize each of the processes manually or randomly. 
        
        do
        {
            choice = -1; //Resetting the indicator each time...!
            while(choice < 0 || choice > 3 /*choice > 4*/) //While the choice is out of bounds... 
            {
                try
                {
                    //choice = Integer.parseInt(JOptionPane.showInputDialog("Select an option from the following menu:\nEnter 1 to execute FCFS\nEnter 2 to execute SJF\nEnter 3 to execute Priority\nEnter 4 to exit")); //The user is shown a list of choices and is prompted to select among them to continue by entering the approporiate number. 
                    choice = Integer.parseInt(JOptionPane.showInputDialog("Select an option from the following menu:\nEnter 1 to execute FCFS\nEnter 2 to execute SJF\nEnter 3 to exit")); //The user is shown a list of choices and is prompted to select among them to continue by entering the approporiate number. 
                }
                catch (NumberFormatException e) //When the user does not input anything... 
                {
                    JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the operation to perform.\nPlease choose a valid input.", "WARNING: INVALID INPUT DETECTED!", 2); //A message is shown to the user stating that an invalid input was entered.
                    choice = -1; //The choice is set to -1 so that the loop can execute again. 
                }
            }
            
            if (choice != 3 /*choice != 4*/)
            {
                //If the user has not decided to exit the program, then the number of processes initially in the ready queue is specified by the user. 
                try
                {
                    do
                    {
                        numberOfProcesses = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of processes initially in the ready queue.\nThere can be up to 10 processes in the queue at one time.")); //The user is prompted to input the number of processes initially in the ready queue.
                        if (numberOfProcesses < 1)
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: The number of processes you have input is less than 1. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that invalid input was entered.
                        }
                        else if (numberOfProcesses > 10)
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: Up to 10 processes can initially be placed on the ready queue. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that invalid input was entered.
                        }
                    }
                    while(numberOfProcesses < 1 || numberOfProcesses > 10); //Continue to ask for input until proper input has been entered. 
                }
                catch(NumberFormatException e) //When the user does not input anything... 
                {
                    JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the initial number of processes in the ready queue. \nPlease input a value between 1 and 5.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                    numberOfProcesses = 0; //The initial number of processes is set to 0 so that the loop can execute again. 
                }

                inputManually = JOptionPane.showConfirmDialog(null, "Do you want to initialize each process manually?\n(YES = MANUAL INPUT; NO = RANDOMIZED INPUT)", "How do you want to input each process?", JOptionPane.YES_NO_OPTION); //The user is asked whether it wants to initialize the processes manually or randomly. 
                algorithmInitialization(inputManually); //Regardless of which algorithm is chosen to be executed by the user or if whether the user decides to input the processes manually, some preliminary initializations are required, which is what this method provides.  
            }
            
            switch (choice) //There are different cases based off of what the user decide to do. 
            {
                case 1:
                {
                    FCFS(processes); //The First-Come-First-Served scheduling algorithm is invoked. 
                    break;
                }
                case 2:
                {
                    SJF(processes); //The (non-preemptive) Shortest-Job-First scheduling algorithm is invoked. 
                    break;
                }
                /*
                case 3:
                {
                    Priority(processes); //The (non-preemptive) Priority scheduling algorithm is invoked. 
                    break;
                }
                */
            }
        }
        while(choice != 3 /*choice != 4*/); //Continue to prompt the user as long as the user does not choose to exit the program. 
        
    }
    
    //This method will initialize each of the processes either manually (via user input) or randomly, depending on what the user decides to do. 
    private static void algorithmInitialization(int inputManually)
    {
        processes = new Process[numberOfProcesses]; //The length of the array of processes is created as a new array of process objects whose length is the number of processes. 
        processBurstTimes = new int[numberOfProcesses]; //The array of burst times is created as an array of integers with a length equal to the number of processes. 
        processArrivalTimes = new int[numberOfProcesses]; //The array of arrival times is created as an array of integers with a length equal to the number of processes. 
        processWaitTimes = new int[numberOfProcesses]; //The array which holds the wait times of each process is made as an array of integers with a length equal to the number of processes. 
        processTurnaroundTimes = new int[numberOfProcesses]; //The array which holds the turnaround times of each process is made as an array of integers with a length equal to the number of processes. 
        if (inputManually == 0) //If the user decides to input each of the processes manually...
        {
            int burstTime; //This variable is a placeholder for initializing the burst time of the process. 
            int arrivalTime; //This variable is a placeholder for initializing the arrival time of the process. 
            
            //This for loop will manually initialize each of the process via user input. 
            for (int i = 0; i < numberOfProcesses; i++)
            {
                //First, the ID Number for the process being created is simply the index of the for loop. 
                
                //Second, the user inputs the burst time of the process. 
                do
                {
                    try
                    {
                        burstTime = Integer.parseInt(JOptionPane.showInputDialog("Enter the CPU burst time of process #"+(i+1)+" (of "+numberOfProcesses+").\nThis can be an integer between 1 and 50.")); //The user is prompted to input a burst time for the process, which is held by the placeholder. 
                        if (burstTime < 1 || burstTime > 50) //If the user input for the burst time is not within the accepted range of values...
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: This input for the burst time for this process is invalid.\nPlease input an integer between 1 and 50.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                        }
                    }
                    catch(NumberFormatException e) //When the user does not input anything or if the input cannot be made into an integer value...
                    {
                        JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the burst time of this proces.\nPlease input an integer between 1 and 50.", "WARNING: NO INPUT DETECTED!", JOptionPane.WARNING_MESSAGE); //A message is shown to the user stating that no input was entered.
                        burstTime = 0; //The burst time of the process is set to 0 so that the loop can execute again. 
                    }
                }
                while(burstTime < 1 || burstTime > 50); //Continue to ask for a value until a proper input has been entered!
                processBurstTimes[i] = burstTime; //The burst time of the process is assigned to the value of its placholder. 

                //Lastly, the user inputs the time that the process arrives into the CPU. 
                do
                {
                    try
                    {
                        arrivalTime = Integer.parseInt(JOptionPane.showInputDialog("Enter the time that process #"+(i+1)+" (of "+numberOfProcesses+") arrives in the CPU.\nThis can be a number between 0 and 200.")); //The user is prompted to input a name for the process, which is held by the placeholder. 
                        if (arrivalTime < 0 || arrivalTime > 200) //If the user input for the arrival time is not within the accepted range of values...
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: This input for the arrival time for this process is invalid.\nPlease input an integer between 0 and 200.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                        }
                    }
                    catch (NumberFormatException e) //When the user does not input anything or if the input cannot be made into an integer value...
                    {
                        JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the arrival time of this process.\nPlease input an integer between 0 and 200.", "WARNING: NO INPUT DETECTED!", JOptionPane.WARNING_MESSAGE); //A message is shown to the user stating that no input was entered.
                        arrivalTime = -1; //The arrival time of the process is set to 0 so that the loop can execute again. 
                    }
                }
                while(arrivalTime < 0 || arrivalTime > 200); //Continue to ask for a value until a proper input has been entered!
                processArrivalTimes[i] = arrivalTime; //The arrival time of the process is assigned to the value of its placeholder. 
                
                processes[i] = new Process(i+1, processBurstTimes[i], processArrivalTimes[i]); //With each of these specifications, a new process object is created and placed into the array of processes. 
            }
        }
        else //Otherwise, if the user decides to randomly generate each of the processes...
        {
            //This for loop will initialize each of the processes to be used with random burst times and arrival times. 
            for (int i = 0; i < numberOfProcesses; i++)
            {
                //First, the ID Number for the process being created is simply the index of the for loop. 
                processBurstTimes[i] = (int)((Math.random()*50)+1); //Second, the burst time of the process is set to a random number between 1 time unit and 50 time units, which is consistent with our assumptions. 
                processArrivalTimes[i] = (int)((Math.random()*200)+0); //Lastly, the arrival time of the process is set to a random number between 0 time units and 200 time units, which is consistent with our assumptions. 
                processes[i] = new Process(i+1, processBurstTimes[i], processArrivalTimes[i]); //With each of these specifications, a new process object is created and placed into the array of processes. 
            }
        }
        
        
        
    }
    
    //This method invokes the First-Come-First-Served (FCFS) CPU Scheduling Algorithm, in which the processes are ordered by shortest arrival time and "executed" accordingly.
    public static void FCFS(Process[] processes)
    {
        int[] order = new int[processes.length]; //This array specifies the order of processes to execute. 
        Process[] remainingProcesses = new Process[processes.length]; //This array will hold the processes which has yet to be observed; a null value in a given positon means that the process in that position has already been used. This array will be parallel to the original list of processes. 
        System.arraycopy(processes, 0, remainingProcesses, 0, remainingProcesses.length); //Initially, every process can be observed, and so the original array of process is copied into the array fo remaining processes. 
        
        //Now, the order of processes to execute is determined. In this case, the processes are sorted by the shortest arrival times. 
        int minimum; //The minimum value. 
        int minIndex; //The index where the minimum value was achieved. 
        for (int i = 0; i < processes.length; i++)
        {
            //First, the minimum from the remaining processes is found. 
            minimum = Integer.MAX_VALUE; //Initially, the minimum value is set to the lagest value that an integer can be. 
            minIndex = -1; //Initially, the index at which the minimum value was achieved is set to -1. 
            for (int j = 0; j < remainingProcesses.length; j++)
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
        processWaitTimes[order[0]] = processArrivalTimes[order[0]]; //The waiting time of the first process in the specified order is equal to the arrival time of the first process in that order. 
        processTurnaroundTimes[order[0]] = processArrivalTimes[order[0]] + processBurstTimes[order[0]]; //Given the first process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
        
        //This for loop will calculate and store the wait times and turnaround times for the other processes. 
        for (int i = 1; i < processes.length; i++) 
        {
            if (processes[order[i]].getArrivalTime() < processes[order[i-1]].getBurstTime() + processes[order[i-1]].getArrivalTime()) //If the arrival time of the current process is equal to the sum between the burst and arrival times of the previous process...
            {
                processWaitTimes[order[i]] = processTurnaroundTimes[order[i-1]]; //Since the algorithm is non-preemptive, the current process gets executed immediately after the previous one finishes, and so the waiting time of the current process is equal to the turnaround time of the process that came before it. 
                processTurnaroundTimes[order[i]] = processWaitTimes[order[i]] + processBurstTimes[order[i]]; //The turnaround time of the current process is equal to its wait time plus its burst time. 
            }
            else //Otherwise...
            {
                processWaitTimes[order[i]] = processArrivalTimes[order[i]]; //The waiting time of the current process in the specified order is equal to the arrival time of the first process in that order. 
                processTurnaroundTimes[order[i]] = processArrivalTimes[order[i]] + processBurstTimes[order[i]]; //Given the current process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
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
        
        //System.out.println("Avg. Wait Time: " + averageWaitTime + " time units. "); //Output the average waiting time. 
        //System.out.println("Avg. Turnaround Time: " + averageTurnaroundTime + " time units. "); //Output the average turnaround time. 
        //System.out.println(""); //USED AS A SPACE
    }
    
    //This method invokes the Shortest-Job-First (SJF) CPU Scheduling Algorithm, in which the processes are ordered by shortest burst time and "executed" accordingly. Note that this algorithm is non-preempive. 
    public static void SJF(Process[] processes)
    {
        int[] order = new int[processes.length]; //This array specifies the order of processes to execute. 
        Process[] remainingProcesses = new Process[processes.length]; //This array will hold the processes which has yet to be observed; a null value in a given positon means that the process in that position has already been used. This array will be parallel to the original list of processes. 
        System.arraycopy(processes, 0, remainingProcesses, 0, remainingProcesses.length); //Initially, every process can be observed, and so the original array of process is copied into the array fo remaining processes. 
        
        //Now, the order of processes to execute is determined. In this case, the processes are sorted by the shortest burst times. 
        int minimum; //The minimum burst time. 
        int minIndex; //The index where the minimum value was achieved. 
        for (int i = 0; i < processes.length; i++)
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
        processWaitTimes[order[0]] = processArrivalTimes[order[0]]; //The waiting time of the first process in the specified order is equal to the arrival time of the first process in that order. 
        processTurnaroundTimes[order[0]] = processArrivalTimes[order[0]] + processBurstTimes[order[0]]; //Given the first process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
        
        //This for loop will calculate and store the wait times and turnaround times for the other processes. 
        for (int i = 1; i < processes.length; i++) 
        {
            if (processes[order[i]].getArrivalTime() < processes[order[i-1]].getBurstTime() + processes[order[i-1]].getArrivalTime()) //If the arrival time of the current process is equal to the sum between the burst and arrival times of the previous process...
            {
                processWaitTimes[order[i]] = processTurnaroundTimes[order[i-1]]; //Since the algorithm is non-preemptive, the current process gets executed immediately after the previous one finishes, and so the waiting time of the current process is equal to the turnaround time of the process that came before it. 
                processTurnaroundTimes[order[i]] = processWaitTimes[order[i]] + processBurstTimes[order[i]]; //The turnaround time of the current process is equal to its wait time plus its burst time. 
            }
            else //Otherwise...
            {
                processWaitTimes[order[i]] = processArrivalTimes[order[i]]; //The waiting time of the current process in the specified order is equal to the arrival time of the first process in that order. 
                processTurnaroundTimes[order[i]] = processArrivalTimes[order[i]] + processBurstTimes[order[i]]; //Given the current process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
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
        
        //System.out.println("Avg. Wait Time: " + averageWaitTime + " time units. "); //Output the average waiting time. 
        //System.out.println("Avg. Turnaround Time: " + averageTurnaroundTime + " time units. "); //Output the average turnaround time. 
        //System.out.println(""); //USED AS A SPACE
    }
    
    //This method invokes the Priority CPU Scheduling Algorithm, in which the processes are ordered by largest priority number and "executed" accordingly. Note that this algorithm is non-preemptive. 
    /*
    public static void Priority(Process[] processes)
    {
        int[] order = new int[processes.length]; //This array specifies the order of processes to execute. 
        Process[] remainingProcesses = new Process[processes.length]; //This array will hold the processes which has yet to be observed; a null value in a given positon means that the process in that position has already been used. This array will be parallel to the original list of processes. 
        System.arraycopy(processes, 0, remainingProcesses, 0, remainingProcesses.length); //Initially, every process can be observed, and so the original array of process is copied into the array fo remaining processes. 
        
        //Now, the order of processes to execute is determined. In this case, a larger priority number for any given process entails that the process has a higher priority and should therefore be executed first. 
        int maximum; //The maximum value. 
        int maxIndex; //The index where the maximum value was achieved. 
        for (int i = 0; i < processes.length; i++)
        {
            //First, the maximum value from the remaining processes is found. 
            maximum = Integer.MIN_VALUE; //Initially, the maximum value is set to the smallest value that an integer can be. 
            maxIndex = -1; //Initially, the index at which the maximum value was achieved is set to -1. 
            for (int j = 0; j < remainingProcesses.length; j++)
            {
                if (remainingProcesses[j] != null && remainingProcesses[j].getArrivalTime() > maximum) //If the current process is not null and its priority number is less than the largest priority number found so far...
                {
                    maximum = processes[j].getArrivalTime(); //The minimum value is set to the arrival time of the process currently being observed. 
                    maxIndex = j; //The index of the array of processes at which the minimum arrival time has been located is set to the value of the inner loop index. 
                }
            }
            
            remainingProcesses[maxIndex] = null; //Then, the process which has the largest priority number from the remaining processes is "eliminated" by setting the contents of the array at the maxIndex to null. 
            
            order[i] = maxIndex; //The next process to execute will be the one with the largest priority among the remaining processes. 
            
        }
        
        //Now, the wait and turnaround times for each process is identified. 
        processWaitTimes[order[0]] = processArrivalTimes[order[0]]; //The waiting time of the first process in the specified order is equal to the arrival time of the first process in that order. 
        processTurnaroundTimes[order[0]] = processArrivalTimes[order[0]] + processBurstTimes[order[0]]; //Given the first process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
        
        //This for loop will calculate and store the wait times and turnaround times for the other processes. 
        for (int i = 1; i < processes.length; i++) 
        {
            if (processes[order[i]].getArrivalTime() < processes[order[i-1]].getBurstTime() + processes[order[i-1]].getArrivalTime()) //If the arrival time of the current process is equal to the sum between the burst and arrival times of the previous process...
            {
                processWaitTimes[order[i]] = processTurnaroundTimes[order[i-1]]; //Since the current process gets executed immediately after the previous one finishes, the waiting time of the current process is equal to the turnaround time of the process that came before it. 
                processTurnaroundTimes[order[i]] = processWaitTimes[order[i]] + processBurstTimes[order[i]]; //The turnaround time of the current process is equal to its wait time plus its burst time. 
            }
            else //Otherwise...
            {
                processWaitTimes[order[i]] = processArrivalTimes[order[i]]; //The waiting time of the current process in the specified order is equal to the arrival time of the first process in that order. 
                processTurnaroundTimes[order[i]] = processArrivalTimes[order[i]] + processBurstTimes[order[i]]; //Given the current process in the specified order, its turnaround time is equal to its burst time added to its arrival time. 
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
        
        //System.out.println("Avg. Wait Time: " + averageWaitTime + " time units. "); //Output the average waiting time. 
        //System.out.println("Avg. Turnaround Time: " + averageTurnaroundTime + " time units. "); //Output the average turnaround time. 
        //System.out.println(""); //USED AS A SPACE
    }
    */
    
    //Given the wait times and the turnaround times of each process, this method will compute the average wait time and the average turnaround time.  
    /*
    private static void computeAverageTimes()
    {
        
    }
    */ 
}
