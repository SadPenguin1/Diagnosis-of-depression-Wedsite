INSERT INTO role(id, name) values(1, 'ROLE_ADMIN') ON DUPLICATE KEY UPDATE name = 'ROLE_ADMIN';
INSERT INTO user(id, created_date, address, age, enabled, name, phone, password, role_id)
values (1, now(), 'HN', 20, 1, 'admin', '0123456789','$2a$12$cZpjQBhfxpEd3xBoywRTU.jx3f.UD/ygj/nR373ebd/0uZe.xtYZ2',1) ON DUPLICATE KEY UPDATE id = 1;
INSERT INTO settings VALUES (1,1) ON DUPLICATE KEY UPDATE id = 1;