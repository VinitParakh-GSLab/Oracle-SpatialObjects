DELETE FROM  user_sdo_geom_metadata where table_name in ('VRP_ANNOUNCEMENT','VRP_BUILDINGS','VRP_STUDENTS');

drop index index_announcement;
drop index index_announcement2;
drop index index_building;
drop index index_students;

drop table vrp_buildings;
drop table vrp_students;
drop table vrp_announcement;