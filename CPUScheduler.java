import javax.swing.JOptionPane;

public class CPUScheduler
{
    private final int MAX_NUMBER_OF_PROCESSES = 10;
    private Process[] processes; //An array containing the individual processes in the ready queue. Each process has an ID Number, a Burst Time, and an Arrival Time. 
    private int[] processBurstTimes; //The individual burst times of each process. This array will be parallel to the array of processes. 
    private int[] processArrivalTimes; //The individual arival times of each process. This array will be parallel to the array of processes. 
    private int numberOfProcesses; //The number of processes initially in the ready queue. 
    private double averageWaitTime, averageTurnaroundTime; //The average wait time and the average turnaround time, respectively. 
    
    public CPUScheduler()
    {
        this.numberOfProcesses = 0;
        this.processes = new Process[10];
    }
    
    public void main(String[] args)
    {
        int choice; //This variable is an indicator as to what opertion to perform
        int inputManually; //This variable is a flag which states whether or not the user wants to initialize each of the processes manually or randomly. 
        
        do
        {
            choice = -1; //Resetting the indicator each time...!
            while(choice < 0 || choice > 3) //While the choice is out of bounds... 
            {
                try
                {
                    choice = Integer.parseInt(JOptionPane.showInputDialog("Select an option from the following menu:\nEnter 1 to AAAAA\nEnter 2 to BBBBB\nEnter 3 to exit")); //The user is shown a list of choices and is prompted to select among them to continue by entering the approporiate number. 
                }
                catch (NumberFormatException e) //When the user does not input anything... 
                {
                    JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the operation to perform.\nPlease choose a valid input.", "WARNING: INVALID INPUT DETECTED!", 2); //A message is shown to the user stating that an invalid input was entered.
                    choice = -1; //The choice is set to -1 so that the loop can execute again. 
                }
            }
            
            //Then, the number of processes initially in the ready queue is specified by the user. 
            try
            {
                do
                {
                    numberOfProcesses = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of processes initially in the ready queue.\nThere can be up to 10 processes in the queue at one time.")); //The user is prompted to input the number of processes initially in the ready queue.
                    if (numberOfProcesses < 1)
                    {
                        JOptionPane.showMessageDialog(null, "WARNING: The number of processes you have input is less than 1. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                    }
                    else if (numberOfProcesses > 10)
                    {
                        JOptionPane.showMessageDialog(null, "WARNING: Up to 10 processes can initially be placed on the ready queue. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
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
            algorithmInitialization(inputManually); //Regardless of which algorithm is chosen to be executed by the user or if whether the user decides to input the processes manually, some preliminary initializations are required, which is what this method does. 
            switch (choice) //There are different cases based off of what he user decide to do. 
            {
                case 1:
                {
                    //CODE GOES HERE!
                    break;
                }
                case 2:
                {
                    //CODE GOES HERE!
                    break;
                }
            }
        }
        while(choice != 3); //Continue to prompt the user as long as the user does not choose to exit the program. 
    }
    
    //This method will initialize each of the processes either manually (via user input) or randomly, depending on what the user decides to do. 
    private void algorithmInitialization(int inputManually)
    {
        processes = new Process[numberOfProcesses]; //The length of the array of processes is created as a new array of process objects whose length is the number of processes. 
        processBurstTimes = new int[numberOfProcesses]; //The array of burst times is created as an array of integers with a length equal to the number of processes. 
        processArrivalTimes = new int[numberOfProcesses]; //The array of arrival times is created as an array of integers with a length equal to the number of processes. 
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
                        burstTime = Integer.parseInt(JOptionPane.showInputDialog("Enter the CPU burst time of process #"+i+" (of "+numberOfProcesses+").")); //The user is prompted to input a burst time for the process, which is held by the placeholder. 
                        if (burstTime < 1)
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: The value of the burst time for this process is less than 1. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                        }
                        else if (burstTime > 10)
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: The value of the burst time for this process is greater than 10. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                        }
                    }
                    catch(NumberFormatException e) //When the user does not input anything...
                    {
                        JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the burst time of this proces.", "WARNING: NO INPUT DETECTED!", JOptionPane.WARNING_MESSAGE); //A message is shown to the user stating that no input was entered.
                        burstTime = 0; //The burst time of the process is set to 0 so that the loop can execute again. 
                    }
                }
                while(burstTime < 1 || burstTime > 10); //Continue to ask for a value until a proper input has been entered!
                processBurstTimes[i] = burstTime; //The burst time of the process is assigned to the value of its placholder. 

                //Lastly, the user inputs the time that the process arrives into the CPU. 
                do
                {
                    try
                    {
                        arrivalTime = Integer.parseInt(JOptionPane.showInputDialog("Enter the time that process #"+i+" (of "+numberOfProcesses+") arrives in the CPU.")); //The user is prompted to input a name for the process, which is held by the placeholder. 
                        if (arrivalTime < 0)
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: The value of the burst time for this process is less than 0. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                        }
                        else if (arrivalTime > 100)
                        {
                            JOptionPane.showMessageDialog(null, "WARNING: The value of the burst time for this process is greater than 100. \nPlease input a value between 1 and 10.", "WARNING: INVALID INPUT!", 2); //A message is shown to the user stating that no input was entered.
                        }
                    }
                    catch (NumberFormatException e) //When the user does not input anything...
                    {
                        JOptionPane.showMessageDialog(null, "WARNING: No valid input was placed for the arrival time of this process.", "WARNING: NO INPUT DETECTED!", JOptionPane.WARNING_MESSAGE); //A message is shown to the user stating that no input was entered.
                        arrivalTime = 0; //The arrival time of the process is set to 0 so that the loop can execute again. 
                    }
                }
                while(arrivalTime < 0 || arrivalTime > 100); //Continue to ask for a value until a proper input has been entered!
                processArrivalTimes[i] = arrivalTime; //The arrival time of the process is assigned to the value of its placeholder. 
                
                processes[i] = new Process(i+1, processBurstTimes[i], processArrivalTimes[i], 0, false); //With each of these specifications, a new process object is created and placed into the array of processes. 
            }
            
        }
        else //Otherwise, if the user decides to randomly generate each of the processes...
        {
            //This for loop will initialize each of the processes to be used with random burst times and arrival times. 
            for (int i = 0; i < numberOfProcesses; i++)
            {
                //First, the ID Number for the process being created is simply the index of the for loop. 
                processBurstTimes[i] = (int)((Math.random()*10)+1); //Second, the burst time of the process is set to a random number between 1 time unit and 10 time units, which is consistent with our assumptions. 
                processArrivalTimes[i] = (int)((Math.random()*100)+0); //Lastly, the arrival time of the process is set to a random number between 0 time units and 100 time units, which is consistent with our assumptions. 
                processes[i] = new Process(i+1, processBurstTimes[i], processArrivalTimes[i], 0, false); //With each of these specifications, a new process object is created and placed into the array of processes. 
            }
        }
        
        
        
    }
    
    //CPU SCHEDULING ALGORITHS GO HERE!
    
    /*
    Anything afterwards was added by Jude
    */

    public void addProcess()
    {
        this.processes[numberOfProcesses] = new Process(numberOfProcesses + 1);
        this.processes[numberOfProcesses].input();
        this.numberOfProcesses++;
    }
    
    public void addProcess(Process p)
    {
        this.processes[numberOfProcesses] = p.deepCopy();
        this.numberOfProcesses++;
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
    }
    
    public int getNumberOfProcesses()
    {
        return this.numberOfProcesses;
    }
    
}
