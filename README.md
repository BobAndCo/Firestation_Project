# Firestation_Project

This is a project for our grade 12 AP Computer Science class, and the topics it covers are algorithms, time complexities and optimization.

## Scenario 

City council is working on a plan for fire protection.
The council needs to decide where to build fire stations, in order to protect all communities within the city
boundaries. A community is considered covered if there is a fire station within the community, or if there is a
station in one of the neighbouring communities.
You job is to pinpoint the communities where those stations should be located, given a map of the communities
and the connections among them.
Since the budget is limited, the aim is to designate the minimum number of locations, such that all communities
are protected, as per the description above.

## Task
Write a program that takes as an input the “map” of the communities with the connections, and outputs a “map”
with highlighted communities where the fire stations should be located.

* The input should be provided in a format that is human readable but also convenient for parsing into a
chosen data structure.
* The output should be given in a human readable form, ideally visual or at least similar to the given input.

## Sample Solution

Here is an example of communities’ layout, with a solution of the problem as described:

### How we layout the city

We number the city starting from 0 - n and then we add a '-' after which we add all the cities that it it connected to seperated by a comma and a space. For example:

0 - 1 </br>
1 - 1, 2, 4 </br>
2 - 1, 3 </br>
3 - 2, 4 </br>
4 - 1, 3 </br>

![](images/sampleCity.jpg)
