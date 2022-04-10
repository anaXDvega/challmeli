create table distance (geoname_id integer not null, city varchar(255), country varchar(255), distance double, invocations integer, primary key (geoname_id));
insert into distance (city, country, distance, invocations, geoname_id)values('cucuta', 'colombia', '55.333333', '3', '5555555');
insert into distance (city, country, distance, invocations, geoname_id)values('santa marta', 'colombia', '55.3335555333', '3', '5558555');
insert into distance (city, country, distance, invocations, geoname_id)values('bogota', 'colombia', '45.333333', '2', '5555455');