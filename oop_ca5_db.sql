/* CREATING DATABASE */
DROP DATABASE IF EXISTS MC_items;
CREATE DATABASE MC_items;
USE MC_items;

/* CREATING TABLES */
CREATE TABLE blocks 
(block_id INTEGER not NULL AUTO_INCREMENT, 
 block_name VARCHAR(20), 
 hardness DOUBLE(4,2), 
 blast_resistance DOUBLE(8,2), 
 gravity_affected BOOLEAN,
 PRIMARY KEY (block_id));
 
 
/* TABLES INSERTS: */

/* BLOCKS */
INSERT INTO blocks (block_id,block_name,hardness,blast_resistance,gravity_affected)
VALUES (1,'cobblestone',2,6,FALSE),
 (2,'grass block',0.6,0.6,FALSE),
 (3,'sand',0.5,0.5,TRUE),
 (4,'obsidian',50,1200,FALSE),
 (5,'log',2,2,FALSE),
 (6,'stone',1.5,6,FALSE),
 (7,'gravel',0.6,0.6,TRUE),
 (8,'terracota',1.25,4.2,FALSE),
 (9,'clay',0.6,0.6,FALSE),
 (10,'bricks',2,6,FALSE),
 (11,'sandstone',0.8,0.8,FALSE),
 (12,'prismarine',1.5,6,FALSE),
 (13,'diamond block',5,6,FALSE);