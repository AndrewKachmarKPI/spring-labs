-- Create the User table
CREATE TABLE IF NOT EXISTS users
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    username          VARCHAR(255) NOT NULL,
    email             VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    profile_picture   VARCHAR(255),
    biography         TEXT,
    registration_date VARCHAR(255) NOT NULL,
    role              VARCHAR(255) NOT NULL
);

-- Create the ForumCategory table
CREATE TABLE IF NOT EXISTS forum_category
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    created          VARCHAR(255) NOT NULL,
    category_name    VARCHAR(255) NOT NULL,
    description      TEXT,
    background_image VARCHAR(255),
    moderator_id     INT,
    FOREIGN KEY (moderator_id) REFERENCES users (id)
);
