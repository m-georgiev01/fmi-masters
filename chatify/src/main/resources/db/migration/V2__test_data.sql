INSERT INTO users
(username, password, is_active)
VALUES
    ('alice', 'password123', TRUE),
    ('bob', 'password123', TRUE),
    ('charlie', 'password123', TRUE),
    ('diana', 'password123', TRUE),
    ('eve', 'password123', TRUE);

INSERT INTO roles
(name)
VALUES
    ('Owner'),
    ('Admin'),
    ('Member');

INSERT INTO channels
(name, is_direct_message, is_active)
VALUES
    ('General', FALSE, TRUE),
    ('Announcements', FALSE, TRUE),
    ('Alice-Bob DM', TRUE, TRUE),
    ('Alice-Charlie DM', TRUE, TRUE);

INSERT INTO channels_users
(channel_id, user_id, role_id)
VALUES
-- General
(1, 1, 1), -- Alice-Owner
(1, 2, 3), -- Bob-Admin
(1, 3, 3), -- Charlie-Member

-- Announcements
(2, 1, 1), -- Alice is an Admin
(2, 2, 2), -- Bob is a Moderator
(2, 3, 3), -- Charlie is a Member

-- DMs
(3, 1, 3), -- Alice +
(3, 2, 3), -- Bob
(4, 1, 3), -- Alice +
(4, 3, 3); -- Charlie

INSERT INTO friends
(first_user_id, second_user_id)
VALUES
    (1, 2), -- Alice and Bob
    (1, 3), -- Alice and Charlie
    (2, 4), -- Bob and Diana
    (3, 5); -- Charlie and Eve

INSERT INTO messages
(content, sender_id, channel_id, sent_at)
VALUES
-- General
('Hello everyone!', 1, 1, '2025-01-22 10:00:00'),
('Good morning!', 2, 1, '2025-01-22 10:05:00'),
('Hi all!', 3, 1, '2025-01-22 10:10:00'),

-- Announcements
('Welcome to the announcements channel!', 1, 2, '2025-01-22 11:00:00'),

-- DM messages
('Hey Bob, how are you?', 1, 3, '2025-01-22 13:00:00'),
('I am good, Alice. How about you?', 2, 3, '2025-01-22 13:05:00'),
('Hi Charlie, long time no see!', 1, 4, '2025-01-22 14:00:00'),
('Yes Alice, been a while!', 3, 4, '2025-01-22 14:10:00');