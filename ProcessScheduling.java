import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Austin Daigle
 * @since 10/29/2021
 * @version 1.0
 * 
 * This program reads in a file and solves the 
 * process scheduling algorithm with the algorithm
 * and data defined on the file. 
 * 
 * This program uses a directory path string
 * as parameter run arguments.
 * 
 * Check and update the runtime arguments 
 * prior to executing this program.
 * 
 * 
 * This program supports the following process
 * scheduling algorithms:
 * # First Come First Serve
 * # Shortest Remaining Time Next
 * # Non-Preemptive Priority
 * # Preemptive Priority
 * # Shortest Job First
 * 
 */

public class ProcessScheduling {
	//Driver Method
	/**
	 * This is the driver method for the whole program.
	 * @param args The directory that is used as a runtime
	 * arguments within the execution.
	 * @throws Exception throws an exception with sub-processes
	 * error detection.
	 */
	public static void main(String[] args) throws Exception {
		//this string stores the algorithm string from the first line
		String algorithm = "";
		//this arraylist stores the file contents for each line
		ArrayList<int[]> fileContents = new ArrayList<int[]>();
		//If the runtime arguments are blank then return an error
		if(args.length < 1) {
			System.out.println("Error. Usage: Java ClassName inputfile");
			System.exit(1);
		}	
		//try to scan the whole file and store the data into the correct data structures. 
		try(Scanner input = new Scanner(new File(args[0]))) {
			//get the algorithm string from the first line
			algorithm = input.nextLine();
			//for every line in the file store the line within the ArrayList fileContents
			//convert array from string to int before adding the result into the ArrayList
			while(input.hasNextLine()) {
				fileContents.add(getIntArrayFromString(input.nextLine()));
			}
		}
		//throw an error message if there is no file present
		catch(FileNotFoundException e) {
			System.err.println("File cannot be found");
		}

		System.out.println("----------- Printing Data from file -----------");
		System.out.println(algorithm);
		//printout the scanned data from the file
		for(int i = 0; i < fileContents.size(); i++) {
			printArray(fileContents.get(i));
			System.out.println("");
		}
		System.out.println("-----------------------------------------------");
		//analyze and execute the scanned data and the algorithm definition
		analyzeAndRunFileInput(fileContents,algorithm);
		
	}
	

	/**
	 * This method take the scanned ArrayList content
	 * and algorithm input and executes the correct 
	 * method for solving the process scheduling routine
	 * @param fileContents This is an ArrayList that store the contents of the
	 * input file
	 * @param algorithm This is a string value for the selected algorithm in the 
	 * file
	 * @throws Exception throws exception if the input is incorrect
	 */
	public static void analyzeAndRunFileInput(ArrayList<int[]> fileContents, String algorithm) throws Exception {
		/*
		 * parse and run the first come first serve method with process inputs
		 * from the fileContents ArrayList
		 *
		 * For reference of the positions of fileContents in relation to the 
		 * rolls of data in relation of the algorithm:
		 * fileContents.get(0) = processNumber[]
		 * fileContents.get(1) = arrivalTime[]
		 * fileContents.get(2) = burstTime[]
		 * fileContents.get(3)[0] = quantum time for Round Robin only
		 * or...
		 * fileContents.get(3) = process priority for NPP and PP only
		 * 
		 */
		//Run first come first serve with the given parameters
		if(algorithm.equalsIgnoreCase("FCFS")) {
			fcfs(fileContents.get(0),fileContents.get(1),fileContents.get(2));
		}
		//Run shortest time remaining next with the given parameters
		if(algorithm.equalsIgnoreCase("SRTN")) {
			srtn(fileContents.get(0),
					fileContents.get(1),
					fileContents.get(2));
		}
		//Run round robin with the given parameters
		if(algorithm.equalsIgnoreCase("RR")) {
			rr(fileContents.get(0),
				fileContents.get(1),
				fileContents.get(2),
				fileContents.get(3)[0]);
		}
		//Run non-preemptive priority with the given parameters
		if(algorithm.equalsIgnoreCase("NPP")) {
			npp(fileContents.get(0),
				fileContents.get(1),
				fileContents.get(2),
				fileContents.get(3));
		}
		//Run preemptive priority with the given parameters
		if(algorithm.equalsIgnoreCase("PP")) {
			pp(fileContents.get(0),fileContents.get(1),
				fileContents.get(2),
				fileContents.get(3));
		}
		//Run shortest job first priority with the given parameters
		if(algorithm.equalsIgnoreCase("SJF")) {
			sjf(fileContents.get(0),
				 fileContents.get(1),
				 fileContents.get(2));
		}
	}
	
	/*
	 * Process Scheduling Algorithms
	 * 
	 * Functions used to solve for the final results of each process
	 * turnaround time = (exit time) - (arrival time)
	 * wait time = (exit time) - (arrival time) - (execution time)
	 * Response time =  (first execution time) - (arrival time)
	 */
	
	/**
	 * This method solves the given processes with the 
	 * first come first serve scheduling algorithm.
	 * @param processNumber This is a int array that holds the process numbers for the processes
	 * @param arrivalTime This is a int array that holds the arrival times for the processes
	 * @param burstTime This is a int array that hold the burst times for the processes
	 * @throws Exception throws exception if subroutine(s) encounter an exception
	 */
	
