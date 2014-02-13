spool cs-6360-assignment-1b.txt;
DELETE FROM ActsIn;
DELETE FROM Actor;
DELETE FROM Directs;
DELETE FROM Director;
DELETE FROM Ticket; 
DELETE FROM PlayingInScreen;
DELETE FROM Movie;
DELETE FROM Screens;
DELETE FROM StaffPosition;
DELETE FROM StaffSalary;
DELETE FROM Position;
DELETE FROM Staff;
DELETE FROM Theater;

DROP TABLE ActsIn;
DROP TABLE Actor;
DROP TABLE Directs;
DROP TABLE Director;
DROP TABLE Ticket; 
DROP TABLE PlayingInScreen;
DROP TABLE Movie;
DROP TABLE Screens;
DROP TABLE StaffPosition;
DROP TABLE StaffSalary;
DROP TABLE Position;
DROP TABLE Staff;
DROP TABLE Theater;

CREATE TABLE Movie (
    MovieId	char(8) Primary Key,
    MovieName varchar2(100) Not Null,
    MovieDescription varchar2(1024) Not Null,
    MovieMPAARating	varchar2(5) Not Null,
    MovieReleaseDate Date Not Null,
    MovieLength Number Not Null
    );

CREATE TABLE Actor (
    ActorId char(8) Primary Key,
    ActorName varchar2(100) Not Null,
    ActorGender char Not Null,
    ActorDOB Date Not Null,
    CONSTRAINT act_gen_ch CHECK (ActorGender IN('M','F'))
    );
    
CREATE TABLE ActsIn ( 
    ActorId char(8),
    MovieId char(8),
    CONSTRAINT actin_pk Primary Key (ActorId, MovieId),
    CONSTRAINT actin_fk_act Foreign Key (ActorId) References Actor(ActorId),
    CONSTRAINT actin_fk_mov Foreign Key (MovieId) References Movie(MovieId)
    );

CREATE TABLE Director (
    DirectorId char(8) Primary Key,
    DirectorName varchar2(100) Not Null,
    DirectorGender char Not Null,
    DirectorDOB Date,
    CONSTRAINT dir_gen_ch CHECK (DirectorGender IN('M','F'))
    );        
    
CREATE TABLE Directs ( 
    DirectorId char(8),
    MovieId char(8),
    CONSTRAINT direct_pk Primary Key (DirectorId, MovieId),
    CONSTRAINT direct_fk_dir Foreign Key (DirectorId) References Director(DirectorId),
    CONSTRAINT direct_fk_mov Foreign Key (MovieId) References Movie(MovieId)
    ); 
    
CREATE TABLE Theater ( 
    TheaterId	char(8)	Primary Key,
    TheaterName	Varchar2(40) Not Null,
    StreetAddress	Varchar2(100)	Not Null,
    City	Varchar2(40)	Not Null,
    State	Varchar2 (2)	Not Null,
    ZipCode	Varchar2 (5)	Not Null
    );

CREATE TABLE Screens (	
    ScreenId	char(8),	
    TheaterId	char(8),
    ScreenName	Varchar2(40)	Not Null,	
    ScreenSeatingCapacity	Int	Not Null,
    CONSTRAINT scr_pk Primary Key (ScreenId, TheaterId),
    CONSTRAINT scr_fk_theater Foreign Key (TheaterId) References Theater(TheaterId),
    CONSTRAINT scr_stcp_ch CHECK (ScreenSeatingCapacity > 0)
    );
    
CREATE TABLE PlayingInScreen (
    ScreenId	char(8),	
    TheaterId	char(8),
    MovieId	    char(8),
    StartTime	Date	Not Null,
    CONSTRAINT pinsc_pk Primary Key (ScreenId, TheaterId,StartTime),
    CONSTRAINT ps_fk_mov Foreign Key (MovieId) References Movie(MovieId),
    CONSTRAINT ps_fk_screen Foreign Key (ScreenId,TheaterId) References Screens(ScreenId,TheaterId)
    );
            
CREATE TABLE Staff (	
    StaffId	char(8)	Primary Key,
    StaffName	Varchar2(40),	
    StaffStreetAddress	Varchar2(100),	
    StaffCity	Varchar2(20),	
    StaffState	Varchar2(2),	
    StaffZipCode	Varchar2(5),	
    StaffPhoneNumber	Varchar2(16),	
    StaffEmail	Varchar2(40),
    Gender	Char,
    SSN	Varchar2(9)	Not Null,
    DOB	Date	Not Null,
    CONSTRAINT staff_gen_ch CHECK (Gender IN('M','F'))
);

