INSERT INTO user_data (user_id, first_name, last_name, middle_name, birth_date, priority_email, phone_number,
                       state, admin_approval, avatar_url)
VALUES ('4f7d9a3e-8b6f-4a50-9c21-63d8b7e88d43', 'James', 'Smith', 'Edward', '1985-03-12', 'james.smith@example.com',
        '+12345678901', 'ACTIVE', true, NULL),
       ('d10f62a9-0a47-40b5-b8d1-38f9a7e346cc', 'Olivia', 'Johnson', 'Marie', '1994-07-22','olivia.johnson@example.com',
        '+19876543210','ACTIVE', true, NULL),
       ('a2e3b0f4-c5d7-4c34-9a3f-1bfb6bcd5a7e', 'Michael', 'Williams', 'James', '1977-11-30','michael.williams@example.com',
        '+14785236900','ACTIVE', true, NULL),
       ('58b01e17-129d-427f-81f4-254b6db6a7b8', 'Emma', 'Brown', 'Louise', '1999-05-05', 'emma.brown@example.com',
        '+16253479820','ACTIVE', true, NULL),
       ('1f4a2c77-f22e-4f18-80d1-2734e8aeb3dc', 'William', 'Jones', 'Alexander', '1983-08-15','william.jones@example.com',
        '+10928374655','ACTIVE', true, NULL),
       ('b567c1de-8c74-4ea3-9f98-6edb5c4a7f0e', 'Sophia', 'Garcia', 'Isabel', '1991-12-02', 'sophia.garcia@example.com',
        '+19847651230', 'ACTIVE', true, NULL),
       ('8d72f0a9-e39c-4bcb-bfd5-e0eac2b75f3a', 'James', 'Miller', 'Thomas', '1975-01-28', 'james.miller@example.com',
        '+17583920461','ACTIVE', true, NULL),
       ('c34d5f47-02b7-4a2c-9c33-0a9ed1e65184', 'Isabella', 'Davis', 'Rose', '2000-06-19', 'isabella.davis@example.com',
        '+18492057346','ACTIVE', true, NULL),
       ('9a4e376f-3d1b-47c6-b22e-5c1d4f1ea8f7', 'Benjamin', 'Martinez', 'Lee', '1987-10-09','benjamin.martinez@example.com',
        '+12309845672','ACTIVE', true, NULL),
       ('e51c6937-77ad-4f3e-876c-73bffdb9e6b2', 'Mia', 'Rodriguez', 'Ann', '1995-04-25', 'mia.rodriguez@example.com',
        '+19028374655','ACTIVE', true, NULL);

INSERT INTO user_role (user_id, role)
VALUES ('4f7d9a3e-8b6f-4a50-9c21-63d8b7e88d43', 'CLIENT'),
       ('d10f62a9-0a47-40b5-b8d1-38f9a7e346cc', 'CLIENT'),
       ('a2e3b0f4-c5d7-4c34-9a3f-1bfb6bcd5a7e', 'CLIENT'),
       ('58b01e17-129d-427f-81f4-254b6db6a7b8', 'CLIENT'),
       ('1f4a2c77-f22e-4f18-80d1-2734e8aeb3dc', 'CLIENT'),
       ('b567c1de-8c74-4ea3-9f98-6edb5c4a7f0e', 'CLIENT'),
       ('8d72f0a9-e39c-4bcb-bfd5-e0eac2b75f3a', 'CLIENT'),
       ('c34d5f47-02b7-4a2c-9c33-0a9ed1e65184', 'CLIENT'),
       ('9a4e376f-3d1b-47c6-b22e-5c1d4f1ea8f7', 'CLIENT'),
       ('e51c6937-77ad-4f3e-876c-73bffdb9e6b2', 'CLIENT');

INSERT INTO client (client_id, user_id)
VALUES ('680426e1-c56a-4e67-98f0-25b348c7e6c5', '4f7d9a3e-8b6f-4a50-9c21-63d8b7e88d43');
INSERT INTO client (client_id, user_id)
VALUES ('4a083f9b-754b-4722-a68f-bcd583aa911c', 'd10f62a9-0a47-40b5-b8d1-38f9a7e346cc');
INSERT INTO client (client_id, user_id)
VALUES ('cfc1d956-f8d1-44a7-ac28-f1edd182c6ce', 'a2e3b0f4-c5d7-4c34-9a3f-1bfb6bcd5a7e');
INSERT INTO client (client_id, user_id)
VALUES ('e6eb466b-074c-43ca-834b-feeaa6e012b2', '58b01e17-129d-427f-81f4-254b6db6a7b8');
INSERT INTO client (client_id, user_id)
VALUES ('d063c837-3ea8-44ce-9659-8975c97ce82b', '1f4a2c77-f22e-4f18-80d1-2734e8aeb3dc');
INSERT INTO client (client_id, user_id)
VALUES ('cc133ab8-ee0e-417f-b32d-171620ee5d88', 'b567c1de-8c74-4ea3-9f98-6edb5c4a7f0e');
INSERT INTO client (client_id, user_id)
VALUES ('1c84f167-9a41-411f-97e8-ba1d1c71504c', '8d72f0a9-e39c-4bcb-bfd5-e0eac2b75f3a');
INSERT INTO client (client_id, user_id)
VALUES ('da9fd5a2-16bc-4bd7-9ac0-f9f527a833e8', 'c34d5f47-02b7-4a2c-9c33-0a9ed1e65184');
INSERT INTO client (client_id, user_id)
VALUES ('37a794aa-1b91-45e0-a7e2-aa3a2ddc71d2', '9a4e376f-3d1b-47c6-b22e-5c1d4f1ea8f7');
INSERT INTO client (client_id, user_id)
VALUES ('002a7c04-22e1-446a-a0c6-f1bd3a5b2d9a', 'e51c6937-77ad-4f3e-876c-73bffdb9e6b2');