	/*
	 * First Come First Serve Algorithm pseudo Code
	 * 
	 * for each process
	 * 		execute the processes and update the final output variables
	 * print results
	 */
	public static void fcfs(int[] processNumber, int[] arrivalTime, int[] burstTime) throws Exception {
		//This is a arraylist that holds all of the processes generated by the parameters
		ArrayList<Process> allProcesses = generateProcesses(processNumber,arrivalTime,burstTime);
		//These arraylists stores time and process queues for the GanttChart
		ArrayList<String> chart = new ArrayList<String>();
		ArrayList<String> gchart = new ArrayList<String>();
		//create time variable
		int time  = 0;
		//update the gantt chart arraylist for times
		chart.add(time+"");
		
		for(int i = 0; i < allProcesses.size(); i++) {
			//update time
			time = time + allProcesses.get(i).getExecutionTime();
			//update the exit time
			allProcesses.get(i).setExitTime(time);
			//update the gantt chart arraylist(s)
			chart.add(time+"");
			gchart.add("P"+allProcesses.get(i).getProcessNumber());
			//turn around time
			if(i == 0) {
				//if the process is the first process then set the wait time to zero
				allProcesses.get(i).setWaitTime(0);
			}
			else {
				//create all of the variables for all of the final attributes
				int exit = allProcesses.get(i).getExitTime();
				int arrival = allProcesses.get(i).getArrivalTime();
				int execution = allProcesses.get(i).getExecutionTime();
				//update wait time
				allProcesses.get(i).setWaitTime(exit-arrival-execution);
			}
			//create all of the variables for all of the final attributes
			int exit = allProcesses.get(i).getExitTime();
			int arrival = allProcesses.get(i).getArrivalTime();
			//update turn around time
			allProcesses.get(i).setTurnAroundTime(exit-arrival);	
		}
		//printout all of the processes
		for(int i = 0; i < allProcesses.size(); i++) {
			System.out.println(allProcesses.get(i));
		}
		//printout the gantt chart
		formatGanttChart(gchart,chart);
	}
	
	/**
	 * This method solves the given processes with the 
	 * shortest remaining time next scheduling algorithm.
	 * @param processNumber This is a int array that holds the process numbers for the processes
	 * @param arrivalTime This is a int array that holds the arrival times for the processes
	 * @param burstTime This is a int array that hold the burst times for the processes
	 * @throws Exception throws exception if subroutine(s) encounter an exception
	 */
	
	/*
	 * Shortest Time Remaining next Algorithm pseudo code
	 * 
	 * while readyQueue is not empty
	 * 		if there are no more processes that can arrive
	 * 			terminate process
	 * 			update time
	 * 			update final variables 
	 *		end if
	 * 		else 
	 * 			update burst time of process with the difference of the next arrival time
	 * 			update time
	 * 				if process is complete
	 * 					update final variables
	 * 					terminate process
	 * 				end if
	 * 			update readyQueue with new time
	 * end else
	 */
	public static void srtn(int[] processes, int[] arrivalTimes, int[] burstTimes) throws Exception {
		//create time variable
		int time = 0;
		//This is a arraylist that holds all of the processes generated by the parameters
		ArrayList<Process> processList = generateProcesses(processes,arrivalTimes,burstTimes);
		//This is a arraylist that hold all of the completed processs
		ArrayList<Process> completedList = new ArrayList<Process>();
		//These arraylists stores time and process queues for the GanttChart
		ArrayList<String> chart = new ArrayList<String>();
		ArrayList<String> gchart = new ArrayList<String>();

		/**
		 * create a shallow copy of the process list for the completedList 
		 * The advantage of creating a shallow copy of the processList is that 
		 * we don't need to worry with reintroducing the completed processes back
		 * into the completedList in the correct order. Due to the memory associations
		 * between both list, the completedList is already in order with the 
		 * updated attributes upon program termination. This means that we don't
		 * need an extra sorting algorithm for our final result, thus this saves
		 * a fair amount of computational resources (not to mention extra code).
		 */
		completedList.addAll(processList);
		//create the readyQueue 
		ArrayList<Process> readyQueue = new ArrayList<Process>();
		//update the ready queue at starting time
		updateReadyQueueArrayList(processList,readyQueue,time);
		//update gantt chart
		chart.add(time+"");
		//while readyQueue is not empty
		while(!readyQueue.isEmpty()) {
			//if there are no more process to arrive 
			if(processList.isEmpty()) {
				//create variables to find the smallest execution time left in readyQueue
				int smallestIndex = readyQueue.size()-1;
				int smallestTimeNext = readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft();
				//iterate through ready queue searching for the smallest execution time left
				for(int i = readyQueue.size()-1; i >= 0; i--) {
					//if a smaller execution time has been found then update the index variables
					if(readyQueue.get(i).getCurrentExecutionTimeLeft() <= smallestTimeNext) {
						//update variables
						smallestIndex = i;
						smallestTimeNext = readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft();
					}
				}
				//update time with the current execution time left plus the current time
				time = time + readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft();
				//update gantt chart arraylists
				chart.add(time+"");
				gchart.add("P"+readyQueue.get(smallestIndex).getProcessNumber());
				//update the current execution time left to zero
				readyQueue.get(smallestIndex).setCurrentExecutionTimeLeft(0);
				//update the exit time to the current time
				readyQueue.get(smallestIndex).setExitTime(time);
				//update the turn around time
				readyQueue.get(smallestIndex).setTurnAroundTime(
						readyQueue.get(smallestIndex).getExitTime()-readyQueue.get(smallestIndex).getArrivalTime());
				//update the wait time
				readyQueue.get(smallestIndex).setWaitTime(
						readyQueue.get(smallestIndex).getTurnAroundTime()-readyQueue.get(smallestIndex).getExecutionTime());
				//remove the process from the readyQueue
				readyQueue.remove(smallestIndex);

				
			}
			//if there are processes that can still arrive from the processList
			else {
				//create variables to find the smallest execution time left in readyQueue
				int smallestIndex = readyQueue.size()-1;
				int smallestTimeNext = readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft();
				//iterate through ready queue searching for the smallest execution time left
				for(int i = readyQueue.size()-1; i >= 0; i--) {
					//if a smaller execution time has been found then update the index variables
					if(readyQueue.get(i).getCurrentExecutionTimeLeft() <= smallestTimeNext) {
						//update variables
						smallestIndex = i;
						smallestTimeNext = readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft();
					}
				}
				//difference between current time to the next arrival time
				int arrDiff = processList.get(0).getArrivalTime() - time;
				//if process is nearing completion
				if(readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft() < arrDiff) {
					//update time
					time += readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft();
					//update gantt chart arraylists
					chart.add(time+"");
					gchart.add("P"+readyQueue.get(smallestIndex).getProcessNumber());
					//set the current time left to zero
					readyQueue.get(smallestIndex).setCurrentExecutionTimeLeft(0);
					//set the exit time to the current time
					readyQueue.get(smallestIndex).setExitTime(time);
					//update the turn around time
					readyQueue.get(smallestIndex).setTurnAroundTime(
							readyQueue.get(smallestIndex).getExitTime()-readyQueue.get(smallestIndex).getArrivalTime());
					//update the wait time 
					readyQueue.get(smallestIndex).setWaitTime(
							readyQueue.get(smallestIndex).getTurnAroundTime()-readyQueue.get(smallestIndex).getExecutionTime());
					//remove the process from the readyQueue
					readyQueue.remove(smallestIndex);
					//update the readyQueue at the given time
					updateReadyQueueArrayList(processList,readyQueue,time);
				}
				//if process has executionTimeLeft that is larger than the difference of the next arrival time
				else {
					//update the current execution time left to the arrival difference
					readyQueue.get(smallestIndex).setCurrentExecutionTimeLeft(readyQueue.get(smallestIndex).getCurrentExecutionTimeLeft()-arrDiff);
					//update time
					time += arrDiff;
					//update gantt chart
					chart.add(time+"");
					gchart.add("P"+readyQueue.get(smallestIndex).getProcessNumber());
					//update the readyQueue at the given time
					updateReadyQueueArrayList(processList,readyQueue,time);
				}
			}
		}
		//printout the final result
		for(int i = 0; i < completedList.size(); i++) {
			System.out.println(completedList.get(i));
		}
		//printout the gantt chart
		formatGanttChart(gchart,chart);
	}
	
