# CS7340TeamOneLab4
Here is the project for CS7460 Lab 4 in Team One

## ReadMe Instructions for running Lab 4:

In the .zip file, there are two projects, backend and frontend, these must be open separately in IntelliJ.
If the project does not have the configuration set, select add configuration, add Play 2 App, insert the URL to open,
backend will be: http://localhost:9005, frontend will be: http://localhost:9001.

Once the configuration is added, the backend should be run. Before running the backend, ensure that the
application.conf file is properly setup in the Conf folder. Make sure these values are filled with proper information,
In the URL replace "test" with your DB name, and username, and password with your values.

db.default.url="jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
db.default.username=root
db.default.password=root

Now you can proceed with running the backend, this will load value into the database. After this you can run the frontend.
The frontend will present a "View Best Fit" button, press the button and the best fit solution will be presented along with
the calculated fitness value and route.

If you go back to run again wait approximately 10-15 seconds in between runs, or else the frontend will present null
as it takes a few seconds to repopulate after running calculation second time.

## Explain the fitness function, how to calculate.

Each chromosome represents different combinations for service clusters. And each service cluster
has several services. <br>
Furthermore, each services has four types of attributes:
- Cost
- Performance (Time)
- Availability
- Reliability.

So, the weight of QoS parameters in calculating fitness function:

- Cost (35%) <br>
- Reliability (10%)<br>
- Performance(Time) (20%)<br>
- Availability (35%)<br>

So we just need to add these attributes according to their different weights.

Therefore, the initial fitness function should be <br>
fitness = (fcost * 0.35) + (freliability * 0.1) + (ftime * 0.2) + (favailability * 0.35)

Also there are some other details in these attributes.
1. Because reliability and availability are between 0 an 1, so we need to do normalization which change cost and performance(time) into 0 and 1.<br>
   Normalization should be that we need to add each service cost or time together, then divide by three max cost or time in each service clusters<br>
   Also, if cost and time are higher, the fitness value should be smaller, because we do not want our cost and time higher comparing with other attributes.<br>
   Therefore, we need ot use (1 - cost) and (1 - time) to get the correct fitness value. <br>
   ~ fcost = 1 - {cost(server 1x) + cost(service 2x) + cost(service 3x)/ (MaxCost(SC1) +MaxCost(SC2)+ MaxCost(SC3))}; <br>
   ~ ftime = 1 - {time(server 1x) + time(service 2x) + time(service 3x)/ (MaxTime(SC1) +MaxTime(SC2)+ MaxTime(SC3))}; <br>

2. For reliability and availability, they are different from cost and time, we cannot add them together to get the fitness value for the two attributes.<br>
   Instead of that, we multiply each service reliability and availability together.  <br>
   ~ freliability = reliability(service 1x) * reliability(service 2x) * reliability(service 3x) <br>
   ~ favailability = availability(service 1x) * availability(service 2x) * availability(service 3x)


## Explain how to build the mapping from business question into the digital world in chromosomes and genes
For lab4 we have two path in this workflow:
- Path 1: SC1 -> SC2 -> SC3
- Path 2: SC1 -> SC3

So we decide to do the join merge which means that we will create two types of chromosome. Then we calculate two chromosome best fitness values.
Then According to the best fitness function value we will choose the better one as our solution.

And the chromosome structure is that each gene represent different service clusters and gene value represent the service number belonged to specific service cluster which is an integer.
For example,
- for path 1, the chromosome should be liked: |SC1|SC2|SC3| , the length of chromosome equals 3.
- for path 2, the chromosome should be liked: |SC1|SC3|, the length of chromosome equals2.

Then for each gene value, we will do the random initialization which set a random value for each gene.

Besides, the value of gene has a range between upper bound and lower bound which the range equals from 1 to the number of services in specific service cluster.
For example, for path 1, gene 1, the service cluster has 5 number of services, s11, s12, s13, s14 and s15.
Therefore, the range should equal with 1 to 5. Then pick the random value between 1 and 5, liked 3 which means we use service 13 as our first gene value in the initialization.

After that, we set the population size 20 and iteration time 50 which is the same configuration as JGAP package.

Then we get the result:

Total evolution time: 352 ms <br>
The best solution is path 2 sc1 -> sc3 which has a fitness value of 0.8234781483106626 <br>
It contains the following:<br>
service 11 .<br>
service 34 .<br>

which means that the best solution is path 2: SC1 -> SC3 which has the highest fitness value 0.8235 comparing with all other combinations in path 1 and 3.
And the solution picked service 11 and service 34 in this workflow.



