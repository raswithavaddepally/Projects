--> Question 1
SELECT Movie.MovieName , Movie.MovieReleaseDate
FROM Movie,ActsIn,Actor
WHERE Movie.MovieId = ActsIn.MovieId AND ActsIn.ActorId = Actor.ActorId AND Actor.ActorName ='Matt Damon';

--> Question 2
SELECT DISTINCT Actor.ActorName FROM Actor ,ActsIn 
WHERE Actor.ActorId = ANY (
				SELECT ActorId FROM ActsIn 
				WHERE ActorId =ANY (
					SELECT ActorId FROM ActsIn 
					GROUP BY ActorId
					HAVING( COUNT(ActorId)>1)));				

--> Question 3
SELECT DISTINCT Staff.StaffName, Staff.SSN
FROM Staff,StaffPosition
WHERE Staff.StaffId= ANY (SELECT DISTINCT SP1.StaffId 
					FROM StaffPosition SP1, StaffPosition SP2 
					WHERE SP1.StaffId=SP2.StaffId AND 
					SP1.TheaterId <> SP2.TheaterId);

-->Question 4
SELECT DISTINCT Staff.StaffName
FROM Staff,StaffPosition
WHERE Staff.StaffId NOT IN(SELECT DISTINCT StaffPosition.StaffId 
							FROM StaffPosition 
							WHERE StaffPosition.EndingDate IS  NULL  );

--> Question 5
--Assuming that only 'SalesMan' sells the tickets.
SELECT Staff.StaffName 
FROM Staff
WHERE Staff.StaffId NOT IN (SELECT Staff.StaffId FROM Staff, StaffPosition , Position  
							WHERE Staff.StaffId= StaffPosition.StaffId AND 
							StaffPosition.PositionId = Position.PositionId AND 
							Position.PositionName = 'SalesMan');
							
--> Question 6
SELECT DISTINCT Movie.MovieName FROM Movie,PlayingInScreen,Theater
WHERE Movie.MovieId= PlayingInScreen.MovieId AND 
Theater.TheaterID = PlayingInScreen.TheaterId AND 
Theater.TheaterName = 'Cinemark Allen 16';

-->Question 7
SELECT Actor.ActorName , Actor.ActorDOB
FROM Actor
WHERE Actor.ActorId NOT IN (SELECT Actor.ActorId 
							FROM Actor, ActsIn, Directs,Director 
							WHERE Actor.ActorId = ActsIn.ActorId AND 
							ActsIn.MovieId= Directs.MovieId AND 
							Directs.DirectorId = Director.DirectorId AND 
							Director.DirectorName='Brad Bird');		
--Question 8:
SELECT AVG(Salary)
FROM StaffSalary,Staff
Where Staff.StaffId=StaffSalary.StaffId AND Staff.StaffCity='Garland';

--Question 9:
SELECT Staff.StaffName,Theater.TheaterName
FROM Staff,Theater,StaffPosition
WHERE Staff.StaffId=StaffPosition.StaffId AND 
Theater.TheaterId=StaffPosition.TheaterID AND 
Staff.StaffPhoneNumber LIKE '(214%' AND StaffPosition.EndingDate IS NULL;

--Question 10:
SELECT Staff.StaffName FROM Staff,Theater,StaffPosition 
WHERE Staff.StaffId=StaffPosition.StaffId AND 
Theater.TheaterId = StaffPosition.TheaterId AND 
Staff.StaffCity = Theater.City AND 
StaffPosition.EndingDate IS NULL;