	/**
	 * This method solves the given processes with the 
	 * round robin process scheduling algorithm.
	 * @param processNumber This is a int array that holds the process numbers for the processes
	 * @param arrivalTime This is a int array that holds the arrival times for the processes
	 * @param burstTime This is a int array that hold the burst times for the processes
	 * @param quantum This is the quantum time that is used for the process functions
	 * @throws Exception throws exception if subroutine(s) encounter an exception
	 */
	
	/*
	 * Round Robin Algorithm pseudo code
	 * 
	 * while readyQueue is not empty
	 * 		if there are no more processes that can arrive
	 * 			if the process time left is less than the quantum value
	 * 				terminate process
	 * 				update final variables
	 * 			end if
	 * 			else 
	 * 				update process time left minus the current time minus quantum value
	 *			end else
	 *
	 *			if process is compete
	 *				terminate process
	 *				update final variables
	 *			end if
	 *		end if
	 * 		else 
	 * 			update burst time of process with the difference of the quantum
	 * 			update time
	 * 				if process is complete
	 * 					update final variables
	 * 					terminate process
	 * 				end if
	 * 			update readyQueue with new time
	 * end else
	 */
	public static void rr(int[] processes, int[] arrivalTimes, int[] burstTimes,
		int quantum) throws Exception {
			//create time variable
			int time = 0;
			//This is a arraylist that holds all of the processes generated by the parameters
			ArrayList<Process> processList = generateProcesses(processes,arrivalTimes,burstTimes);
			//This is a arraylist that hold all of the completed processs
			ArrayList<Process> completedList = new ArrayList<Process>();
			//These arraylists stores time and process queues for the GanttChart
			ArrayList<String> chart = new ArrayList<String>();
			ArrayList<String> gchart = new ArrayList<String>();
			/**
			 * create a shallow copy of the process list for the completedList 
			 * The advantage of creating a shallow copy of the processList is that 
			 * we don't need to worry with reintroducing the completed processes back
			 * into the completedList in the correct order. Due to the memory associations
			 * between both list, the completedList is already in order with the 
			 * updated attributes upon program termination. This means that we don't
			 * need an extra sorting algorithm for our final result, thus this saves
			 * a fair amount of computational resources (not to mention extra code).
			 */
			completedList.addAll(processList);
			//create the readyQueue FIFO queue object
			Queue<Process> readyQueue = new LinkedList<Process>();
			//update the gantt chart with the current time
			chart.add(time+"");
			//update the readyQueue
			updateReadyQueue(processList,readyQueue,time);
			//if the readyQueue is not empty
			while(!readyQueue.isEmpty()) {
				//if the process has not more execution time left
				if(readyQueue.peek().getCurrentExecutionTimeLeft() == 0) {
					//remove processs from readyQueue
					readyQueue.poll();
				}
				//if the current execution time left is less than quantum
				else if(readyQueue.peek().getCurrentExecutionTimeLeft() < quantum) {
					//if the process just was introduced to the readyQueue
					if(readyQueue.peek().getCurrentExecutionTimeLeft() == readyQueue.peek().getExecutionTime()) {
						//update the start time
						readyQueue.peek().setStartTime(time);
					}
					//update the time with the current time plus the current execution time left
					time = time + readyQueue.peek().getCurrentExecutionTimeLeft();
					//update gantt chart
					chart.add(time+"");
					gchart.add("P"+readyQueue.peek().getProcessNumber());
					//update the current execution time left to zero
					readyQueue.peek().setCurrentExecutionTimeLeft(0);
					//if the current process is complete then update final variables
					if(readyQueue.peek().getCurrentExecutionTimeLeft() == 0) {
						//update exit time
						readyQueue.peek().setExitTime(time);
						//update turn around time
						readyQueue.peek().setTurnAroundTime(((readyQueue.peek().getExitTime())-(readyQueue.peek().getArrivalTime())));
						//update wait time
						readyQueue.peek().setWaitTime((readyQueue.peek().getTurnAroundTime())-(readyQueue.peek().getExecutionTime()));
						//update the response time
						readyQueue.peek().setResponceTime((readyQueue.peek().getStartTime())-(readyQueue.peek().getArrivalTime()));
					}
					//update the readyQueue at the given time
					updateReadyQueue(processList,readyQueue,time);
					
				}
				else {
					//if the process has just entered the readyQueue
					if(readyQueue.peek().getCurrentExecutionTimeLeft() == readyQueue.peek().getExecutionTime()) {
						//update the start time
						readyQueue.peek().setStartTime(time);
					}
					//update current time to current time plus quantum
					time = time + quantum;
					//update readyQueue at the current time
					updateReadyQueue(processList,readyQueue,time);
					//update the gantt chart
					chart.add(time+"");
					gchart.add("P"+readyQueue.peek().getProcessNumber());
					//update the current execution time left to the difference of itself minus quantum
					readyQueue.peek().setCurrentExecutionTimeLeft(
							readyQueue.peek().getCurrentExecutionTimeLeft()-quantum);
					//if the process is complete then update the final attributes
					if(readyQueue.peek().getCurrentExecutionTimeLeft() == 0) {
						//update exit time
						readyQueue.peek().setExitTime(time);
						//update turn around time
						readyQueue.peek().setTurnAroundTime(((readyQueue.peek().getExitTime())-(readyQueue.peek().getArrivalTime())));
						//update wait time
						readyQueue.peek().setWaitTime((readyQueue.peek().getTurnAroundTime())-(readyQueue.peek().getExecutionTime()));
						//update response time
						readyQueue.peek().setResponceTime((readyQueue.peek().getStartTime())-(readyQueue.peek().getArrivalTime()));
					}
					//take the head and add it to the tail of the readyQueue
					readyQueue.add(readyQueue.poll());

				}
			}
			//printout the the final result
			for(int i = 0; i < completedList.size(); i++) {
				System.out.println(completedList.get(i));
			}
			//printout the gantt chart
			formatGanttChart(gchart,chart);

		}	
		
