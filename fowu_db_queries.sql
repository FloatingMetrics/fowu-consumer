SELECT *
FROM INFORMATION_SCHEMA.TABLES
WHERE table_type = 'BASE TABLE'

USE fowudatabase;


-- DROP TABLE str004;
IF OBJECT_ID(N'str004', N'U') IS NULL
CREATE TABLE str004
(
  captureTime VARCHAR(20) PRIMARY KEY NOT NULL,
  strain DOUBLE PRECISION
);

-- IF OBJECT_ID(N'str005', N'U') IS NULL
-- CREATE TABLE str005 (
--   captureTime VARCHAR(20) PRIMARY KEY NOT NULL,
--   strain DOUBLE PRECISION
-- );

-- SELECT OBJECT_ID(N'str005', N'U');

-- Change column data type (Done)
-- ALTER TABLE weather ALTER COLUMN wavePeriod DOUBLE PRECISION;



DELETE FROM weather;
SELECT * FROM weather;

-- DELETE FROM str001;
-- DELETE FROM str002;
-- DELETE FROM str003;
-- DELETE FROM str004;

SELECT * FROM str001;
SELECT * FROM str002;
SELECT * FROM str003;
SELECT * FROM str004;

-- Describe table
-- EXEC sp_help weather;


-- SELECT
-- CAST( captureTime AS TIME) AS "time"
-- FROM weather;