CREATE TABLE Ticket (				
    TicketId	char(8),
    TheaterId	char(8),
    ScreenId	char(8),
    StartTime	Date	Not Null,	
    Price	Number	Not Null,	
    StaffId	char(8),
    SellingTimestamp    date,
    CONSTRAINT ticket_pk Primary Key (TicketId, ScreenId, TheaterId),
    CONSTRAINT ticket_fk_screen Foreign Key (ScreenId,TheaterId,StartTime) References PlayingInScreen(ScreenId,TheaterId,StartTime),
    CONSTRAINT ticket_fk_stf Foreign Key (StaffId) References Staff(StaffId)
    );	
        
CREATE TABLE Position ( 		
    PositionId	char(8)	Primary Key,
    PositionName	Varchar2(20)	Not Null
    );

CREATE TABLE StaffPosition (
    StaffId	char(8),
    PositionId	char(8),
    theaterId	char(8),
    StartingDate	Date Not Null,
    EndingDate Date,
    CONSTRAINT stpos_pk Primary Key (StaffId, PositionId, theaterId,StartingDate),
    CONSTRAINT stpos_fk_pos Foreign Key (PositionId) References Position(PositionId),
    CONSTRAINT stpos_fk_thr Foreign Key (StaffId) References Staff(StaffId),
    CONSTRAINT stpos_fk_stf Foreign Key (TheaterId) References Theater(TheaterId)
    );
    
CREATE TABLE StaffSalary (
    StaffId	char(8),
    PositionId	char(8),
    Salary	Number Not Null,
    EffectiveDate	Date 	Not Null,
    CONSTRAINT stsal_pk Primary Key (StaffId, PositionId,EffectiveDate),
	CONSTRAINT stsal_fk_pos Foreign Key (PositionId) References Position(PositionId),
    CONSTRAINT stsal_fk_stf Foreign Key (StaffId) References Staff(StaffId),
    CONSTRAINT stsal_sal_ch CHECK (Salary > 0)
    );
    
INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength) 
VALUES ('mv000001', 'The Godfather', 'The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.', 'R', to_date('24 March 1972', 'dd Month YYYY'), 175);

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength) 
VALUES('mv000002', 'The Da Vinci Code', 'A murder inside the Louvre and clues in Da Vinci paintings lead to the discovery of a religious mystery protected by a secret society for two thousand years -- which could shake the foundations of Christianity.', 'PG-13', to_date('19 May 2006', 'dd Month YYYY'), 149);

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000003', 'The Godfather: Part II', 'The early life &'||' career of Vito Corleone in 1920''s New York is portrayed while his son, Michael, expands and tightens his grip on his crime syndicate stretching from Lake Tahoe, Nevada to pre-Revolution 1958 Cuba.', 'R', to_date('20 December 1974', 'dd Month YYYY'), 200); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000004', 'Buono, il brutto, il cattivo, Il', 'Three gunmen set out to find a hidden fortune. Who will walk away with the cash?', 'R', to_date('29 December 1967', 'dd Month YYYY'), 161); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000005', 'Forrest Gump', 'Forrest Gump, while not intelligent, has accidentally been present at many historic moments, but his true love, Jenny, eludes him.', 'PG-13', to_date('6 July 1994', 'dd Month YYYY'), 142); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000006', 'Ratatouille', 'Remy is a young rat in the French countryside who arrives in Paris, only to find out that his cooking idol is dead. When he makes an unusual alliance with a restaurant''s new garbage boy, the culinary and personal adventures begin despite Remy''s family''s skepticism and the rat-hating world of humans.', 'G', to_date('29 June 2007', 'dd Month YYYY'), 110); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000007', 'The Incredibles', 'A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.', 'PG', to_date('5 November 2004', 'dd Month YYYY'), 115); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000008', 'Finding Nemo', 'A father-son underwater adventure featuring Nemo, a boy clownfish, stolen from his coral reef home. His timid father must then travel to Sydney, and search Sydney Harbour find Nemo.', 'G', to_date('30 May 2003', 'dd Month YYYY'), 100); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000009', 'Toy Story', 'A cowboy toy is profoundly threatened and jealous when a fancy spaceman toy supplants him as top toy in a boy''s room.', 'G', to_date('22 November 1995', 'dd Month YYYY'), 81); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000010', 'Shrek', 'An ogre, in order to regain his swamp, travels along with an annoying donkey in order to bring a princess to a scheming lord, wishing himself King.', 'PG', to_date('18 May 2001', 'dd Month YYYY'), 90); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000011', 'Minority Report', 'In the future, criminals are caught before the crimes they commit, but one of the officers in the special unit is accused of one such crime and sets out to prove his innocence.', 'PG-13', to_date('21 June 2002', 'dd Month YYYY'), 145); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000012', 'The Bourne Ultimatum', 'Bourne dodges new, superior assassins as he searches for his unknown past while a government agent tries to track him down.', 'PG-13', to_date('3 August 2007', 'dd Month YYYY'), 111); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000013', 'Jurassic Park', 'Scientists clone dinosaurs to populate a theme park which suffers a major security breakdown and releases the dinosaurs.', 'PG-13', to_date('11 June 1993', 'dd Month YYYY'), 127); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000014', 'The Matrix', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against the controllers of it.', 'R', to_date('31 March 1999', 'dd Month YYYY'), 136); 

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000015', 'Aliens', 'The planet from Alien (1979) has been colonized, but contact is lost. This time, the rescue team has impressive firepower, enough?', 'R', to_date('18 July 1986', 'dd Month YYYY'), 137);

INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000016', 'Ocean''s Eleven', 'Danny Ocean and his ten accomplices plan to rob three Las Vegas casinos simultaneously.', 'PG-13', to_date('7 December 2001', 'dd Month YYYY'), 116); 
 
INSERT INTO Movie(MovieId, MovieName, MovieDescription, MovieMPAARating, MovieReleaseDate, MovieLength)
VALUES ('mv000017', 'Terminator 2: Judgment Day', 'A shape-shifting cyborg is sent back from the future to kill John Connor before he can grow up to lead the resistance; a protector is sent, too.', 'R', to_date('3 July 1991', 'dd Month YYYY'), 137); 

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000001','Tom Cruise','M',to_date('3 July 1962', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000002','Samantha Morton','F',to_date('13 May 1977', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000003','Max von Sydow','M',to_date('10 April 1929', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000004','Arnold Schwarzenegger','M',to_date('30 July 1947', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000005','Linda Hamilton','F',to_date('26 September 1956', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000006','George Clooney','M',to_date('6 May 1961', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000007','Brad Pitt','M',to_date('18 December 1963', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000008','Julia Roberts','F',to_date('28 October 1967', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000009','Sigourney Weaver','F',to_date('8 October 1949', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000010','Michael Biehn','M',to_date('31 July 1956', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000011','Keanu Reeves','M',to_date('2 September 1964', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000012','Carrie-Anne Moss','F',to_date('21 August 1967', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000013','Matt Damon','M',to_date('8 October 1970', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000014','Julia Stiles','F',to_date('28 March 1981', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000015','Sam Neill','M',to_date('14 September 1947', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000016','Laura Dern','F',to_date('10 February 1967', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000017','Cameron Diaz','F',to_date('30 August 1972', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000018','Eddie Murphy','M',to_date('3 April 1961', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000019','Tom Hanks','M',to_date('9 July 1956', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000020','Robin Wright Penn','F',to_date('8 April 1966', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000021','Albert Brooks','M',to_date('22 July 1947', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000022','Samuel L. Jackson','M',to_date('21 December 1948', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000023','Holly Hunter','F',to_date('20 March 1958', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000024','Patton Oswalt','M',to_date('27 January 1969', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000025','Brian Dennehy','M',to_date('9 July 1938', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000026','Marlon Brando','M',to_date('3 April 1924', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000027','Al Pacino','M',to_date('25 April 1940', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000028','Diane Keaton','F',to_date('5 January 1946', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000029','Eli Wallach','M',to_date('7 December 1915', 'dd Month YYYY'));

INSERT INTO Actor(ActorId, ActorName, ActorGender, ActorDOB)
VALUES ('ac000030','Clint Eastwood','M',to_date('31 May 1930', 'dd Month YYYY'));

INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000001','mv000011');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000002','mv000011');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000003','mv000011');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000017','mv000011');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000004','mv000017');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000005','mv000017');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000006','mv000016');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000007','mv000016');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000008','mv000016');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000013','mv000016');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000009','mv000015');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000010','mv000015');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000011','mv000014');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000012','mv000014');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000013','mv000012');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000014','mv000012');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000015','mv000013');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000016','mv000013');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000017','mv000010');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000018','mv000010');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000019','mv000009');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000019','mv000002');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000019','mv000005');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000020','mv000005');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000021','mv000008');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000022','mv000007');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000023','mv000007');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000024','mv000006');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000025','mv000006');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000026','mv000001');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000027','mv000001');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000027','mv000003');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000028','mv000003');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000029','mv000004');
INSERT INTO ActsIn(ActorId, MovieId) VALUES('ac000030','mv000004');

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000001','Steven Spielberg','M',to_date('18 December 1946', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000002','James Cameron','M',to_date('16 August 1954', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000003','Andy Wachowski','M',to_date('29 December 1967', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000004','Larry Wachowski','M',to_date('21 June 1965', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000005','Steven Soderbergh','M',to_date('14 January 1963', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000015','Paul Greengrass','M',to_date('13 August 1955', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender,DirectorDOB)
VALUES('d0000006','Andrew Adamson','M',to_date('1 December 1966', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender)
VALUES('d0000007','Vicky Jenson','F');

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000008','John Lasseter','M',to_date('12 January 1957', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000009','Ron Howard','M',to_date('1 March 1954', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000010','Robert Zemeckis','M',to_date('14 May 1952', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000011','Andrew Stanton','M',to_date('3 December 1965', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000012','Brad Bird','M',to_date('11 September 1957', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000013','Francis Ford Coppola','M',to_date('7 April 1939', 'dd Month YYYY'));

INSERT INTO Director(DirectorId,DirectorName,DirectorGender, DirectorDOB)
VALUES('d0000014','Sergio Leone','M',to_date('3 January 1929', 'dd Month YYYY'));

INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000013','mv000001');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000009','mv000002');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000013','mv000003');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000014','mv000004');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000010','mv000005');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000012','mv000006');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000012','mv000007');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000011','mv000008');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000008','mv000009');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000006','mv000010');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000007','mv000010');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000001','mv000011');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000005','mv000012');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000001','mv000013');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000003','mv000014');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000004','mv000014');		
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000002','mv000015');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000005','mv000016');
INSERT INTO Directs(DirectorId,MovieId) VALUES('d0000002','mv000017');

