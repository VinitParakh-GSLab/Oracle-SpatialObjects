create table vrp_announcement (
announcement_name varchar2(50) PRIMARY KEY,
shape SDO_GEOMETRY,
announcement_radius number,
xcenter NUMBER(5) ,
ycenter NUMBER(5) ,
as_point_shape SDO_GEOMETRY
);
 
INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'vrp_announcement',
  'shape',
  SDO_DIM_ARRAY( 
    SDO_DIM_ELEMENT('X', 0, 20, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 20, 0.005)
     ),
  NULL 
);
 
CREATE INDEX index_announcement
   ON vrp_announcement(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
 


INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'vrp_announcement',
  'as_point_shape',
  SDO_DIM_ARRAY(
    SDO_DIM_ELEMENT('X', 0, 20, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 20, 0.005)
     ),
  NULL 
);

CREATE INDEX index_announcement2
   ON vrp_announcement(as_point_shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;

   
CREATE TABLE vrp_buildings (
  building_id varchar2(5) PRIMARY KEY,
  building_name varchar2(50),
  coord_count number,
  shape SDO_GEOMETRY);
 
INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'vrp_buildings',
  'shape',
  SDO_DIM_ARRAY(  
    SDO_DIM_ELEMENT('X', 0, 20, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 20, 0.005)
     ),
  NULL
);


CREATE INDEX index_building
   ON vrp_buildings(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;
 
CREATE TABLE vrp_students (
  studentID varchar2(5) PRIMARY KEY,
  shape SDO_GEOMETRY);
  
INSERT INTO user_sdo_geom_metadata
    (TABLE_NAME,
     COLUMN_NAME,
     DIMINFO,
     SRID)
  VALUES (
  'vrp_students',
  'shape',
  SDO_DIM_ARRAY(
    SDO_DIM_ELEMENT('X', 0, 20, 0.005),
    SDO_DIM_ELEMENT('Y', 0, 20, 0.005)
     ),
  NULL
);

CREATE INDEX index_students
   ON vrp_students(shape)
   INDEXTYPE IS MDSYS.SPATIAL_INDEX;