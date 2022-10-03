# Operating Systems Process Scheduling Algorithms
This repo contains the different algorithms found in computer operating system process scheduling.

## How it works:
This program reads in a file and solves the process scheduling algorithm with the algorithm and data defined on the file. 
This program uses a directory path string as parameter run arguments. Check and update the runtime arguments prior to executing this program (jGRASP is an IDE that makes this process easy).

### File Input format:
    algorithm_name (see list below)
    process numbers (spaces must be between processes to separate entries)
    arrival times (spaces must be between processes to separate entries)
    burst time (spaces must be between processes to separate entries)

Algorithm Abbreviations: (This program supports the following algorithms)
  * First Come First Serve -> FCFS
  * non-preemptive priority -> NPP
  * preemptive priority -> PP
  * shortest job first -> SJF
  * round-robin -> RR
  * shortest time remaining next -> STRN

Program Console Output Format:
  * print contents of the file
  * print the parsed data from the processes with all of the unknown variables solved (finish time, turn around time, etc.)
  * print the Gantt chart out  


### Instructions for jGRASP:
load both Process.java and ProcessScheduling.java into jGRASP, then in the "Build" Ribbon 




# Execution Screenshots:

* First Come First Serve:

Console Output From [fcfs1.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/fcfs1.txt)
![image](https://user-images.githubusercontent.com/100094056/193623007-d0dbf79f-2501-4002-8bcf-90660915fc78.png)

Console Output From [fcfs2.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/fcfs2.txt)
![image](https://user-images.githubusercontent.com/100094056/193624142-7427e020-b4d3-432a-80d4-8d113f6245b4.png)

* Non-Preemptive Priority:

Console Output From [npp1.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/npp1.txt)
![image](https://user-images.githubusercontent.com/100094056/193629551-9733d82f-71dc-41ef-9628-de820df358bb.png)

Console Output From [nnp2.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/npp2.txt)
![image](https://user-images.githubusercontent.com/100094056/193629915-a22dcc83-85cb-4e71-8ae9-c986464b31b2.png)

* Preemptive Priority

Console Output From [pp1.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/pp1.txt)
![image](https://user-images.githubusercontent.com/100094056/193630088-ec01cdfd-4351-46aa-93db-ae3d6811c64a.png)

Console Output From [pp2.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/pp2.txt)
![image](https://user-images.githubusercontent.com/100094056/193630308-d56d5c6f-cb33-44ce-afd8-63911f212d4f.png)

* Shortest Job First:

Console Output From [sjf.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/sjf.txt)
![image](https://user-images.githubusercontent.com/100094056/193630611-856da4f9-14da-425d-8468-2a3d8884df82.png)

* Round Robin:

Console Output From [rr1.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/rr1.txt)
![image](https://user-images.githubusercontent.com/100094056/193630819-c060e1e8-21c9-4a9c-914b-da6326c5286f.png)

Console Output From [rr2.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/rr2.txt)
![image](https://user-images.githubusercontent.com/100094056/193630964-c038d914-718a-4420-a771-3c723bca8ad0.png)

* Shortest Remaining Time Next: 

Console Output From [srtn1.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/srtn1.txt)
![image](https://user-images.githubusercontent.com/100094056/193631243-2e38d909-6f9d-497d-9280-9440bdb01735.png)

Console Output From [srtn2.txt](https://github.com/Austin-Daigle/Process-Scheduling-Algorithms/blob/main/example%20data/srtn2.txt)
![image](https://user-images.githubusercontent.com/100094056/193631384-427ced98-19b1-42b1-a103-c0e25c45d12b.png)