	/**
	 * This method solves the given processes with the 
	 * Non-Preemptive Priority process scheduling algorithm.
	 * @param processNumber This is a int array that holds the process numbers for the processes
	 * @param arrivalTime This is a int array that holds the arrival times for the processes
	 * @param burstTime This is a int array that hold the burst times for the processes
	 * @param priority This is a int array that hold the priorities for the processes
	 * @throws Exception throws exception if subroutine(s) encounter an exception
	 */
	
	/*
	 * Non-Preemptive Priority Algorithm pseudo code
	 * while readyQueue is not empty
	 * 		if there are no more processes that can arrive
	 *			find the highest priority process
	 *			update final variables
	 *			terminate process
	 *		end if
	 * 		else 
	 *			find the highest priority process
	 *			update final variables
	 *			terminate process
	 *			update readyQueue
	 *			if no processes has arrived at the current time
	 *				push time by one forward
	 *			end if
	 * end else
	 */
	public static void npp(int[] processes, int[] arrivalTimes, int[] burstTimes, int[] priority) {
		//create time variable
		int time = 0;
		//This is a arraylist that holds all of the processes generated by the parameters
		ArrayList<Process> processList = generateProcessesWithPriority(processes,arrivalTimes,burstTimes,priority);
		//This is a arraylist that hold all of the completed processs
		ArrayList<Process> completedList = new ArrayList<Process>();
		//These arraylists stores time and process queues for the GanttChart
		ArrayList<String> chart = new ArrayList<String>();
		ArrayList<String> gchart = new ArrayList<String>();
		/**
		 * create a shallow copy of the process list for the completedList 
		 * The advantage of creating a shallow copy of the processList is that 
		 * we don't need to worry with reintroducing the completed processes back
		 * into the completedList in the correct order. Due to the memory associations
		 * between both list, the completedList is already in order with the 
		 * updated attributes upon program termination. This means that we don't
		 * need an extra sorting algorithm for our final result, thus this saves
		 * a fair amount of computational resources (not to mention extra code).
		 */
		completedList.addAll(processList);
		//create the readyQueue arraylist
		ArrayList<Process> readyQueue = new ArrayList<Process>();
		//update the readyQueue at the current time
		updateReadyQueueArrayList(processList,readyQueue,time);
		//update the gantt chart
		chart.add(time+"");
		//while the readyQueue is not empty
		while(!readyQueue.isEmpty()) {
			//if the processList is not empty
			if(processList.isEmpty()) {
				//create priority index variables
				int priorityIndex = 0;
				int largestPriorityNext = readyQueue.get(priorityIndex).getPriority();
				//iterate thought the readyQueue to find the highest priority process
				for(int i = 0; i < readyQueue.size() ; i++) {
					//if a higher priority has been found
					if(readyQueue.get(i).getPriority() <= largestPriorityNext) {
						//update index variables
						priorityIndex = i;
						largestPriorityNext = readyQueue.get(i).getPriority();
					}
				}
				//update the time to the current time plus the current execution time left
				time += readyQueue.get(priorityIndex).getCurrentExecutionTimeLeft();
				//update gantt chart
				chart.add(time+"");
				gchart.add("P"+readyQueue.get(priorityIndex).getProcessNumber());
				//update the execution time left to zero
				readyQueue.get(priorityIndex).setCurrentExecutionTimeLeft(0);
				//update the exit time to time
				readyQueue.get(priorityIndex).setExitTime(time);
				//update the turn around time
				readyQueue.get(priorityIndex).setTurnAroundTime(
						readyQueue.get(priorityIndex).getExitTime() - 
						readyQueue.get(priorityIndex).getArrivalTime());
				//update the wait time
				readyQueue.get(priorityIndex).setWaitTime(readyQueue.get(priorityIndex).getTurnAroundTime()
						- readyQueue.get(priorityIndex).getExecutionTime());
				//remove the process from the readyQueue
				readyQueue.remove(priorityIndex);
			}
			else {
				//create priority index variables
				int priorityIndex = 0;
				int largestPriorityNext = readyQueue.get(priorityIndex).getPriority();
				//iterate thought the readyQueue to find the highest priority process
				for(int i = 0; i < readyQueue.size() ; i++) {
					if(readyQueue.get(i).getPriority() <= largestPriorityNext) {
						//if a higher priority has been found
						priorityIndex = i;
						largestPriorityNext = readyQueue.get(i).getPriority();
					}
				}
				//update time to the time plus the current execution time left
				time += readyQueue.get(priorityIndex).getCurrentExecutionTimeLeft();
				//update gantt chart
				chart.add(time+"");
				gchart.add("P"+readyQueue.get(priorityIndex).getProcessNumber());
				//update current execution time left to zero
				readyQueue.get(priorityIndex).setCurrentExecutionTimeLeft(0);
				//update exit time
				readyQueue.get(priorityIndex).setExitTime(time);
				//update turn around time
				readyQueue.get(priorityIndex).setTurnAroundTime(
						readyQueue.get(priorityIndex).getExitTime() - 
						readyQueue.get(priorityIndex).getArrivalTime());
				//update wait time
				readyQueue.get(priorityIndex).setWaitTime(readyQueue.get(priorityIndex).getTurnAroundTime()
						- readyQueue.get(priorityIndex).getExecutionTime());
				//remove process from readyQueue
				readyQueue.remove(priorityIndex);
				//update readyQueue at the given time
				updateReadyQueueArrayList(processList,readyQueue,time);
				
				//if the readyQueue is "stuck" with an empty readyQueue with processes that have yet to arrive
				if(!processList.isEmpty()) {
					//if the time is less than the next arrival time
					if(time < processList.get(0).getArrivalTime()) {
						//if the readyQueue is empty 
						while(readyQueue.isEmpty()) {
							//update time
							time++;
							//update gantt chart
							chart.add(time+"");
							gchart.add("P*");
							//update the readyQueue
							updateReadyQueueArrayList(processList,readyQueue,time);
						}

					}
				}
			}
		}
		//printout the final results
		for(int i = 0; i < completedList.size(); i++) {
			System.out.println(completedList.get(i));
		}
		//printout the gantt chart
		formatGanttChart(gchart,chart);
		
	}

