/* CREATING DATABASE */
DROP DATABASE IF EXISTS MC_items;
CREATE DATABASE MC_items;
USE MC_items;

/* CREATING TABLES */
CREATE TABLE blocks 
(block_id INTEGER not NULL AUTO_INCREMENT, 
 block_name VARCHAR(20), 
 hardness INTEGER not NULL, 
 blast_resistance INTEGER not NULL, 
 gravity_affected BOOLEAN,
 PRIMARY KEY (block_id));
 
 
/* TABLES INSERTS: */

/* BLOCKS */
INSERT INTO blocks (block_id,block_name,hardness,blast_resistance,gravity_affected)
VALUES (),