INSERT INTO Theater(TheaterId,TheaterName,StreetAddress,City,State,ZipCode)
VALUES ('th000001','Cinemark Allen 16','921 SH 121','Allen','TX','75013');

INSERT INTO Theater(TheaterId,TheaterName,StreetAddress,City,State,ZipCode)
VALUES ('th000002','Cinemark 17 and IMAX® Theatre','11819 Webb Chapel','Dallas','TX','75234');

INSERT INTO Screens(ScreenId,TheaterId,ScreenName,ScreenSeatingCapacity)
VALUES('sc000001','th000001','Screen1',50);
INSERT INTO Screens(ScreenId,TheaterId,ScreenName,ScreenSeatingCapacity)
VALUES('sc000002','th000001','Screen2',40);
INSERT INTO Screens(ScreenId,TheaterId,ScreenName,ScreenSeatingCapacity)
VALUES('sc000003','th000001','Screen3',60);

INSERT INTO Screens(ScreenId,TheaterId,ScreenName,ScreenSeatingCapacity)
VALUES('sc000001','th000002','Screen1',50);
INSERT INTO Screens(ScreenId,TheaterId,ScreenName,ScreenSeatingCapacity)
VALUES('sc000002','th000002','Screen2',40);
INSERT INTO Screens(ScreenId,TheaterId,ScreenName,ScreenSeatingCapacity)
VALUES('sc000003','th000002','Screen3',60);

INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000001','mv000001', to_date('1:10PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000001','mv000001', to_date('4:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000001','mv000001', to_date('7:50PM', 'HH:MIAM'));

INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000001','mv000002', to_date('12:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000001','mv000002', to_date('3:20PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000001','mv000002', to_date('6:10PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000001','mv000002', to_date('9:00PM', 'HH:MIAM'));

INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000001','mv000005', to_date('12:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000001','mv000005', to_date('3:20PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000001','mv000005', to_date('6:10PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000001','mv000005', to_date('9:00PM', 'HH:MIAM'));

INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000002','mv000001', to_date('12:00PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000002','mv000001', to_date('2:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000002','mv000001', to_date('5:00PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000002','mv000001', to_date('7:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000001','th000002','mv000001', to_date('10:00PM', 'HH:MIAM'));

INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('12:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('2:10PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('3:50PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('4:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('6:20PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('8:10PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000002','th000002','mv000009', to_date('10:00PM', 'HH:MIAM'));

INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000002','mv000015', to_date('12:30PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000002','mv000015', to_date('3:20PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000002','mv000015', to_date('6:10PM', 'HH:MIAM'));
INSERT INTO PlayingInScreen(ScreenId,TheaterId,MovieId,StartTime)
VALUES('sc000003','th000002','mv000015', to_date('9:00PM', 'HH:MIAM'));


INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB) 
VALUES('st000001', 'John Doe', '1234 Waterview Pkwy', 'Richardson', 'TX', '75080', '(972) 235-0681', 'john@doe.com', 'M', '123456789', to_date('4 December 1985', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000002', 'Mary Jane', '425 Coit Rd', 'Plano', 'TX', '75075', '(972) 437-9146', 'mary@jane.com', 'F', '951358642', to_date('8 March 1977', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000003', 'Julia Hudson', '1700 Dallas Parkway', 'Plano', 'TX', '75093', '(214) 931-9846', 'julie@hudson.com', 'F', '456987124', to_date('12 October 1979', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000004', 'Brad Anderson', '12789 Midway Road', 'Dallas', 'TX', '75244', '(469) 488-8862', 'brad@anderson.com', 'M', '684253971', to_date('26 June 1986', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000005', 'Robert Brown', '5302 No. Garland Ave.', 'Garland', 'TX', '75044', '(972) 496-2711', 'robert@brown.com', 'M', '975421354', to_date('26 October 1987', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000006', 'John Steven', '9661 Audelia Road', 'Dallas', 'TX', '75238', '(214) 343-1525', 'john@steven.com', 'M', '741258963', to_date('16 May 1982', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000007', 'Andrew Symonds', '18121 Marsh Lane', 'Dallas', 'TX', '75287', '(972) 307-6978', 'andrew@@symonds.com', 'M', '974568215', to_date('14 April 1971', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000008', 'Samantha Patterson', '150 West Fm 544', 'Murphy', 'TX', '75094', '(972) 633-0257', 'samantha@patterson.com', 'F', '974123589', to_date('29 January 1966', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000009', 'Emily Robert', '150 West Fm 554', 'Murphy', 'TX', '75094', '(469) 633-0252', 'emily@robert.com', 'F', '594123589', to_date('29 January 1976', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000010', 'Micheal Admond', '1213 E. Trinity Mills Road', 'Carrollton', 'TX', '75006', '(972) 466-2228', 'micheal@admond.com', 'F', '325123589', to_date('19 May 1972', 'dd Month YYYY'));

INSERT INTO Staff(StaffId, StaffName, StaffStreetAddress, StaffCity, StaffState, StaffZipCode, StaffPhoneNumber, StaffEmail, Gender, SSN, DOB)
VALUES('st000011', 'Christina Diaz', '3959 Broadway Blvd', 'Garland', 'TX', '75043', '(214) 926-6215', 'christina@diaz.com', 'F', '275123523', to_date('16 May 1982', 'dd Month YYYY'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000001', 'th000001', 'sc000001', to_date('01:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 01:03PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000002', 'th000001', 'sc000001', to_date('01:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 01:04PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000003', 'th000001', 'sc000001', to_date('01:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 01:08PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000004', 'th000001', 'sc000003', to_date('06:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 01:06PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000001', 'th000001', 'sc000003', to_date('06:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 12:26PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000002', 'th000001', 'sc000003', to_date('06:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 12:25PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000003', 'th000001', 'sc000003', to_date('06:10PM', 'HH:MIAM'), 6.00, 'st000001', to_date('5-September-2007 12:23PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000001', 'th000001', 'sc000002', to_date('03:20PM', 'HH:MIAM'), 5.50, 'st000003', to_date('5-September-2007 03:19PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000005', 'th000001', 'sc000002', to_date('03:20PM', 'HH:MIAM'), 5.50, 'st000003', to_date('5-September-2007 03:17PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000002', 'th000001', 'sc000002', to_date('03:20PM', 'HH:MIAM'), 5.50, 'st000003', to_date('6-September-2007 03:05PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000003', 'th000001', 'sc000002', to_date('03:20PM', 'HH:MIAM'), 5.50, 'st000003', to_date('6-September-2007 03:08PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000004', 'th000001', 'sc000002', to_date('03:20PM', 'HH:MIAM'), 5.50, 'st000003', to_date('6-September-2007 03:11PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000001', 'th000002', 'sc000001', to_date('07:30PM', 'HH:MIAM'), 8.50, 'st000002', to_date('8-September-2007 05:43PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000002', 'th000002', 'sc000001', to_date('05:00PM', 'HH:MIAM'), 6.50, 'st000002', to_date('8-September-2007 04:48PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000003', 'th000002', 'sc000001', to_date('05:00PM', 'HH:MIAM'), 6.50, 'st000002', to_date('8-September-2007 04:53PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000004', 'th000002', 'sc000001', to_date('05:00PM', 'HH:MIAM'), 6.50, 'st000002', to_date('8-September-2007 04:49PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Ticket(TicketId, TheaterId, ScreenId, StartTime, Price, StaffId, SellingTimestamp)
VALUES ('tk000005', 'th000002', 'sc000001', to_date('07:30PM', 'HH:MIAM'), 6.50, 'st000002', to_date('8-September-2007 04:56PM', 'DD-Month-YYYY HH:MIAM'));

INSERT INTO Position (PositionId,PositionName) VALUES ('pos00001','ManagerOfSales');
INSERT INTO Position (PositionId,PositionName) VALUES ('pos00002','ManagerOfAccounts');
INSERT INTO Position (PositionId,PositionName) VALUES ('pos00003','SalesMan');
INSERT INTO Position (PositionId,PositionName) VALUES ('pos00004','GeneralManager');
INSERT INTO Position (PositionId,PositionName) VALUES ('pos00005','Accountant');
INSERT INTO Position (PositionId,PositionName) VALUES ('pos00006','Projectionist');

INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000001','pos00003','th000001',to_date('01-September-2003', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000003','pos00003','th000001',to_date('15-August-2004', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate,EndingDate)
VALUES('st000004','pos00003','th000001',to_date('15-August-2002', 'DD-Month-YYYY'),to_date('01-August-2006', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000004','pos00001','th000001',to_date('15-August-2006', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000006','pos00002','th000001',to_date('01-July-2005', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000008','pos00006','th000001',to_date('01-July-2004', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000009','pos00004','th000001',to_date('01-July-2005', 'DD-Month-YYYY'));

INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000002','pos00003','th000002',to_date('15-July-2005', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000011','pos00003','th000002',to_date('01-September-2006', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000005','pos00001','th000002',to_date('15-April-2005', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate,EndingDate)
VALUES('st000006','pos00005','th000002',to_date('01-July-1998', 'DD-Month-YYYY'),to_date('01-June-2003', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate,EndingDate)
VALUES('st000006','pos00002','th000002',to_date('15-June-2003', 'DD-Month-YYYY'),to_date('15-June-2005', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate)
VALUES('st000007','pos00005','th000001',to_date('01-August-2003', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate,EndingDate)
VALUES('st000009','pos00004','th000002',to_date('01-January-2001', 'DD-Month-YYYY'),to_date('30-June-2005', 'DD-Month-YYYY'));
INSERT INTO StaffPosition (StaffId,PositionId,theaterId,StartingDate,EndingDate)
VALUES('st000010','pos00001','th000002',to_date('01-June-1998', 'DD-Month-YYYY'),to_date('31-March-2005', 'DD-Month-YYYY'));

INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000001','pos00003',40000,to_date('01-September-2003', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000001','pos00003',42000,to_date('01-December-2004', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000002','pos00003',40000,to_date('15-July-2005', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000003','pos00003',40000,to_date('15-August-2004', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000004','pos00003',40000,to_date('15-August-2002', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000004','pos00001',65000,to_date('15-August-2006', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000005','pos00001',65000,to_date('15-April-2005', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000006','pos00005',55000,to_date('01-July-1998', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000006','pos00002',65000,to_date('15-June-2003', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000006','pos00002',65000,to_date('01-July-2005', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000007','pos00005',55000,to_date('01-August-2003', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000008','pos00006',35000,to_date('01-July-2004', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000009','pos00004',75000,to_date('01-January-2001', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000009','pos00004',75000,to_date('01-July-2005', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000010','pos00004',65000,to_date('01-June-1998', 'DD-Month-YYYY'));
INSERT INTO StaffSalary (StaffId,PositionId,Salary,EffectiveDate)
VALUES('st000011','pos00003',40000,to_date('01-September-2006', 'DD-Month-YYYY'));

commit;
spool off;
