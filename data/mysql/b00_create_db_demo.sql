create database fowu;

GRANT  CREATE, ALTER, DROP, SELECT, INSERT, UPDATE, DELETE ON fowu.* TO fowu_user;

CREATE TABLE weather (
                         time varchar(150),
                         waveHeight double,
                         wavePeriod double,
                         waveDirection int,
                         windSpeed double,
                         windDirection int,
                         PRIMARY KEY (time)
);