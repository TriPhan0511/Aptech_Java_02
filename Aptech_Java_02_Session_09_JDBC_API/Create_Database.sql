-- 1. Create a new database named Students_PhanPhuTri
USE master
GO

DROP DATABASE IF EXISTS Students_PhanPhuTri
GO

CREATE DATABASE Students_PhanPhuTri
GO

-- 2. Create four new tables: Sciences, Students, Subjects and Marks.
USE Students_PhanPhuTri
GO

CREATE TABLE Sciences
(
	IDSci char(4) PRIMARY KEY NOT NULL,
	ScienceName char(255) NOT NULL,
)

CREATE TABLE Students
(
	IDStud char(4) PRIMARY KEY NOT NULL,
	IDSci char(4) NOT NULL,
	Fullname char(255) NOT NULL,
	Gender char(10) NOT NULL,
	Birthday date NULL,
	CONSTRAINT fk_science FOREIGN KEY (IDSci)
		REFERENCES Sciences (IDSci)
		ON UPDATE CASCADE
		ON DELETE CASCADE
)

CREATE TABLE Subjects
(
	IDSub char(4) PRIMARY KEY NOT NULL,
	SubjectName char(255) NOT NULL,
	NumberLesson int NOT NULL,
)

CREATE TABLE Marks
(
	IDStud char(4) NOT NULL,
	IDSub char(4) NOT NULL,
	NumberExam int NOT NULL,
	Mark decimal(3, 1) NOT NULL,
	PRIMARY KEY (IDStud, IDSub),
	CONSTRAINT fk_student FOREIGN KEY (IDStud)
		REFERENCES Students (IDStud)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT fk_subject FOREIGN KEY (IDSub)
		REFERENCES Subjects (IDSub)
		ON UPDATE CASCADE 
		ON DELETE CASCADE
)

-- 3. Insert data to the tables
-- 3.1 Sciences table:
INSERT INTO Sciences (IDSci, ScienceName)
	VALUES ('s001', 'Software Engineering')
INSERT INTO Sciences (IDSci, ScienceName)
	VALUES ('s002', 'Physics')
INSERT INTO Sciences (IDSci, ScienceName)
	VALUES ('s003', 'Chemistry')
INSERT INTO Sciences (IDSci, ScienceName)
	VALUES ('s004', 'Astronomy')
INSERT INTO Sciences (IDSci, ScienceName)
	VALUES ('s005', 'Biochemistry')
GO

-- 3.2 Students table:
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES('st01', 's001', 'Alex Ferguson', 'male', '19541230')
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES('st02', 's001', 'Brad Pitt', 'male', '19700511')
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES('st03', 's001', 'Britney Spears', 'female', '19801120')
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES('st04', 's001', 'Hillary Duff', 'female', '19840708')
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES('st05', 's001', 'White Rose', 'female', '19950510')
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES('st06', 's002', 'Jenifer Lopez', 'female', '19740101')

INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES ('st07', 's001', 'Rose', 'female', '19850511')
INSERT INTO Students (IDStud, IDSci, Fullname, Gender, Birthday)
	VALUES ('st08', 's002', 'Rose', 'male', '20211020')
GO

-- 3.3 Subjects table:
INSERT INTO Subjects (IDSub, SubjectName, NumberLesson)
	VALUES ('su01', 'Mathematics', 5)
INSERT INTO Subjects (IDSub, SubjectName, NumberLesson)
	VALUES ('su02', 'Java', 4)
INSERT INTO Subjects (IDSub, SubjectName, NumberLesson)
	VALUES ('su03', 'C#', 1)
INSERT INTO Subjects (IDSub, SubjectName, NumberLesson)
	VALUES ('su04', 'SQL Server', 1)
INSERT INTO Subjects (IDSub, SubjectName, NumberLesson)
	VALUES ('su05', 'C++', 2)
GO

---- 3.4 Marks table:
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st01', 'su01', 1, 5.5)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st01', 'su02', 1, 7.8)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st02', 'su01', 1, 9)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st02', 'su03', 2, 7.5)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st02', 'su04', 1, 8)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st03', 'su05', 1, 4)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st03', 'su02', 2, 5)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st04', 'su04', 1, 10)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st04', 'su05', 1, 7.5)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st05', 'su03', 1, 8)
INSERT INTO Marks (IDStud, IDSub, NumberExam, Mark)
	VALUES ('st05', 'su02', 1, 6.5)
GO

-- 4. Creates stored procedures
-- 4.1 Create a stored procedure to display all students
CREATE OR ALTER PROC usp_Student_List
AS
BEGIN
	SELECT st.IDStud,
		st.Fullname,
		st.Gender,
		st.Birthday,
		sc.ScienceName,
		ma.Mark, 
		su.SubjectName
	FROM Students st 
	INNER JOIN Sciences sc ON sc.IDSci = st.IDSci
	INNER JOIN Marks ma ON ma.IDStud = st.IDStud
	INNER JOIN Subjects su ON su.IDSub = ma.IDSub
END
GO

-- 4.2 Create a stored procedure to update a student
CREATE OR ALTER PROC usp_Update_Student 
(
	@studentID char(4),
	@fullName char(255),
	@gender char(10),
	@birthday date
)
AS
BEGIN
	UPDATE Students
	SET Fullname = @fullName,
	Gender = @gender,
	Birthday = @birthday
	WHERE IDStud = @studentID
END
GO

-- 4.3 Create a stored procedure to search students based on his/her student id or full name.
CREATE OR ALTER PROC usp_Search_Students
(
	@input char(255)
)
AS
BEGIN
	SELECT IDStud,
		IDSci,
		Fullname,
		Gender,
		Birthday
		FROM Students
		WHERE IDStud = @input OR Fullname = @input
END
GO