USE SuperheroSightings;
-- insert into superPower
insert into superPower (id, superPower, description)
values 
(1, 'Super Powers', 'Super speed, strength and flying'),
(2, 'Magical', 'Can cast spells and control the elements'),
(4, 'Invisibility', 'Becomes invisible at will'),
(6, 'Timey Wimey', 'Can manipulate time.  Make it slower or faster.');

insert into hero (id, name, description, superPowerId)
values
(4, 'Mighty Man','The mightiest of all', 1),
(5, 'Wizard Warrior','Very quick with the spell', 2),
(7, 'Dr Who','Has a TARDIS and travels thru time', 6),
(8, 'The Master', 'Bad guy with his own TARDIS', 6),
(10, 'Super Chaos','Just another bad guy who can be invisible', 4);

INSERT INTO organization (id, `name`,`description`, address ) 
VALUES
(1, 'Justice Federation', 'Heros for justice', '555 Main St, Kingsplace, NY'),
(2, 'Freedom Leauge', 'Freedom for all', '123 Harvard Ave, Queensplace, NY'),
(3, 'Chaos Charter', 'Creators of chaos', '777 Park Pl, Jokersplace, NY'),
(5, 'The Bad Guys', 'All around bad guys doing bad things', '2224 Hyjinks Ave, Gremlinsplace, NY'),
(6, 'The Hero Longhouse', 'Group of viking heros', '104 Viking Way, Norsplace, NY');


INSERT INTO location(id, `name`, `description`, address, latitude, longitude) 
VALUES
(1, 'Downtown Main', 'Downtown in Kingsplace by Plaza', '123 Main St, Kingsplace, NY', 
'40.71455', '-74.00712'),
(2, 'University Central', 'On campus by Central Ave', '88 Central Ave, Queensplace, NY', 
'33.77391', '-84.53921'),
(3, 'The Duckpond', 'The Duckpond in the middle of city park', '11 University Ave, Kingsplace, NY', 
'44.43777', '-63.63548'),
(4, 'The Plaza', 'Center of  City Park', '456 Plaza Way, Longsplace, NY', 
'51.88423', '-97.39123');

INSERT INTO hero_organization(heroId, organizationId) 
VALUES 
(4, 1),
(5, 1),
(7, 1),
(5, 2),
(4, 2),
(7, 2),
(8, 3),
(10, 3),
(8, 5),
(10, 5);


insert into sighting (id, locationId, heroId, `date`,`description`)
values
(1,1,4, '2022-01-01 10:15:00', 'Did something heroic'),
(2, 2, 5, '2022-01-01 11:30:00', 'Did something heroic'),
(3,3, 10, '2022-01-01 22:05:00', 'Did something evil'),
(4, 4, 7, '2022-02-01 10:15:00', 'Did something heroic'),
(5,4, 8, '2022-02-01 23:45:00', 'Did something evil'),
(6, 3, 5, '2022-02-01 09:15:00', 'Did something heroic'),
(7,4,4, '2022-03-01 08:30:00', 'Did something heroic'),
(8,1,7, '2022-03-01 21:15:00', 'Did something heroic'),
(9,1,8,'2022-03-01 22:45:00', 'Did something evil');