	/**
	 * This method solves the given processes with the 
	 * Preemptive Priority process scheduling algorithm.
	 * @param processNumber This is a int array that holds the process numbers for the processes
	 * @param arrivalTime This is a int array that holds the arrival times for the processes
	 * @param burstTime This is a int array that hold the burst times for the processes
	 * @param priority This is a int array that hold the priorities for the processes
	 * @throws Exception throws exception if subroutine(s) encounter an exception
	 */
	
	/*
	 * Preemptive Priority Algorithm pseudo code
	 * while readyQueue is not empty
	 * 		if there are no more processes that can arrive
	 *			find the highest priority process
	 *			update final variables
	 *			terminate process
	 *		end if
	 * 		else 
	 *			find the highest priority process
				update the execution time minus the next arrival time
	 *			if no processes has arrived at the current time
	 *				push time by one forward
	 *			end if
	 * end else
	 */
	public static void pp(int[] processes, int[] arrivalTimes, int[] burstTimes, int[] priority) {
		//create time variable
		int time = 0;
		//This is a arraylist that holds all of the processes generated by the parameters
		ArrayList<Process> processList = generateProcessesWithPriority(processes,arrivalTimes,burstTimes,priority);
		//This is a arraylist that hold all of the completed processs
		ArrayList<Process> completedList = new ArrayList<Process>();
		//These arraylists stores time and process queues for the GanttChart
		ArrayList<String> chart = new ArrayList<String>();
		ArrayList<String> gchart = new ArrayList<String>();
		/**
		 * create a shallow copy of the process list for the completedList 
		 * The advantage of creating a shallow copy of the processList is that 
		 * we don't need to worry with reintroducing the completed processes back
		 * into the completedList in the correct order. Due to the memory associations
		 * between both list, the completedList is already in order with the 
		 * updated attributes upon program termination. This means that we don't
		 * need an extra sorting algorithm for our final result, thus this saves
		 * a fair amount of computational resources (not to mention extra code).
		 */
		completedList.addAll(processList);
		ArrayList<Process> readyQueue = new ArrayList<Process>();
		//update the readyQueue at the given time
		updateReadyQueueArrayList(processList,readyQueue,time);
		//update the gantt chart
		chart.add(time+"");

		//if the readyQueue is not empty
		while(!readyQueue.isEmpty()) {
			//if the processList is not empty
			if(processList.isEmpty()) {
				//create the priority indexes
				int priorityIndex = 0;
				int largestPriorityNext = readyQueue.get(priorityIndex).getPriority();
				//find the greatest priority process
				for(int i = 0; i < readyQueue.size() ; i++) {
					//if the higher priority process has been found
					if(readyQueue.get(i).getPriority() <= largestPriorityNext) {
						//update the greatest priority indexes
						priorityIndex = i;
						largestPriorityNext = readyQueue.get(i).getPriority();
					}
				}
				//update time with the rest of the execution time
				time += readyQueue.get(priorityIndex).getCurrentExecutionTimeLeft();
				//update gantt chart
				chart.add(time+"");
				gchart.add("P"+readyQueue.get(priorityIndex).getProcessNumber());
				//set current exection time left to zero
				readyQueue.get(priorityIndex).setCurrentExecutionTimeLeft(0);
				//update exit time
				readyQueue.get(priorityIndex).setExitTime(time);
				//update turn around time 
				readyQueue.get(priorityIndex).setTurnAroundTime(
						readyQueue.get(priorityIndex).getExitTime() - 
						readyQueue.get(priorityIndex).getArrivalTime());
				//update wait time
				readyQueue.get(priorityIndex).setWaitTime(readyQueue.get(priorityIndex).getTurnAroundTime()
						- readyQueue.get(priorityIndex).getExecutionTime());
				//remove process from readyQueue
				readyQueue.remove(priorityIndex);
			}
			else {
				//create the priority indexes
				int priorityIndex = 0;
				int largestPriorityNext = readyQueue.get(priorityIndex).getPriority();
				//find the greatest priority process
				for(int i = 0; i < readyQueue.size() ; i++) {
					//if the higher priority process has been found
					if(readyQueue.get(i).getPriority() <= largestPriorityNext) {
						//update the greatest priority indexes
						priorityIndex = i;
						largestPriorityNext = readyQueue.get(i).getPriority();
					}
				}
				//this if the difference between the current time minus the next arrival time
				int arrDiff = processList.get(0).getArrivalTime() - time;
				//if the current process has less time than the arrival time
				if(readyQueue.get(priorityIndex).getCurrentExecutionTimeLeft() < arrDiff) {
					//update the time with the current execution time left
					time += readyQueue.get(priorityIndex).getCurrentExecutionTimeLeft();	
					//update the gantt chart
					chart.add(time+"");
					gchart.add("P"+readyQueue.get(priorityIndex).getProcessNumber());
					//set the current execution time to zero
					readyQueue.get(priorityIndex).setCurrentExecutionTimeLeft(0);
					//set the exit time to time
					readyQueue.get(priorityIndex).setExitTime(time);
					//set the turn around time
					readyQueue.get(priorityIndex).setTurnAroundTime(
							readyQueue.get(priorityIndex).getExitTime()-readyQueue.get(priorityIndex).getArrivalTime());
					//set the wait time 
					readyQueue.get(priorityIndex).setWaitTime(
							readyQueue.get(priorityIndex).getTurnAroundTime()-readyQueue.get(priorityIndex).getExecutionTime());
					//remove the readyQueue
					readyQueue.remove(priorityIndex);
					//update the readyQueue at the given time
					updateReadyQueueArrayList(processList,readyQueue,time);
				}
				else {
					//set the current execution time left to the difference of the current execution time minus next arrival time
					readyQueue.get(priorityIndex).setCurrentExecutionTimeLeft(readyQueue.get(priorityIndex).getCurrentExecutionTimeLeft()-arrDiff);
					//update time
					time += arrDiff;
					//update gantt chart
					chart.add(time+"");
					gchart.add("P"+readyQueue.get(priorityIndex).getProcessNumber());
					//update the readyQueue at the given time
					updateReadyQueueArrayList(processList,readyQueue,time);
				}

				//if the readyQueue is "stuck" with an empty readyQueue with processes that have yet to arrive
				if(!processList.isEmpty()) {
					//if the time is less than the next arrival time
					if(time < processList.get(0).getArrivalTime()) {
						//if the readyQueue is empty 
						while(readyQueue.isEmpty()) {
							//update the time
							time++;
							//update the gantt time
							chart.add(time+"");
							gchart.add("P*");
							//update the readyQueue
							updateReadyQueueArrayList(processList,readyQueue,time);
						}

					}
				}
			}
		}
		//printout the completed process
		for(int i = 0; i < completedList.size(); i++) {
			System.out.println(completedList.get(i));
		}
		//printout the gantt chart
		formatGanttChart(gchart,chart);
		
	}
	
