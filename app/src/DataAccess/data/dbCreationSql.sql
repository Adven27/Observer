drop table Categories;
drop table Positions;
drop table Category_Position;
drop table Organizations;
drop table Advertisings;
drop table Category_Advertising;
drop table Contacts;
drop table Organization_Contact;
drop table Places;
drop table Addresses;
drop table Place_Address;
drop table Organization_Place;
drop table Organization_Event;
drop table Events;
drop tavle Event_Place; 



CREATE TABLE `Events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `text` VARCHAR , `image` BIGINT ) 
CREATE TABLE `Categories` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `name` VARCHAR NOT NULL , `parent` INTEGER , `order` INTEGER NOT NULL ); 
CREATE TABLE `Positions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `name` VARCHAR , `organization` BIGINT ) ;
CREATE TABLE `Contacts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `info` VARCHAR NOT NULL , `type` BIGINT NOT NULL ); 
CREATE TABLE `Category_Position` (`position` BIGINT NOT NULL , `category` INTEGER NOT NULL ); 
CREATE TABLE `Category_Advertising` (`advertising` BIGINT NOT NULL , `category` INTEGER NOT NULL ); 
CREATE TABLE `Organizations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `logo` BIGINT , `name` VARCHAR NOT NULL , `description` VARCHAR );
CREATE TABLE `Organization_Contact` (`organization` BIGINT NOT NULL , `contact` BIGINT NOT NULL ) 
CREATE TABLE `Images` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `binaryContent` BLOB ); 
CREATE TABLE `Advertisings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `organization` BIGINT, `priority` INTEGER , `text` VARCHAR , `image` BIGINT ); 
CREATE TABLE `Places` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `lat` DOUBLE PRECISION NOT NULL , `alt` DOUBLE PRECISION NOT NULL , `description` VARCHAR ) 
CREATE TABLE `Addresses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT , `street` VARCHAR NOT NULL , `house` INTEGER NOT NULL , `letter` VARCHAR , `floor` INTEGER , `office` INTEGER ) 
CREATE TABLE `Place_Address` (`place` BIGINT NOT NULL , `address` BIGINT NOT NULL ) 
CREATE TABLE `Organization_Place` (`organization` BIGINT NOT NULL , `place` BIGINT NOT NULL ) 
CREATE TABLE `Organization_Event` (`organization` BIGINT NOT NULL , `event` BIGINT NOT NULL )
CREATE TABLE `Event_Place` (`event` BIGINT NOT NULL , `place` BIGINT NOT NULL ) 



