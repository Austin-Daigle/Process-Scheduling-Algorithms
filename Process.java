
public class Process {

	//inputs from the process
	int processNumber;
	int arrivalTime;
	int executionTime;	//also known as "burst time"
	int startTime = -1; //round robin only
	int responceTime = -1;  //round robin only
	int priority = -1; //for priority algorithms only
	
	//hidden processing attributes
	int currentExecutionTimeLeft;	
	
	//attributes generated from the process.
	int exitTime;
	int waitTime;
	int turnAroundTime;
	
	/**
	 * This is the standard constructor for the process object class
	 * @param processNumber This is a int array that stores the process number
	 * @param arrivalTime This is a int array that stores the arrival times of the processes 
	 * @param executionTime This is a int array that stores the execution times of the processes
	 */
	public Process(int processNumber, int arrivalTime, int executionTime) {
		this.processNumber = processNumber;
		this.arrivalTime = arrivalTime;
		this.executionTime = executionTime;
		this.currentExecutionTimeLeft = executionTime;
	}
	
	/**
	 * This is the standard constructor for the process object class
	 * @param processNumber This is a int array that stores the process number
	 * @param arrivalTime This is a int array that stores the arrival times of the processes 
	 * @param executionTime This is a int array that stores the execution times of the processes
	 * @param priority This is a int array that stores the priorities for each of the processes
	 */
	public Process(int processNumber, int arrivalTime, int executionTime, int priority) {
		this.processNumber = processNumber;
		this.arrivalTime = arrivalTime;
		this.executionTime = executionTime;
		this.currentExecutionTimeLeft = executionTime;
		this.priority = priority;
	}
	
	
	/**
	 * This is the getter method for the process number.
	 * @return Returns the int value for the 
	 * process number for the process.
	 */
	public int getProcessNumber() {
		return this.processNumber;
	}
	
	/**
	 * This is the getter method for the arrival time.
	 * @return Returns the int value for the arrival
	 * time for the process.
	 */
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	/**
	 * This is the getter method for the execution time
	 * @return Returns the int value of the execution 
	 * time.
	 */
	public int getExecutionTime() {
		return this.executionTime;
	}
	
	/**
	 * This is the getter method that 
	 * returns the current execution time left 
	 * of the current process.
	 * @return Returns the int value of the 
	 * current execution time left.
	 */
	public int getCurrentExecutionTimeLeft() {
		return this.currentExecutionTimeLeft;
	}
	
	/**
	 * This is the setter method for the current 
	 * execution time left
	 * @param currentExecutionTimeLeft This is the value that 
	 * updates the current execution time left.
	 */
	public void setCurrentExecutionTimeLeft(int currentExecutionTimeLeft) {
		this.currentExecutionTimeLeft = currentExecutionTimeLeft;
	}
	
	/**
	 * This is the getter method for the exit time
	 * for the current process.
	 * @return Returns the current exit time of the current process.
	 */
	public int getExitTime() {
		return this.exitTime;
	}
	
	/**
	 * This is the setter method for the exit time
	 * @param exitTime This is the value that updates the 
	 * current execution time left.
	 */
	public void setExitTime(int exitTime) {
		this.exitTime = exitTime;
	}
	
	/**
	 * This is getter method for the wait time
	 * for the current process.
	 * @return Returns the int value of waitTime.
	 */
	public int getWaitTime() {
		return this.waitTime;
	}
	
	/**
	 * This is the setter method for the
	 * wait time for the current process
	 * @param waitTime This is the value that updates the 
	 * current waitTime.
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * This is the getter method for the
	 * turn around time for the current process
	 * @return This returns the turn around time 
	 * for the current process.
	 */
	public int getTurnAroundTime() {
		return this.turnAroundTime;
	}
	
	/**
	 * This is a setter method for the field
	 * turn around time for the current process
	 * @param turnAroundTime This update the value
	 * of the turnaround time for the current process.
	 */
	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}
	
	/**
	 * This is a getter method for the field
	 * response time for the current process. 
	 * @return Returns the int value of the field
	 * response time.
	 */
	public int getResponceTime() {
		return this.responceTime;
	}
	
	/**
	 * This is a setter method for the field for the 
	 * Response time.
	 * @param responceTime returns the int value of the
	 * field responceTime.
 	 */
	public void setResponceTime(int responceTime) {
		this.responceTime = responceTime;
	}
	
	/**
	 * This is is the getter method that returns the 
	 * start time of the current process.
	 * @return This returns the current value of the 
	 * field startTime.
	 */
	public int getStartTime() {
		return this.startTime;
	}
	
	/**
	 * This is the setter for the field startTime.
	 * @param startTime This is the parsed value
	 * that updates the startTime.
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * This is a setter method that update the field 
	 * priority
	 * @param priority This updates the field 
	 * of priority of the current process.
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
	 * This is a getter method that returns the 
	 * priority of the current process
	 * @return This returns the priority of the
	 * current method.
	 */
	public int getPriority() {
		return priority;
	}
		
	/**
	 * This is a toString method that returns the string 
	 * value with the displayed attributes of the process object.
	 * 
	 * The fields that are returned are changed to the functional values
	 * of the process.
	 */
	public String toString() {
		
		if(this.responceTime == -1 && priority == -1) {
			return "[P"+this.processNumber+" | Arrival Time: "+this.arrivalTime
					+"\t| Burst: "+this.executionTime+"\t|"
					+" Finish Time: "+this.exitTime+"\t|"
					//+" Burst R.: "+this.currentExecutionTimeLeft+"\t|"
					+" Turn Around Time: "+this.turnAroundTime+"\t|"
					+" Wait Time: "+this.waitTime+"]";
		}
		else if(priority != -1) {
			return "[P"+this.processNumber+" | Arrival Time: "+this.arrivalTime
					+"\t| Burst: "+this.executionTime+"\t|"
					+" Finish Time: "+this.exitTime+"\t|"
					//+" Burst R.: "+this.currentExecutionTimeLeft+"\t|"
					+" Turn Around Time: "+this.turnAroundTime+"\t|"
					+" Wait Time: "+this.waitTime+"\t|"
					+" Priority: "+this.priority+"]";
		}
		else {
			return "[P"+this.processNumber+" | Arrival Time: "+this.arrivalTime
					+"\t| Burst: "+this.executionTime+"\t|"
					+" Finish Time: "+this.exitTime+"\t|"
					//+" Burst R.: "+this.currentExecutionTimeLeft+"\t|"
					+" Turn Around Time: "+this.turnAroundTime+"\t|"
					+" Wait Time: "+this.waitTime+"\t|"
					+ " Responce Time: "+this.responceTime+"]";
		}
		

	}
	
}