	/**
	 * This method solves the given processes with the 
	 * Shortest Job First process scheduling algorithm.
	 * @param processNumber This is a int array that holds the process numbers for the processes
	 * @param arrivalTime This is a int array that holds the arrival times for the processes
	 * @param burstTime This is a int array that hold the burst times for the processes
	 * @throws Exception throws exception if subroutine(s) encounter an exception
	 */
	
	/*
	 * Shortest Job First Algorithm pseudo code
	 * while readyQueue is not empty
	 * 		if there are no more processes that can arrive
	 *			find the shortest job in the readyQueue
	 *			update final variables
	 *			terminate processes 
	 *		end if
	 * 		else 
	 *			find the shortest job in the readyQueue
	 *			update final variables
	 *			terminate processes 
	 *			update readyQueue
	 *			if no processes has arrived at the current time
	 *				push time by one forward
	 *			end if
	 * end else
	 */
	public static void sjf(int[] processes, int[] arrivalTimes, int[] burstTimes) throws Exception {
		int time = 0;
		ArrayList<Process> processList = generateProcesses(processes,arrivalTimes,burstTimes);
		ArrayList<Process> completedList = new ArrayList<Process>();
		ArrayList<String> chart = new ArrayList<String>();
		ArrayList<String> gchart = new ArrayList<String>();
		/**
		 * create a shallow copy of the process list for the completedList 
		 * The advantage of creating a shallow copy of the processList is that 
		 * we don't need to worry with reintroducing the completed processes back
		 * into the completedList in the correct order. Due to the memory associations
		 * between both list, the completedList is already in order with the 
		 * updated attributes upon program termination. This means that we don't
		 * need an extra sorting algorithm for our final result, thus this saves
		 * a fair amount of computational resources (not to mention extra code).
		 */
		completedList.addAll(processList);
		ArrayList<Process> readyQueue = new ArrayList<Process>();
		//update the readyQueue
		updateReadyQueueArrayList(processList,readyQueue,time);
		//update the gantt chart
		chart.add(time+"");
		//if the readyQueue is not empty
		while(!readyQueue.isEmpty()) {
			//if the processList is not empty
			if(processList.isEmpty()) {
				//create the indexes 
				int shortestJobIndex = 0;
				int shortestJobNext = readyQueue.get(shortestJobIndex).getExecutionTime();
				//iterate through the readyQueue
				for(int i = readyQueue.size()-1; i >= 0; i--) {
					//if a process with a shorter job has been identified then update the indexes
					if(readyQueue.get(i).getExecutionTime() <= shortestJobNext) {
						//update the indexes
						shortestJobIndex = i;
						shortestJobNext = readyQueue.get(i).getExecutionTime();
					}
				}
				//update the time to the current time plus the current execution time left
				time += readyQueue.get(shortestJobIndex).getCurrentExecutionTimeLeft();
				//update the gantt chart
				chart.add(time+"");
				gchart.add("P"+readyQueue.get(shortestJobIndex).getProcessNumber());
				//update the current execution time to 0
				readyQueue.get(shortestJobIndex).setCurrentExecutionTimeLeft(0);
				//update the exit time to time
				readyQueue.get(shortestJobIndex).setExitTime(time);
				//update the turn around time
				readyQueue.get(shortestJobIndex).setTurnAroundTime(
						readyQueue.get(shortestJobIndex).getExitTime() - 
						readyQueue.get(shortestJobIndex).getArrivalTime());
				//update the wait time
				readyQueue.get(shortestJobIndex).setWaitTime(readyQueue.get(shortestJobIndex).getTurnAroundTime()
						- readyQueue.get(shortestJobIndex).getExecutionTime());
				//remove the process from the readyQueue
				readyQueue.remove(shortestJobIndex);
				
			}
			else {
				//create the indexes 
				int shortestJobIndex = 0;
				int shortestJobNext = readyQueue.get(shortestJobIndex).getExecutionTime();
				//iterate through the readyQueue
				for(int i = readyQueue.size()-1; i >= 0; i--) {
					//if a process with a shorter job has been identified then update the indexes
					if(readyQueue.get(i).getExecutionTime() <= shortestJobNext) {
						//update the indexes
						shortestJobIndex = i;
						shortestJobNext = readyQueue.get(i).getExecutionTime();
					}
				}
				//update the time to the current execution time left
				time += readyQueue.get(shortestJobIndex).getCurrentExecutionTimeLeft();
				//update the gantt chart
				chart.add(time+"");
				gchart.add("P"+readyQueue.get(shortestJobIndex).getProcessNumber());
				//update the current execution time left to 0
				readyQueue.get(shortestJobIndex).setCurrentExecutionTimeLeft(0);
				//update the exit time to time
				readyQueue.get(shortestJobIndex).setExitTime(time);
				//update turn around time
				readyQueue.get(shortestJobIndex).setTurnAroundTime(
						readyQueue.get(shortestJobIndex).getExitTime() - 
						readyQueue.get(shortestJobIndex).getArrivalTime());
				//update the wait time
				readyQueue.get(shortestJobIndex).setWaitTime(readyQueue.get(shortestJobIndex).getTurnAroundTime()
						- readyQueue.get(shortestJobIndex).getExecutionTime());
				//remove the process from the readyQueue
				readyQueue.remove(shortestJobIndex);
				//update the readyQueue
				updateReadyQueueArrayList(processList,readyQueue,time);
				
				//if the readyQueue is "stuck" with an empty readyQueue with processes that have yet to arrive
				if(!processList.isEmpty()) {
					//if the time is less than the next arrival time
					if(time < processList.get(0).getArrivalTime()) {
						//if the readyQueue is empty 
						while(readyQueue.isEmpty()) {
							//update time
							time++;
							//update gantt chart
							chart.add(time+"");
							gchart.add("P*");
							//update the readyQueue
							updateReadyQueueArrayList(processList,readyQueue,time);
						}

					}
				}
			}
					
		}
		//printout the final output
		for(int i = 0; i < completedList.size(); i++) {
			System.out.println(completedList.get(i));
		}
		//printout the gnatt chart
		formatGanttChart(gchart,chart);

	}
	
