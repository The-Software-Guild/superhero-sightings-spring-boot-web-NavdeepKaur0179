Drop DATABASE if exists SuperheroSightingsTest;
CREATE DATABASE SuperheroSightingsTest;

USE SuperheroSightingsTest;
CREATE TABLE IF NOT EXISTS superPower
(
id INT PRIMARY KEY AUTO_INCREMENT,
superPower VARCHAR(50) NOT NULL,
description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS hero
(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
description VARCHAR(255),
superPowerId INT NOT NULL,
FOREIGN KEY(superPowerId) REFERENCES superPower(id),
imageName VARCHAR(45) NULL
);

CREATE TABLE IF NOT EXISTS organization
(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
description VARCHAR(255),
address VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS hero_organization
(
heroId INT NOT NULL,
organizationId INT NOT NULL,
PRIMARY KEY(heroId,organizationId),
FOREIGN KEY(heroId) REFERENCES hero(id),
FOREIGN KEY(organizationId) REFERENCES organization(id)
);

CREATE TABLE IF NOT EXISTS location
(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(30) NOT NULL,
description VARCHAR(255),
address VARCHAR(150) NOT NULL,
latitude VARCHAR(50) NOT NULL,
longitude VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS sighting
(
id INT PRIMARY KEY AUTO_INCREMENT,
locationId INT NOT NULL,
heroId INT NOT NULL,
`date` DATETIME NOT NULL,
description VARCHAR(255),
FOREIGN KEY(locationId) REFERENCES location(id),
FOREIGN KEY(heroId) REFERENCES hero(id)
);


