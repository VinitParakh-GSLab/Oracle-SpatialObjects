Oracle-SpatialObjects
=====================

Introduction to Oracle Spatial objects and how to query maps

##About 
This project included USC map and saved each building as polygons in oracle. A java based application where in the user 
can draw random polygon and query the intersecting buildings. One can simulate a building to be on fire and query the 
nearest fire hydrant.

##Type of Queries
1. Whole Query :
Selects the shape object depending on the checkbox selected.
eg: If building is selected in the checkbox the following query is used : select v1.shape.GET_WKT() from vrp_buildings v1.

2. Point Query :
Selects a point where the user clicks on the map and finds the shape objects required based on the checkboxes selected using the following steps:
eg: Building is checked.
i. Selects the nearest building in the given point range(50) and then selects all other buildings except for this which are in the range and then colors
them according to the specifications given.
ii.Used: SDO_NN (Selects the nearest building), SDO_WITHIN_DISTANCE(Selects the buildings within the given distance).

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


##How to complie and run
1. Insert into tables 
javac populate.java
java -cp ./ojdbc6.jar;. populate buildings.xy students.xy announcementSystems.xy

2. Main Code 
javac -cp ./ojdbc6.jar;. hw2.java
java -cp ./ojdbc6.jar;. hw2