	/*
	 * These are the dependencies
	 * {These are the sub-routines/methods that are shared between 
	 * scheduling algorithms in order to render the output}
	 */
	
	/**
	 * This method takes in the integer arrays for the parameters
	 * for processTime, arrivalTime,& burstTime and creates process objects
	 * for the process scheduling algorithms
	 * @param processNumber This is the int array for the processNumber
	 * @param arrivalTime This is the int array for the arrivalTime
	 * @param burstTime This is the int array for the burstTime
	 * @return returns a process scheduling object ArrayList
	 * @throws Exception Throws an exception if the inputs are missing elements 
	 * (mismatched int[] lengths)
	 */
	public static ArrayList<Process> generateProcesses(int[] processNumber,
		int[] arrivalTime, int[] burstTime) throws Exception {
		//If the lengths are unequal for the inputs then throw an exception
		if((processNumber.length != arrivalTime.length)
			|| (arrivalTime.length != burstTime.length)) {
			throw new Exception("[Error]: Incorrect inputs lengths found!");
		}
		//create an ArrayList to store all of the process objects
		ArrayList<Process> allProcesses = new ArrayList<Process>();
		//for all of the elements within the parameters, create process objects
		for(int i = 0; i < processNumber.length; i++) {
			allProcesses.add(new Process(processNumber[i],arrivalTime[i],burstTime[i]));
		}
		//return the object ArrayList of the process objects
		return allProcesses;
	}
	
