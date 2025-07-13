INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('a1b2c3d4-5e6f-7a8b-9c0d-e1f2a3b4c5d6', '4f7d9a3e-8b6f-4a50-9c21-63d8b7e88d43',
        'Great dental clinic, very professional staff!', NULL, '2025-01-15 10:30:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('7f8e9d0c-1b2a-3c4d-5e6f-7a8b9c0d1e2f', 'd10f62a9-0a47-40b5-b8d1-38f9a7e346cc',
        'The dentist made me feel comfortable and relaxed.', NULL, '2025-02-10 14:45:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('3e4d5c6b-7a8f-9e0d-1c2b-3a4e5f6d7b8c', 'a2e3b0f4-c5d7-4c34-9a3f-1bfb6bcd5a7e',
        'Clean and modern facilities, highly recommend!', NULL, '2025-03-03 09:20:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('b9c8a7d6-e5f4-3c2b-1a0d-9e8f7c6b5a4d', '58b01e17-129d-427f-81f4-254b6db6a7b8',
        'My teeth have never looked better. Thank you!', NULL, '2025-04-22 16:10:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('d4c3b2a1-f6e5-8d7c-9b0a-1e2f3c4d5b6a', '1f4a2c77-f22e-4f18-80d1-2734e8aeb3dc',
        'Friendly staff and excellent service.', NULL, '2025-05-01 11:00:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('e7f6d5c4-b3a2-1c0d-9e8f-7b6a5c4d3e2f', 'b567c1de-8c74-4ea3-9f98-6edb5c4a7f0e',
        'Quick appointment and thorough checkup.', NULL, '2025-01-27 13:25:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('2b3c4d5e-6f7a-8b9c-0d1e-2f3a4b5c6d7e', '8d72f0a9-e39c-4bcb-bfd5-e0eac2b75f3a',
        'Very gentle and caring dentist.', NULL, '2025-02-14 17:40:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('5a6b7c8d-9e0f-1a2b-3c4d-5e6f7a8b9c0d', 'c34d5f47-02b7-4a2c-9c33-0a9ed1e65184',
        'The best dental experience I have ever had.', NULL, '2025-03-19 08:55:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('f1e2d3c4-b5a6-7c8d-9e0f-1a2b3c4d5e6f', '9a4e376f-3d1b-47c6-b22e-5c1d4f1ea8f7',
        'Highly skilled professionals and great atmosphere.', NULL, '2025-04-09 12:35:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('c7b8a9d0-e1f2-3c4d-5b6a-7e8f9d0c1b2a', 'e51c6937-77ad-4f3e-876c-73bffdb9e6b2',
        'I am very satisfied with the treatment received.', NULL, '2025-05-20 15:15:00', NULL);

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('e8c1f3a7-4d2b-9f6a-b0e1-5a7c2d4e9b3f', 'd10f62a9-0a47-40b5-b8d1-38f9a7e346cc',
        'Absolutely agree, the staff is wonderful!', NULL, '2025-02-01 12:00:00',
        'a1b2c3d4-5e6f-7a8b-9c0d-e1f2a3b4c5d6');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('f4b2a9e7-6c1d-3e8f-0b5a-2d7c9e1a4f3b', 'b567c1de-8c74-4ea3-9f98-6edb5c4a7f0e',
        'Thanks for sharing your experience!', NULL, '2025-02-02 15:30:00', 'a1b2c3d4-5e6f-7a8b-9c0d-e1f2a3b4c5d6');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('c9d4b2e7-8f1a-5c3d-0e6b-7a2f4b3c1d8e', 'a2e3b0f4-c5d7-4c34-9a3f-1bfb6bcd5a7e',
        'I felt the same, very relaxing atmosphere.', NULL, '2025-03-01 10:10:00',
        '7f8e9d0c-1b2a-3c4d-5e6f-7a8b9c0d1e2f');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('e3b7a1c9-5d2f-8e4b-0c6a-1d9f3b2e7c5a', '8d72f0a9-e39c-4bcb-bfd5-e0eac2b75f3a',
        'Glad to hear you had a good visit!', NULL, '2025-03-02 11:20:00', '7f8e9d0c-1b2a-3c4d-5e6f-7a8b9c0d1e2f');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('b1e7c3f9-2a4d-8b5c-0e6f-3d7a9c1b4e2f', 'c34d5f47-02b7-4a2c-9c33-0a9ed1e65184',
        'The clinic is always spotless, I agree!', NULL, '2025-04-01 09:40:00', '3e4d5c6b-7a8f-9e0d-1c2b-3a4e5f6d7b8c');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('d8f2b3c1-7e5a-4c9d-0b6f-2a1e7c3b5d4f', 'e51c6937-77ad-4f3e-876c-73bffdb9e6b2',
        'Modern equipment makes a huge difference!', NULL, '2025-04-02 14:05:00',
        '3e4d5c6b-7a8f-9e0d-1c2b-3a4e5f6d7b8c');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('f3a1d7e9-2c5b-8e4f-0a6d-1b9c7e3a5d2f', '1f4a2c77-f22e-4f18-80d1-2734e8aeb3dc',
        'Happy for you! The results are impressive.', NULL, '2025-05-01 13:30:00',
        'b9c8a7d6-e5f4-3c2b-1a0d-9e8f7c6b5a4d');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('e2c9b7a3-5d1f-8e4b-0c6a-7d3b1a9e4f2c', '58b01e17-129d-427f-81f4-254b6db6a7b8',
        'I also noticed a big improvement after my visit.', NULL, '2025-05-02 16:50:00',
        'b9c8a7d6-e5f4-3c2b-1a0d-9e8f7c6b5a4d');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('b7e3a1f9-2d4c-8b5e-0a6f-1c9d7e3b5a2f', '9a4e376f-3d1b-47c6-b22e-5c1d4f1ea8f7',
        'Everyone is so welcoming every time I visit.', NULL, '2025-05-10 10:15:00',
        'd4c3b2a1-f6e5-8d7c-9b0a-1e2f3c4d5b6a');

INSERT INTO comment (comment_id, author_id, content, dentist_id, date_time, parent_id)
VALUES ('f9c1b7e3-5a2d-4c8f-0b6e-1a9d3e7c5b4f', '4f7d9a3e-8b6f-4a50-9c21-63d8b7e88d43',
        'I appreciate the excellent service as well!', NULL, '2025-05-11 11:45:00',
        'd4c3b2a1-f6e5-8d7c-9b0a-1e2f3c4d5b6a');
