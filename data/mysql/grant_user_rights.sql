CREATE USER 'fowu_user'@'localhost' IDENTIFIED BY '1234';
GRANT CREATE, ALTER, DROP, SELECT, INSERT, UPDATE, DELETE ON fowu.* TO fowu_user;

CREATE USER 'grafanareader'@'localhost' IDENTIFIED BY 'Ds2cuSV4BZC9pZG';
GRANT SELECT ON fowu.* TO grafanareader;


