INSERT INTO artists(name, lastname, created_at) VALUES
('Kanye', 'West', CURRENT_TIMESTAMP),
('Mickael', 'Jackson', CURRENT_TIMESTAMP),
('Kendrick', 'Lamar', CURRENT_TIMESTAMP);

INSERT INTO albums(title, cover_url, created_at, artist_id) VALUES
('My Beautiful Dark Twisted Fantasy', 'https://lh3.googleusercontent.com/tSZcuqpZPhJIK8ItC4tkokRmIA6zEo408LFJnWb-3Nm8fv5adFiE1ArPTd_UqhaC-o8KI8tMNZIuFEo=w544-h544-l90-rj', CURRENT_TIMESTAMP, 1),
('KIDS SEE GHOSTS', 'https://lh3.googleusercontent.com/xRhLzotVKyeBwYPHcWDFzD-ndNcPLXIH9ZVNy_C7Lpt66dZ_lfR47boH7ussxcYOkYBwvbo5_KRKUYFi=w544-h544-l90-rj', CURRENT_TIMESTAMP, 1),
('Thriller', 'https://lh3.googleusercontent.com/URvHCfI2iyGAlAwqqBFeaFhU9DeKk_iuX40OIIIj8Zp0wIT3BVsJ2JRMwLLbUB9EZS7t7oDlMrI2S3OvGA=w544-h544-l90-rj', CURRENT_TIMESTAMP, 2),
('good kid, m.A.A.d. city', 'https://lh3.googleusercontent.com/Fz9_8koA1VbRz51kyUaOHIVDQu7LCx2W0lDjytEXz4KPGL3VIV5LS2F0uISIHHCvqQpbgHl3oCWIG6I=w544-h544-l90-rj', CURRENT_TIMESTAMP, 3);

INSERT INTO songs(title, duration, created_at, album_id) VALUES
('Dark Fantasy', '00:04:41', CURRENT_TIMESTAMP, 1),
('Gorgeous', '00:05:58', CURRENT_TIMESTAMP, 1),
('POWER', '00:04:53', CURRENT_TIMESTAMP, 1),
('All Of The Lights', '00:05:00', CURRENT_TIMESTAMP, 1),
('Monster', '00:06:19', CURRENT_TIMESTAMP, 1),
('So Appalled', '00:06:38', CURRENT_TIMESTAMP, 1),
('Devil In A New Dress', '00:05:52', CURRENT_TIMESTAMP, 1),
('Runaway', '00:09:08', CURRENT_TIMESTAMP, 1),
('Hell Of A Life', '00:05:28', CURRENT_TIMESTAMP, 1),
('Blame Game', '00:07:50', CURRENT_TIMESTAMP, 1),
('Lost In The World', '00:04:17', CURRENT_TIMESTAMP, 1),
('Who Will Survive In America', '00:01:39', CURRENT_TIMESTAMP, 1);