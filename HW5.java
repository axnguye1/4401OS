/*
Name: Amanda Nguyen
CSCI 4401/5401
Spring 2024
Assignment 5

Due: Monday, 4/22 @ 11:59pm

- This assignment contains a total of 70 points.
- The problems you will solve all have ToDo: notes. 
- You can create new variables, but you cannot hardcode values. Rather than hardcoding values, use the global variables.
- The global variables and the arguments passed to the methods are example test values. You code should not be built to work for these specific values. I.e., it should still work if other test values were used. 
- Do not modify the current print statements. If you add print statements for testing & debugging, please remove them before submitting.
- Submit: this modified file containing your solutions.
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class HW5{ //don't rename
	/*Part I: Main Memory Variables */
	public static int[] memoryHoles = {10, 4, 22, 18, 7, 9, 12, 15}; //List of holes in memory for contiguous allocation
	public static int pageSize = (int) Math.pow(2, 10) * 4; //Page Size is 4KB
	public static int[][] pageTable =  { {0, 3}, {1, 4}, {2, 6}, {3, 2} }; //Page Table is (Page#, Frame#)
	public static int ptrSize = 2; //Pointer size is 2 bytes

	/*Part II: Virtual Memory Variables */
	public static int[] refString = {1, 2, 3, 4 ,1 ,2, 5, 1, 2, 3, 4, 5};

	/*Part III: File System Variables */
	public static int directPtrs = 20; //20 direct pointers


	/*Part IV: Mass Storage Variables */
	public static int[] requests = {86, 1470, 913, 1774, 948, 1509, 1022, 1750, 130};
	public static int max = 4999;
	public static int min = 0;
	


	public static void main(String args[]){
		part1test();
		part2test();
		part3test();
		part4test();
	}

	/********************************************************************************************
	Part I: Main Memory
		- contiguousAllocation()
		- paging()
		- addressMapping()
	********************************************************************************************/

	public static void part1test(){
		/*	This is for testing your code. 
			DO NOT modify this method
		*/
		
		printSectionHeader("Part I: Main Memory");

		//Test contiguousAllocation()
		System.out.println("contiguousAllocation() [5 points]");
		System.out.println(String.format("\t%1$12s%2$12s%3$12s%4$12s", "Request(MB)", "FF Slot(MB)", "BF Slot(MB)", "WF Slot(MB)"));
		contiguousAllocation(12);
		contiguousAllocation(10);
		contiguousAllocation(9);

		//Test paging()
		System.out.println("\npaging() [5 points]");
		System.out.println(String.format("\t%1$10s%2$8s%3$8s", "Address", "Page#", "Offset"));
		paging(10275);
		paging(30600);
		paging(36543);

		//Test addressMapping()
		System.out.println("\naddressMapping() [5 points]");
		System.out.println(String.format("\t%1$10s%2$8s%3$8s%4$17s", "Address", "Frame#", "Offset", "PhysicalAddress"));
		addressMapping(10275);
		addressMapping(8600);
		addressMapping(6500);		
	}

	public static void contiguousAllocation(int requestSize){
		/*	Consider a swapping system in which memory consists of the hole sizes @memoryHoles in memory. 
			Write code that determines what a segment request @requestSize will take for first-fit @firstFit, 
			best-fit @bestFit, and worst-fit @worstFit.
		*/
		int firstFit = 0;
		int bestFit = 0;
		int worstFit = 0;
		
		//ToDo: add your code to calculate @firstFit
		for (int i=0; i<memoryHoles.length; i++) {
			if ((requestSize <= memoryHoles[i]) && (firstFit == 0)) {
				firstFit = memoryHoles[i];
			}
		}
		//ToDo: add your code to calculate @bestFit
		int min = 0;
		int max = 0;

		for (int i=0; i<memoryHoles.length; i++) {
			if (requestSize <= memoryHoles[i]) {
				min = memoryHoles[i];
				if (bestFit == 0) { bestFit = min; }

				if (min < bestFit) {
					bestFit = min;
				}
		//ToDo: add your code to calculate @worsFit
				max = memoryHoles[i];
				if (max > worstFit) {
					worstFit = max;
				}
			}
		}
		System.out.println(String.format("\t%1$12s%2$12s%3$12s%4$12s", requestSize, firstFit, bestFit, worstFit)); //Do not modify		
	}

	public static void paging(int addrRef) {
		/*	Assuming a 4 KB (@pageSize), calculate the page number @pageNbr and offset @offset for a given 
			address reference @addrRef.
		*/
		int pageNbr = 0;
		int offset = 0;

		//ToDo: add your code to calculate the page number @pageNbr
		pageNbr = addrRef / pageSize;

		//ToDo: add your code to calculate the offset @offset
		offset = addrRef % pageSize;
		System.out.println(String.format("\t%1$10s%2$8s%3$8s", addrRef, pageNbr, offset));		
	}

	public static void addressMapping(int addr){
		/*	Assuming a 4 KB (@pageSize) and a frame size of 4 KB (@pageSize), calculate the frame number @frameNbr 
			and the physical address @phyAddr given a logical address @addr and the page table @pageTable
			for
		*/
		int pageNbr = 0;
		int offset = 0;
		int frameNbr = 0;
		int phyAddr = 0;

		//ToDo: add your code to calculate the frameNbr @frameNbr
		pageNbr = addr / pageSize;
		for (int[] page : pageTable) {
			if (page[0] == pageNbr) {
				frameNbr = page[1];
			}
		}
		//ToDo: add your code to calculate the offset @offset
		offset = addr % pageSize;

		//ToDo: add your code to calculate the physical address @phyAddr
		phyAddr = frameNbr * pageSize + offset;

		System.out.println(String.format("\t%1$10s%2$8s%3$8s%4$17s", addr, frameNbr, offset, phyAddr));		
	}

	/********************************************************************************************
	Part II: Virtual Memory
		- fifoPageReplacement()
		- lruPageReplacement()
	********************************************************************************************/

	public static void part2test(){
		printSectionHeader("Part II: Virtual Memory");


		//Test contiguousAllocation()
		System.out.println("fifoPageReplacement() [9 points]");
		fifoPageReplacement(3);
		fifoPageReplacement(4);
		System.out.println("lruPageReplacement() [9 points]");
		lruPageReplacement(3);
		lruPageReplacement(4);
	}


	public static void fifoPageReplacement(int frames) {
		/* Consider the sequence of page accesses @refString. Your system has @frames number of frames. Write the first-in first-out page replacement algorithm to calculate the number of page faults @faults that will occur.
		*/
		int faults = 0;
		
		//ToDo: add your code to calculate the faults @faults
		Queue<Integer> FIFOQueue = new LinkedList<Integer>();
		Set<Integer> mySet = new HashSet<Integer>();
		for (int page : refString) {
			if (!mySet.contains(page)) {
				faults++;
				if (FIFOQueue.size() == frames) {
					int removedPage = FIFOQueue.poll(); // remove the oldest page
					mySet.remove(removedPage);
				}
				FIFOQueue.add(page); // add the newest page to queue
				mySet.add(page);
			}
		}

		System.out.println("\tFIFO Faults: " + faults);
	}


	public static void lruPageReplacement(int frames) {
		/* Consider the sequence of page accesses @refString. Your system has @frames number of frames. Write the least recently used page replacement algorithm to calculate the number of page faults @faults that will occur.
		*/

		int faults = 0;

		//ToDo: add your code to calculate the faults @faults
		Set<Integer> pageSet = new HashSet<>();
		LinkedList<Integer> LRUQueue = new LinkedList<>();

		for (int page : refString) {
			if (!pageSet.contains(page)) {
				faults++;
				if (LRUQueue.size() == frames) {
					int removedPage = LRUQueue.removeFirst();
					pageSet.remove(removedPage);
				}
			} else {
				LRUQueue.remove(Integer.valueOf(page));
			}
			LRUQueue.addLast(page);
			pageSet.add(page);
		}
		System.out.println("\tLRU Faults: " + faults);
	}

	/********************************************************************************************
	Part III: File Systems
		- inode1()
		- inode2()
	********************************************************************************************/

	public static void part3test(){
		printSectionHeader("Part III: File Systems");

		System.out.println("inode1() [5 points]");
		inode1();
		System.out.println("inode2() [5 points]");
		inode2();
	}

	public static void inode1(){
		/* Assuming a 4 KB disk block size (@pageSize), calculate the largest
		file size @fileSize for an i-node that contains 20 direct pointers @directPtrs
		and one single indirect pointer. Make sure to convert your file size to GB.*/

		double fileSize = 0;

		//ToDo: calculate the max file size @fileSize
		fileSize = fileSize + directPtrs * pageSize;
		fileSize = fileSize + (pageSize / ptrSize) * pageSize;

		double convertSize = fileSize / Math.pow(1024, 3);
		fileSize = convertSize;

		System.out.println(String.format("\tI-Node 1 File Size: %,.2f GB", fileSize));
	}

	public static void inode2(){
		/* Assuming a 4 KB disk block size (@pageSize), calculate the largest file
		size @fileSize for an i-node that contains 20 direct pointers @directPtrs, one single indirect pointer,
		and one double indirect pointer. Make sure to convert your file size to GB.*/

		double fileSize = 0;

		//ToDo: calculate the max file size @fileSize
		fileSize = fileSize + directPtrs * pageSize;
		fileSize = fileSize + (pageSize / ptrSize) * pageSize;
		fileSize = fileSize + Math.pow((pageSize/ptrSize), 2) * pageSize;

		double convertSize = fileSize / Math.pow(1024, 3);
		fileSize = convertSize;

		System.out.println(String.format("\tI-Node 2 File Size: %,.2f GB", fileSize));
	}

	/********************************************************************************************
	Part IV: Mass Storage
		- fcfsScheduling()
		- scanScheduling()
		- lookScheduling()
		- sstfScheduling()
	********************************************************************************************/

	public static void part4test(){
		printSectionHeader("Part IV: Disk Scheduling");

		//Test fcfsScheduling()
		System.out.println("fcfsScheduling() [6 points]");
		fcfsScheduling(143);

		//Test scanScheduling()
		System.out.println("scanScheduling() [6 points]");
		scanScheduling(143);

		//Test lookScheduling()
		System.out.println("lookScheduling() [6 points]");
		lookScheduling(143);

		//Test sstfScheduling()
		System.out.println("sstfScheduling() [9 points]");
		sstfScheduling(143);
	}

	public static void fcfsScheduling(int start){
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for first-come first-serve @fcfsMovements.
		*/

		int fcfsMovements = 0;

		//ToDo: add your code to calculate @fcfsMovements
		int current = start;

		for (int request : requests) {
			fcfsMovements = fcfsMovements + Math.abs(request - current);
			current = request;
		}


		System.out.println(String.format("\tFCFS Movements: %s", fcfsMovements));
	}


	public static void scanScheduling(int start){
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for the SCAN algorithm @scanMovements.
		*/		

		int scanMovements = 0;					
		
		//ToDo: add your code to calculate @scanMovements
		String direction = "right";

		List<Integer> sortedRequests = new ArrayList<>();
		for (int request : requests) {
			sortedRequests.add(request);
		}

		Vector<Integer> left = new Vector<Integer>(), right = new Vector<Integer>(); //track left and right
		Vector<Integer> seekSequence = new Vector<Integer>();

		if (direction.equals("left")) {
			left.add(0);
		} else if (direction.equals("right")) {
			right.add(4999);
		}

		for (int i = 0; i < sortedRequests.size(); i++) { //if less than start, then left side, if greater than then right side.
			if (sortedRequests.get(i) < start) {
				left.add(sortedRequests.get(i));
			}
			if (sortedRequests.get(i) > start) {
				right.add(sortedRequests.get(i));
			}
		}

		Collections.sort(left);
		Collections.sort(right);

		//go through twice to scan left and right
		int run = 2;
		while (run-- > 0) {
			if (direction.equals("left")) {
				for (int i = left.size() - 1; i >= 0; i--) {
					int currentTrack = left.get(i);
					seekSequence.add(currentTrack);
					scanMovements += Math.abs(currentTrack - start);
					start = currentTrack;
				}
				direction = "right";
			} else if (direction.equals("right")) { //2nd run through to scan
				for (int i = 0; i < right.size(); i++) {
					int currentTrack = right.get(i);
					seekSequence.add(currentTrack);
					scanMovements += Math.abs(currentTrack - start);
					start = currentTrack;
				}
				direction = "left";
			}
		}

		System.out.println(String.format("\tSCAN Movements: %s", scanMovements));
	}

	public static void lookScheduling(int start){
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for the LOOK algorithm @lookMovements.
		*/		
		int lookMovements = 0;	

		//ToDo: add your code to calculate @lookMovements
		List<Integer> sortedRequests = new ArrayList<>();
		for (int request : requests) {
			sortedRequests.add(request);
		}

		Vector<Integer> left = new Vector<Integer>(), right = new Vector<Integer>(); //store left and right
		Vector<Integer> seekSequence = new Vector<Integer>();

		for (int i = 0; i < sortedRequests.size(); i++) { //organize the lefts and rights
			if (sortedRequests.get(i) < start) {
				left.add(sortedRequests.get(i));
			}
			if (sortedRequests.get(i) > start) {
				right.add(sortedRequests.get(i));
			}
		}

		Collections.sort(left);
		Collections.sort(right);

		int run = 2; //scan twice for left and right
		while (run-- > 0) {
			if (left.size() > 0) {
				for (int i = left.size() - 1; i >= 0; i--) {
					int currentTrack = left.get(i);
					seekSequence.add(currentTrack);
					lookMovements += Math.abs(currentTrack - start);
					start = currentTrack;
				}
			}

			if (right.size() > 0) {
				for (int i = 0; i < right.size(); i++) {
					int currentTrack = right.get(i);
					seekSequence.add(currentTrack);
					lookMovements += Math.abs(currentTrack - start);
					start = currentTrack;
				}
			}
		}
		System.out.println(String.format("\tLOOK Movements: %s", lookMovements));
		
	}

	public static void sstfScheduling(int start) {
		/*	Suppose a disk drive has 5000 cylinders numbered 0 – 4999. The drive is currently serving a request 
			at cylinder @start, and the previous request was at cylinder 125. The queue of pending requests, in FIFO order, is @requests. Starting from the current head position, calculate the total distance 
			(in cylinders) that the disk arm moves to satisfy all the pending requests for the Shortest-seek-time-first algorithm @sstfMovements.
		*/
	
		int sstfMovements = 0;
		
		//ToDo: add your code to calculate @sstfMovements
		List<Integer> sortedRequests = new ArrayList<>();
		for (int request : requests) {
			sortedRequests.add(request);
		}
		sortedRequests.add(125);
		Collections.sort(sortedRequests);

		int current = start;
		int direction = 1; // 1 ^, -1 v

		while (!sortedRequests.isEmpty()) {
			for (int i = 0; i < sortedRequests.size(); i++) {
				int nextRequest = sortedRequests.get(i);
				int distance = Math.abs(nextRequest - current);
				sstfMovements += distance;
				current = nextRequest;
				sortedRequests.remove(i);
				i--;
			}
			direction = -direction; // change direction
		}
		System.out.println(String.format("\tSSTF Movements: %s", sstfMovements));
	}


	public static void printSectionHeader(String sectionName) {
		System.out.println("\n" + "-".repeat(25));
		System.out.println(String.format("%1$-25s", sectionName));
		System.out.println("-".repeat(25));
	}
}