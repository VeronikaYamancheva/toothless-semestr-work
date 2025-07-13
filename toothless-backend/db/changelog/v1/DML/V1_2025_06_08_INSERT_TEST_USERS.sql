INSERT INTO user_data (user_id, first_name, last_name, middle_name, birth_date, priority_email, phone_number, state,
                       admin_approval, avatar_url)
VALUES ('a3e8f5b2-7c41-4d6a-9f2d-1b0c9e8d7f6a', 'Alexander', 'Volkov', 'Sergeevich', '1975-04-12',
        'master@dentalclinic.com', '+7 (900) 111-22-33', 'ACTIVE', true, NULL),

       ('b4d9e6c1-8f52-4a7b-9e3d-2c0a8f7e6d5b', 'Ekaterina', 'Ivanova', 'Petrovna', '1982-09-25',
        'admin@dentalclinic.com', '+7 (900) 222-33-44', 'ACTIVE', true, NULL),

       ('c5e0f7d2-9a63-4b8c-af4e-3d1b9f8e7d6c', 'Dmitry', 'Sokolov', 'Andreevich', '1990-07-18',
        'client.sokolov@example.com', '+7 (900) 333-44-55', 'ACTIVE', true, NULL),

       ('d6f1e8c3-0b74-4c9d-be5f-4e2c0a9f8e7d', 'Anna', 'Kozlova', 'Mikhailovna', '1988-11-30',
        'dentist.kozlova@dentalclinic.com', '+7 (900) 444-55-66', 'ACTIVE', true, NULL);

INSERT INTO user_role (user_id, role)
VALUES ('a3e8f5b2-7c41-4d6a-9f2d-1b0c9e8d7f6a', 'MASTER'),
       ('a3e8f5b2-7c41-4d6a-9f2d-1b0c9e8d7f6a', 'CLIENT'),
       ('b4d9e6c1-8f52-4a7b-9e3d-2c0a8f7e6d5b', 'ADMIN'),
       ('b4d9e6c1-8f52-4a7b-9e3d-2c0a8f7e6d5b', 'CLIENT'),
       ('c5e0f7d2-9a63-4b8c-af4e-3d1b9f8e7d6c', 'CLIENT'),
       ('d6f1e8c3-0b74-4c9d-be5f-4e2c0a9f8e7d', 'DENTIST');

INSERT INTO user_credentials (credential_id, user_id, provider_type, provider_key, hash_password)
VALUES ('1a2b3c4d-5e6f-4a8b-9c0d-1e2f3a4b5c6d', 'a3e8f5b2-7c41-4d6a-9f2d-1b0c9e8d7f6a', 'LOCAL',
        'master@dentalclinic.com',
        '$2a$10$vnntrq480EgtNxji.cGiu.LUAYIKObBvG7UXx6Y4YmnXU0mLGn9NS'),
       ('2b3c4d5e-6f7a-4b9c-0d1e-2f3a4b5c6d7e', 'b4d9e6c1-8f52-4a7b-9e3d-2c0a8f7e6d5b', 'LOCAL',
        'admin@dentalclinic.com',
        '$2a$10$tWPBMzimDSFgBHDVfwttR.AE0ZQoJNXz5zV/w2O/Cir31muXltX3K'),
       ('3c4d5e6f-7a8b-4c0d-1e2f-3a4b5c6d7e8f', 'c5e0f7d2-9a63-4b8c-af4e-3d1b9f8e7d6c', 'LOCAL',
        'client.sokolov@example.com',
        '$2a$10$tDXCKrLSegMmaaaq/BKh2OgDSSBIjto09slnziB0LX0spjps00nOq'),
       ('4d5e6f7a-8b9c-4d1e-2f3a-4b5c6d7e8f9a', 'd6f1e8c3-0b74-4c9d-be5f-4e2c0a9f8e7d', 'LOCAL',
        'dentist.kozlova@dentalclinic.com',
        '$2a$10$T3EiPJ3NIl8ttGvF.nKug.rj6ADYlE5xPi4yVn0y0Me4VQEzFESEa');

INSERT INTO dentist (dentist_id, user_id, specialization, experience, education, about)
VALUES ('e7f2d9c4-1a85-4d0e-bf6a-5f3d1b0a9e8f', 'd6f1e8c3-0b74-4c9d-be5f-4e2c0a9f8e7d',
        'aa11bc99-9c0b-4ef8-bb6d-6bb9bd380a11',
        10, 'Moscow State Medical University',
        'Specialist in general dentistry with 10 years of experience. Provides comprehensive dental care for adults and children.');

INSERT INTO client (client_id, user_id)
VALUES ('f8e3d0b5-2c96-4e1f-bc7b-6e4e2c1b0a9f',
        'c5e0f7d2-9a63-4b8c-af4e-3d1b9f8e7d6c');

INSERT INTO client (client_id, user_id)
VALUES ('43229ce7-1ebf-4976-9c2b-f8164f447685',
        'a3e8f5b2-7c41-4d6a-9f2d-1b0c9e8d7f6a');

INSERT INTO client (client_id, user_id)
VALUES ('4a793496-6883-48c9-85a9-68488832277c',
        'b4d9e6c1-8f52-4a7b-9e3d-2c0a8f7e6d5b');
