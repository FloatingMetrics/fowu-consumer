-- CREATE USER IF NOT EXISTS fowu_user IDENTIFIED BY '1234';
GRANT CREATE, ALTER, DROP, SELECT, INSERT, UPDATE, DELETE ON fowudatabase.* TO fowu_user;

CREATE USER 'grafanareader'@'localhost' IDENTIFIED BY 'Ds2cuSV4BZC9pZG';
GRANT SELECT ON fowudatabase.* TO grafanareader;


