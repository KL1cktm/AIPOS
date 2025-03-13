-- Создание таблиц
CREATE TABLE `region` (
                          `region_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          PRIMARY KEY (`region_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `combine` (
                           `combine_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `address` varchar(255) NOT NULL,
                           `name` varchar(255) NOT NULL,
                           `phone` varchar(255) NOT NULL,
                           `fkregionid` bigint(20) NOT NULL,
                           PRIMARY KEY (`combine_id`),
                           KEY `FK_combine_region` (`fkregionid`),
                           CONSTRAINT `FK_combine_region` FOREIGN KEY (`fkregionid`) REFERENCES `region` (`region_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `product_groups` (
                          `group_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          PRIMARY KEY (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `product` (
                           `product_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) NOT NULL,
                           `sort` varchar(255) NOT NULL,
                           `group_id` bigint(20) NOT NULL,
                           PRIMARY KEY (`product_id`),
                           KEY `FK_product_group` (`group_id`),
                           CONSTRAINT `FK_product_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
                            `employee_id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `fio` varchar(255) NOT NULL,
                            `position` varchar(255) NOT NULL,
                            `fkcombineid` bigint(20) NOT NULL,
                            PRIMARY KEY (`employee_id`),
                            KEY `FK_employee_combine` (`fkcombineid`),
                            CONSTRAINT `FK_employee_combine` FOREIGN KEY (`fkcombineid`) REFERENCES `combine` (`combine_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `price` (
                         `price_id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `date` date DEFAULT NULL,
                         `purchase_price` double DEFAULT NULL,
                         `selling_price` double DEFAULT NULL,
                         `fkproductid` bigint(20) DEFAULT NULL,
                         `fkemployeeid` bigint(20) NOT NULL,
                         PRIMARY KEY (`price_id`),
                         UNIQUE KEY `uniq_product_date_employee` (`fkproductid`,`date`,`fkemployeeid`),
                         KEY `date_idx` (`date`),
                         KEY `FK_employee_id` (`fkemployeeid`),
                         CONSTRAINT `FK_employee_id` FOREIGN KEY (`fkemployeeid`) REFERENCES `employee` (`employee_id`) ON DELETE CASCADE,
                         CONSTRAINT `FK_price_product` FOREIGN KEY (`fkproductid`) REFERENCES `product` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `email` varchar(255) NOT NULL,
                        `fullname` varchar(255) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        `phone` varchar(255) NOT NULL,
                        `role` enum('ROLE_ADMIN','ROLE_ANALYST','ROLE_DIRECTOR') NOT NULL,
                        `username` varchar(255) NOT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

INSERT INTO `product_groups` (name) VALUES
                                ('Dairy products'),
                                ('Cereal products'),
                                ('Sweets'),
                                ('Car'),
                                ('Meat'),
                                ('Cart'),
                                ('Airplanes'),
                                ('DDD'),
                                ('Wheel'),
                                ('Boots');


INSERT INTO `user` (email,fullname,password,phone,`role`,username) VALUES
    ('urgzhenyok@mail.ru','Eugene Yurhilevich','$2a$10$2eW4qxUK3uyrRNkUHXcLnuhoToqKddFpjEgf2zLsRLDwg7sljhJwa','+375447272522','ROLE_ADMIN','admin');

INSERT INTO region (name) VALUES
                              ('Lida Region'),
                              ('Minsk Region');

INSERT INTO combine (address, name, phone, fkregionid) VALUES
                                                           ('Grodno Region, Lida', 'LidKon', '+3751547777777', 1),
                                                           ('Minsk Region, Minsk', 'MAZ', '+375*******1', 2),
                                                           ('Kommunisticheskaya', 'Neman', '3231231231', 1);

INSERT INTO employee (fio, `position`, fkcombineid) VALUES
                                                        ('Yurgilevich Eugene Vitalievich', 'Director', 1),
                                                        ('Yurgilevich Dmitry Vitalievich', 'Director', 2);

INSERT INTO product (name,sort,group_id) VALUES
                                             ('Chrymstick','Premium',1),
                                             ('Audi','Liftback',4),
                                             ('Meet','-',5),
                                             ('Milk','-',2);

INSERT INTO price (`date`,purchase_price,selling_price,fkproductid,fkemployeeid) VALUES
	 ('2024-10-02',23000.0,25600.0,2,1);
