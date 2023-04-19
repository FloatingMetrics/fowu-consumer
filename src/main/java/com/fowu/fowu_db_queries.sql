USE fowudatabase;

-- DELETE FROM weather;
SELECT *
FROM weather;

-- Change column data type (Done)
-- ALTER TABLE weather ALTER COLUMN wavePeriod DOUBLE PRECISION;

-- Describe table
EXEC sp_help weather;
