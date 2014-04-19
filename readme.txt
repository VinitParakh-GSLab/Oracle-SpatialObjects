Name : Vinit Rajendra Parakh
Student id : 9283509659
_____________________________________________________________________________________________
The list of the submitted files:
1. dbConnection.java
2. emergencySystem.java
3. hw2.java
4. populate.java
5. RadioButtonToggle.java
6. announcementSystems.xy
7. buildings.xy
8. students.xy
9. map.jpg
10. ojdbc6.jar
11. readme.txt
12. createDB.sql
13. dropdb.sql
____________________________________________________________________________________________
Resolution of HomeWork:

P.S. : The code will work with ojdbc6.jar. It wasnt working with classes111.jar which was given with the project and 
       so I am attaching the jar along (Using Oracle 12c). 

1. Whole Query :
Selects the shape object depending on the checkbox selected.
eg: If building is selected in the checkbo the following query is used : select v1.shape.GET_WKT() from vrp_buildings v1.

2. Point Query :
Selects a point where the user clicks on the map and finds the shape objects required based on the checkboxes selected using the following steps:
eg: Building is checked.
i. Selects the nearest building in the given point range(50) and then selects all other buildings except for this which are in the range and then colors
them according to the specifications given.
ii.Used:  SDO_NN (Selects the nearest building), SDO_WITHIN_DISTANCE(Selects the buildings within the given distance).

3. Range Query:
Selects points to form a polygon and then returns the shapes depending upon the checkbox selected.
eg: Buildings is checked
i. Selects all the buildings that either lie within the polygon or intersect with the polygon.
ii. Used: SDO_ANYINTERACT (Selects buildings which either intersect or are within the given polygon)

4. Surrounding Student:
Selects the students which are within a range of a given Announcement System.
eg:
i. First finds the closest announcement system to the point given by the user using SDO_NN.
ii. Finds the students within this AS using the SDO_WITHIN_DISTANCE (radius of the AS)

5. Emergency Query:
Returns student who are affected by the broken emergency system and re-allocates them to the closest working emergency system.
eg:
1. Finds the AS which is broken using SDO_NN.
2. Selects students which are affected by the broken AS using SDO_WITHIN_DISTANCE.
3. Selects the nearest AS to the student which is not broken using SDO_NN and using 'sdo_batch_size=0' and ROWNUM <= 1. 
   This is done so as to get the first nearst AS to that student which is not broken. sdo_batch_size = 0 specifies that
   oracle would make its own decision to decide the size of the batch.
____________________________________________________________________________________________
How to compile/run them:

1. Insert into tables 
javac populate.java
java -cp ./ojdbc6.jar;. populate buildings.xy students.xy announcementSystems.xy

2. Main Code 
javac -cp ./ojdbc6.jar;. hw2.java
java -cp ./ojdbc6.jar;. hw2