# Computation Description
The system will compute the factors of a given postiive integer. 
Eaxmple:
Input: 48
Output: 1, 2, 3, 4, 6, 8, 12, 16, 24, 48

# System Design Diagram
![System Design Diagram](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-Tarron/blob/main/System%20Diagram.png)

## Multi-Threading
The system uses multithreading in the network layer to handle multiple compute requests concurrently.
A fixed-size thread pool is used with a defined upper bound on the number of threads: 4