	/**
	 * This method takes in the integer arrays for the parameters
	 * for processTime, arrivalTime,& burstTime and creates process objects
	 * for the process scheduling algorithms
	 * @param processNumber This is the int array for the processNumber
	 * @param arrivalTime This is the int array for the arrivalTime
	 * @param burstTime This is the int array for the burstTime
	 * @param priority This is an int array for the priorities for each process
	 * @return returns a process scheduling object ArrayList
	 * @throws Exception Throws an exception if the inputs are missing elements 
	 * (mismatched int[] lengths)
	 */
	public static ArrayList<Process> generateProcessesWithPriority(int[] processNumber,
		int[] arrivalTime, int[] burstTime, int[] priority) {
		//If the lengths are unequal for the inputs then throw an exception
		if((processNumber.length != arrivalTime.length)
			|| (arrivalTime.length != burstTime.length)
			|| (arrivalTime.length != priority.length)) {
			System.err.println("[Error]: Incorrect inputs lengths found!");
		}
		//create an ArrayList to store all of the process objects
		ArrayList<Process> allProcesses = new ArrayList<Process>();
		//for all of the elements within the parameters, create process objects
		for(int i = 0; i < processNumber.length; i++) {
			allProcesses.add(new Process(processNumber[i],arrivalTime[i],burstTime[i],priority[i]));
		}
		//return the object ArrayList of the process objects
		return allProcesses;
	}

	/**
	 * This methods takes string input from the file scanned
	 * and converts it to an integer array
	 * @param input This is string (separated with commas for each int)
	 * @return returns the integer string from the input.
	 */
	public static int[] getIntArrayFromString(String input) {
		//create a string array with the split input (each entry is split at each comma)
		String[] integerString = input.split(","); 
		//create a int result at the size of the string array integerString
		int[] result = new int[integerString.length]; 
		//create an index object for the iteration process
		int index = 0;  
		//for each value of textValue to integerString
		for(String textValue:integerString) { 
			//update result at the current index to the parsedInt of textValue
			result[index] = Integer.parseInt(textValue);
			//update the index variable
			index++;  
		}
		//return the int array as the return result
		return result;
	}
	
	/**
	 * This method printout an integer array
	 * @param input This is the integer input that is printout out.
	 */
	public static void printArray(int[] input) {
		System.out.print("[");
		//for the length of the input
		for(int i = 0; i < input.length; i++) {
			//if the current position is not at the end
			if(i < input.length-1) {
				
				System.out.print(input[i]+",");
			}
			else {
				
				System.out.print(input[i]);
			}
		}
		System.out.print("]");
	}
	
	/**
	 * This method update the readyQueue of the current process
	 * scheduling algorithm. 
	 * @param processList This is the list of the processes that the processes are
	 * being pulled from and loaded into the readyQueue
	 * @param readyQueue This is the Arraylist version of the readyQueue
	 * @param time This is the current time, this decides which processes are loaded into the 
	 * queue.
	 * 
	 */
	public static void updateReadyQueueArrayList(ArrayList<Process> processList,ArrayList<Process> readyQueue,
			int time) {
			//get arrived processes
			ArrayList<Process> arrivedProcesses = new ArrayList<Process>();
			//for the size of the processList
			for(int index = 0; index < processList.size(); index++) {
				//if the arrivalTime is less than or equal to the current time
				if(processList.get(index).getArrivalTime() <= time) {
					//add the process to the processList
					arrivedProcesses.add(processList.get(index));
				}
			}
			//remove the arrived processes from the processList
			processList.removeAll(arrivedProcesses);
			//for every arrived processes add the processes to the readyQueue
			for(int i = 0; i < arrivedProcesses.size(); i++) {
				readyQueue.add(arrivedProcesses.get(i));
			}
		}
	
	/**
	 * This method update the readyQueue of the current process
	 * scheduling algorithm. 
	 * @param processList This is the list of the processes that the processes are
	 * being pulled from and loaded into the readyQueue
	 * @param readyQueue This is the queue (FIFO) version of the readyQueue
	 * @param time This is the current time, this decides which processes are loaded into the 
	 * queue.
	 * This version of the method is only used for round robin.
	 */
	public static void updateReadyQueue(ArrayList<Process> processList,Queue<Process> readyQueue,
			int time) {
		//get arrived processes
			ArrayList<Process> arrivedProcesses = new ArrayList<Process>();
			//for the size of the processList
			for(int index = 0; index < processList.size(); index++) {
				//if the arrivalTime is less than or equal to the current time
				if(processList.get(index).getArrivalTime() <= time) {
					arrivedProcesses.add(processList.get(index));
				}
			}
			//remove the arrived processes from the processList
			processList.removeAll(arrivedProcesses);
			//for every arrived processes add the processes to the readyQueue
			for(int i = 0; i < arrivedProcesses.size(); i++) {
				readyQueue.offer(arrivedProcesses.get(i));
			}
		}


	/**
	 * This method printout the gantt chart from the inputs
	 * @param processOrder This is the ArrayList that holds the order of the processes
	 * @param timeOrder This is the ArrayList that holds the order of the process times.
	 */
	public static void formatGanttChart(ArrayList<String> processOrder, ArrayList<String> timeOrder) {
		System.out.println("\nPrinting Out Gantt Chart: ");
		String firstLine = "|";
		String secondLine = "";
		//for the length of processOrder, printout the process order
		for(int i = 0; i < processOrder.size(); i++) {
			firstLine += "\t"+processOrder.get(i)+"\t|";
		}
		//for the length of timeOrder, printout the process order
		for(int i = 0; i < timeOrder.size(); i++) {
			secondLine += timeOrder.get(i)+"\t\t";
		}
		//printout the result
		System.out.println(firstLine);
		System.out.println(secondLine);
	
	
	}
	
	
	
	
	
	
}

