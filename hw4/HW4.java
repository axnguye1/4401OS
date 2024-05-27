/*
Name: Amanda Nguyen
CSCI 4401/5401
Spring 2024
Assignment 4

Due: Monday, 4/8 @ 11:59pm

- This assignment contains a total of 50 points.
- The problems you will solve all have ToDo: notes. 
- You can create new variables, but you cannot hardcode values. 
- The arguments passed to the methods are example test values. You code should not be built to work for these specific values. I.e., it should still work if other test values were used. 
- Do not modify the current print statements. If you add print statements for testing & debugging, please remove them before submitting.
- Submit: this modified file containing your solutions.
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class HW4{ //don't rename

	public static void main(String args[]){
		part1test();
		part2test();
	}

	/********************************************************************************************
	Part I: Process Scheduling
		- firstComeFirsServe()
		- shortestJobFirst()
		- roundRobin()
	********************************************************************************************/

	public static void part1test(){
		/*	This is for testing your code. 
			DO NOT modify this method
		*/
		
		printSectionHeader("Part I: Process Scheduling");

		//Test firstComeFirsServe()
		System.out.println("firstComeFirsServe() [8 points]");
		System.out.println(String.format("\t%1$8s%2$20s", "Process", "Turnaround Time(ms)"));
		int[] fcfsJobs = {12, 8, 10, 4, 2};
		firstComeFirstServe(fcfsJobs);
	
		//Test shortestJobFirst()
		int[] sjfJobs = {12, 8, 10, 4, 2};
		System.out.println("\nshortestJobFirst() [8 points]");
		System.out.println(String.format("\t%1$8s%2$20s", "Process", "Turnaround Time(ms)"));
		shortestJobFirst(sjfJobs);

		//Test roundRobin()
		int[] rrJobs = {12, 8, 10, 4, 2};
		System.out.println("\nroundRobin() [14 points]");
		System.out.println(String.format("\t%1$8s%2$20s", "Process", "Turnaround Time(ms)"));
		roundRobin(rrJobs);
	}

	public static void firstComeFirstServe(int[] jobs) {
		/* Consider a system using first-come, first-serve process scheduling algorithm. 
		   Write code to determine the turnaround times @turnaroundTimes for each process in @jobs, and 
			compute the average turnaround @avgTurnaroundTime.
		   You can assume jobs will have a unique runtime, makng them safe to use as keys in @turnaroundTimes.
		*/

		float avgTurnaroundTime = 0;
		HashMap<Integer, Integer> turnaroundTimes = new HashMap<Integer, Integer>();


		for (int job : jobs) {
			turnaroundTimes.put(job, 0);				
		}

		//ToDo: add your code to calculate @turnaroundTimes and @avgTurnaroundTime.
		int currentTime = 0;
		for (int job : jobs) {
			avgTurnaroundTime += currentTime + job;
			currentTime += job; //update time
			turnaroundTimes.put(job, currentTime); //update turnaround time
		}

		avgTurnaroundTime /= jobs.length; // Calculate average

		for (Integer job : 	turnaroundTimes.keySet()) { //Do not modify		
			System.out.println(String.format("\t%1$8d%2$20d", job, turnaroundTimes.get(job)) ); //Do not modify		
		}
		System.out.println("\tAverage Time: " +  avgTurnaroundTime); //Do not modify		
	}
	
	public static void shortestJobFirst(int[] jobs) {
		/* Consider a system using shortest-job first process scheduling algorithm. 
		   Write code to determine the turnaround times @turnaroundTimes for each process in @jobs, and 
			compute the average turnaround @avgTurnaroundTime.
		   You can assume jobs will have a unique runtime, makng them safe to use as keys in @turnaroundTimes.
		*/

		float avgTurnaroundTime = 0;
		HashMap<Integer, Integer> turnaroundTimes = new HashMap<Integer, Integer>();

		for (int job : jobs) {
			turnaroundTimes.put(job, 0);				
		}

		//ToDo: add your code to calculate @turnaroundTimes and @avgTurnaroundTime.
		Arrays.sort(jobs);

		int currentTime = 0;
		for( int job : jobs) {
			avgTurnaroundTime += currentTime + job;
			currentTime += job; //update
			turnaroundTimes.put(job, currentTime); //update
		}
		avgTurnaroundTime /= jobs.length;

		for (Integer job : 	turnaroundTimes.keySet()) { //Do not modify		
			System.out.println(String.format("\t%1$8d%2$20d", job, turnaroundTimes.get(job)) ); //Do not modify		
		}

		System.out.println("\tAverage Time: " +  avgTurnaroundTime); //Do not modify	
	}

	public static void roundRobin(int[] jobs) {
		/* Consider a system using round robin process scheduling algorithm (assume a quantum = 1). 
		   Write code to determine the turnaround times @turnaroundTimes for each process in @jobs, and 
			compute the average turnaround @avgTurnaroundTime.
		   You can assume jobs will have a unique runtime, makng them safe to use as keys in @turnaroundTimes.
		*/

		float avgTurnaroundTime = 0;
		HashMap<Integer, Integer> turnaroundTimes = new HashMap<Integer, Integer>();

		Queue<Integer> jobQueue = new LinkedList<>();//useing queue, because we can add and remove, and poll(removing head of queue).
		for (int job : jobs) {
			jobQueue.add(job);
			turnaroundTimes.put(job, 0);				
		}

		//ToDo: add your code to calculate @turnaroundTimes and @avgTurnaroundTime.
		int processTime = 0;
		int totalTurnaroundTime = 0;

		int completedJobs = 0;


		while (!jobQueue.isEmpty()) {
			int thisJob = jobQueue.poll(); //bring next job in queue
			if (thisJob > 1) {
				jobQueue.add(thisJob - 1); //if more than 1job, we  - 1 to queue
			}
			totalTurnaroundTime += processTime + 1;
			processTime++; // update time
			if (thisJob == 1) {
				turnaroundTimes.put(thisJob, processTime); //update time
				completedJobs++;
			}
		}

		for (Integer job : 	turnaroundTimes.keySet()) { //Do not modify		
			System.out.println(String.format("\t%1$8d%2$20d", job, turnaroundTimes.get(job)) ); //Do not modify		
		}
		System.out.println("\tAverage Time: " +  avgTurnaroundTime); //Do not modify		
	}
	

	/********************************************************************************************
	Part II: Deadlocks
		- safeState()
		- deadlockDetection()
	********************************************************************************************/

	public static void part2test(){
		/*	This is for testing your code. 
			DO NOT modify this method
		*/

		printSectionHeader("Part II: Deadlocks");

		//Test safeState()
		System.out.println("safeState() [10 points]");
		int[][] allocation1 = {{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
		int[][] need1 = {{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
		int[] available1 = {0, 0, 0};		
		safeState(allocation1, need1, available1);
		int[][] allocation2 = {{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
		int[][] need2 = {{0, 2, 0}, {2, 0, 2}, {0, 0, 3}, {1, 0, 0}, {0, 0, 2}};
		int[] available2 = {0, 0, 0};		
		safeState(allocation2, need2, available2);


		System.out.println("\ndeadlockDetection() [10 points]");
		int[][] allocation3 = {{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
		int[][] need3 = {{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
		int[] available3 = {0, 0, 0};		
		deadlockDetection(allocation3, need3, available3);
		int[][] allocation4 = {{0, 1, 0}, {2, 0, 0}, {1, 0, 0}, {2, 1, 1}, {0, 0, 2}};
		int[][] need4 = {{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
		int[] available4 = {0, 0, 0};		
		deadlockDetection(allocation4, need4, available4);
	}

	public static void safeState(int[][] allocation, int[][] need, int[] available) {
		/* Consider a system using deadlock avoidance (e.g., Banker's algorithm). 
		   Write code to determine if all the processes can finish @finish (a boolean array 
			where true means the process can finish), and if a safe sequence exists, determine one
			of the safe sequences @sequence. You only need to return one possible safe sequence (not 
			all possible safe sequences). For the sequence, use the index position in @need or @allocation, 
			e.g., [1, 0, 2, 4, 3]
		*/

		boolean[] finish = new boolean[allocation.length]; //true means a process can finish, false means a process cannot finish.
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		

		//ToDo: add your code to determine @finish and @sequence.
		int[] work = Arrays.copyOf(available, available.length); //copy of resources
		//need to know if process can finish
		//make loop for processes that cna't finish
		//loop to know check if process can finish or not
		//if it can finish, then make boolean true.
		boolean canFinish = true; // can process finish or not?
		while (canFinish) {
			canFinish = false;
			for (int i = 0; i < allocation.length; i++) { //loop unti canFinsh is false.
				if (!finish[i]) {
					boolean canProcFinish = true;
					for (int j = 0; j < work.length; j++) { //enough to finish proccess?
						if (need[i][j] > work[j]) {
							canProcFinish = false;
							break;
						}
					}
					if (canProcFinish) { //make process true, if can finish.
						finish[i] = true;
						sequence.add(i);
						for (int j = 0; j < work.length; j++) {
							work[j] += allocation[i][j];
						}
						canFinish = true;
						break;
					}
				}
			}
		}

		for (boolean process: finish) { //Do not modify
			if (!process) { //Do not modify
				System.out.println("\tNo safe sequence exists."); //Do not modify
				return; //Do not modify
			}
		}
		System.out.println("\tSafe sequence: " + sequence); //Do not modify
	}

	public static void deadlockDetection(int[][] allocation, int[][] need, int[] available) {
		/* Consider a system using deadlock detection. 
		   Write code to determine if a deadlock is present. If a deadlock is present, store the deadlocked
			process in @deadlockedProcess. For the deadlocked processes, use the index position in @need 
			or @allocation, e.g., [1, 0, 2, 4, 3]
		*/

		ArrayList<Integer> deadlockedProcesses = new ArrayList<Integer>();
		
		//ToDo: add your code to determine @deadlockedProcesses.
		boolean[] finish = new boolean[allocation.length]; // can finish?
		int[] work = Arrays.copyOf(available, available.length); // copy available resources

		for (int i = 0; i < allocation.length; i++) {
			if (!finish[i]) {
				boolean canFinish = true;
				for (int j = 0; j < available.length; j++) {
					if (need[i][j] > work[j]) {
						canFinish = false;
						break;
					}
				}
				if (canFinish) {
					for (int j = 0; j < available.length; j++) {
						work[j] += allocation[i][j];
					}
					finish[i] = true;
					i = -1; //recheck process
				}
			}
		}

		for (int i = 0; i < finish.length; i++) {
			if (!finish[i]) {
				deadlockedProcesses.add(i);
			}
		}
		
		if (deadlockedProcesses.size() > 0) {
			System.out.println("\tDeadlocked processes: " + deadlockedProcesses);
		}
		else {
			System.out.println("\tNo deadlock exists");
		}
	}


	public static void printSectionHeader(String sectionName) {
		//Don't modify this
		System.out.println("\n" + "-".repeat(30));
		System.out.println(String.format("%1$-30s", sectionName));
		System.out.println("-".repeat(30));
	}

}