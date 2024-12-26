INSERT INTO users VALUES ('user', '{noop}admin@123456', '1');
INSERT INTO authorities VALUES ('user', 'read');

INSERT INTO users VALUES ('admin', '{bcrypt}$2a$12$ZPWkZXZcRY964BojdVimbeoFEDsWlDea1TR5B1RLNFZ4x3Lo/Crde', '1'); --adminblah@123456
INSERT INTO authorities VALUES ('admin', 'admin');
