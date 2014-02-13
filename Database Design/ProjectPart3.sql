--> View releated queries
--> Question 1
 create view vw_Actor_revenue as
 select actor.actorname,count(actorname) as NumberOfmoviesacted,sum(vw_movie_revenue.ticket_sales_revenue) as total
 from actsin, actor,movie, vw_movie_revenue where
 actsin.actorid= actor.actorid and movie.movieid= actsin.movieid and movie.moviename=vw_movie_revenue.moviename
  group by actor.actorname;
    
select * from vw_actor_revenue;

-->Question 2
create view vw_Movie_revenue as
 Select movie.movieid,movie.moviename,sum(ticket.price) as ticket_sales_revenue from movie 
 left outer join playinginscreen on ( movie.movieid = playinginscreen.movieid)
 left outer join ticket on(playinginscreen.screenid=ticket.screenid
 and playinginscreen.theaterid = ticket.theaterid and playinginscreen.starttime =ticket.starttime )
 GROUP by (movie.movieid,movie.moviename);
  select * from vw_movie_revenue;

-->Question 3
select actorname , total from vw_actor_revenue;
  
-->PL/SQL related quries:
-->Question 1
SET SERVEROUTPUT ON;
DECLARE
  CURSOR c IS
    SELECT Staff.StaffId ,Staff.Staffname FROM staff, staffposition,position
    WHERE staff.staffid= staffposition.staffid AND staffposition.positionid= position.positionid
    AND position.positionname='SalesMan' AND staffposition.endingdate IS NULL;
 my_sid     staff.staffid%TYPE;
 my_sname   staff.staffname%TYPE;
  Tot_revenue NUMBER:=0;
BEGIN
   OPEN c;
    LOOP
    FETCH  c INTO  my_sid,my_sname;
    IF c%FOUND THEN  
     select sum(ticket.price) into Tot_revenue from ticket where ticket.staffid= my_sid;
       if Tot_revenue is null then tot_revenue:=0;
       end if;  
        DBMS_OUTPUT.PUT_LINE('Staff Name='|| my_sname|| ' Total_Revenue='|| Tot_revenue);
       else
     EXIT;
    END IF;
      END LOOP;
END;
/

-->Question 2
SET SERVEROUTPUT ON;
DECLARE
DirectorData Director%ROWTYPE;
BEGIN
SELECT * 
INTO DirectorData
FROM Director
WHERE DirectorId= 'd0000010';
dbms_output.put_line(' DirectorId: '||DirectorData.directorid );
dbms_output.put_line(' Directorname:'||DirectorData.directorname);
dbms_output.put_line(' DirectorGender:'||DirectorData.directorgender);
dbms_output.put_line(' Directordob:'||DirectorData.directordob);
END;
/
  
 -->Question 3:
SET SERVEROUTPUT ON;
CREATE OR REPLACE FUNCTION sf_getNumberOfDirectors(actor_id IN ActsIn.ActorId%TYPE)
RETURN NUMBER IS
dir_count NUMBER;
BEGIN
	SELECT DISTINCT(COUNT(DirectorId))
	INTO dir_count
	FROM Directs,ActsIn
	WHERE Directs.MovieId=ActsIn.MovieId AND ActsIn.ActorId=actor_id;
	RETURN dir_count;
	END;
/


-->Question 4:
CREATE TABLE tbl_DirectorSum(
ActorId CHAR(8) PRIMARY KEY,
ActorName VARCHAR2(100),
DOB DATE,
numberOfDirectors NUMBER,
foreign key(ActorId) references Actor(ActorId)
);

-->Question 5:
SET serveroutput ON;
CREATE OR REPLACE PROCEDURE sp_PopDirectorSum
IS
Actor_Id  Actor.ActorId%TYPE;
Actor_Name  Actor.ActorName%TYPE;
Actor_DOB  Actor.ActorDOB%TYPE;
number_of_director  number(3); 
CURSOR c IS SELECT ActorId FROM Actor;
BEGIN
OPEN c;
LOOP
fetch c INTO Actor_id ;
EXIT WHEN c%NOTFOUND;
SELECT ActorName,ActorDOB INTO Actor_Name,Actor_DOB FROM Actor
WHERE ActorId=Actor_Id ;
number_of_director := sf_getNumberOfDirectors(Actor_Id);
INSERT INTO tbl_DirectorSum(ActorId,ActorName,DOB,numberOfDirectors) 
VALUES (Actor_Id,Actor_Name,Actor_DOB,number_of_director);
END LOOP;
END;
/

-->Question 6:
DECLARE 
tbl tbl_DirectorSum%ROWTYPE;
CURSOR table_con IS SELECT * FROM tbl_DirectorSum;
BEGIN
sp_PopDirectorSum ();
BEGIN
OPEN table_con;
LOOP
FETCH table_con INTO tbl;
EXIT WHEN table_con%notfound;
dbms_output.put_line(tbl.actorid||'  '|| tbl.actorname||'  '|| tbl.dob||'  '|| tbl.numberOfDirectors);
END LOOP;
END;
END;
/


-->Question 7:
CREATE OR REPLACE PACKAGE pk_Movies AS
FUNCTION sf_getNumberOfDirectors(Actor_Id IN Actor.ActorId%TYPE) RETURN NUMBER;
PROCEDURE sp_PopDirectorSum;
END pk_Movies;
/
-->Package Body
CREATE OR REPLACE PACKAGE BODY pk_movies AS
FUNCTION sf_getNumberOfDirectors(Actor_Id in Actor.ActorId%TYPE) RETURN NUMBER IS
Total_Directors NUMBER;
BEGIN
SELECT DISTINCT(COUNT(DirectorId)) INTO Total_Directors 
FROM ActsIn ,Directs  
WHERE ActsIn.MovieId=Directs.MovieId AND ActsIn.ActorId=Actor_Id;
RETURN Total_Directors;
end;

PROCEDURE sp_PopDirectorSum
IS
var_actor_name Actor.ActorName%TYPE;
var_actor_id Actor.ActorId%TYPE;
var_dob Actor.ActorDOB%TYPE;
var_totaldir NUMBER(3);
CURSOR ActorId IS SELECT ActorId FROM Actor;
BEGIN
OPEN ActorId;
LOOP
FETCH ActorId INTO var_actor_id;
EXIT WHEN ActorId%notfound;
SELECT ActorName,ActorDOB INTO var_actor_name, var_dob 
FROM Actor 
WHERE ActorId= var_actor_id;
var_totaldir := sf_getnumberofdirectors(var_actor_id);
INSERT INTO tbl_DirectorSum(ActorId,ActorName, DOB, numberOfDirectors) 
VALUES (var_actor_id, var_actor_name, var_dob, var_totaldir);
END LOOP;
END;
END pk_movies;
/

-->Question 8:
-->Testing the package
SET serveroutput ON;
DECLARE
records tbl_directorsum%ROWTYPE;
BEGIN
pk_movies.sp_PopDirectorSum ();
DECLARE
CURSOR disp IS SELECT * FROM tbl_directorsum;
BEGIN
OPEN disp;
LOOP
FETCH disp 
INTO records;
EXIT WHEN disp%notfound;
dbms_output.put_line(records.ActorId||'  '|| records.ActorName||'   '|| records.DOB||'  '|| records.numberOfDirectors);
END LOOP;
END;
END;
/