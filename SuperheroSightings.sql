Drop DATABASE if exists SuperheroSightings;
CREATE DATABASE SuperheroSightings;

USE SuperheroSightings;
CREATE TABLE hero
(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
description VARCHAR(255),
superPower VARCHAR(50) NOT NULL
);

CREATE TABLE organization
(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
description VARCHAR(255),
address VARCHAR(150) NOT NULL
);

CREATE TABLE hero_organization
(
heroId INT NOT NULL,
organizationId INT NOT NULL,
PRIMARY KEY(heroId,organizationId),
FOREIGN KEY(heroId) REFERENCES hero(id),
FOREIGN KEY(organizationId) REFERENCES organization(id)
);

CREATE TABLE location
(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
description VARCHAR(255),
address VARCHAR(150) NOT NULL,
latlong varchar(50) NOT NULL
);

CREATE TABLE sighting
(
id INT PRIMARY KEY AUTO_INCREMENT,
locationId INT NOT NULL,
heroId INT NOT NULL,
`date` DATE NOT NULL,
description VARCHAR(255),
FOREIGN KEY(locationId) REFERENCES location(id),
FOREIGN KEY(heroId) REFERENCES hero(id)
);


