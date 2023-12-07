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

-- Create the Topic table
CREATE TABLE IF NOT EXISTS topics
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    content       TEXT,
    author_id     INT,
    creation_date VARCHAR(255),
    category_id   INT,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (category_id) REFERENCES forum_category (id)
);

-- Create the Post table
CREATE TABLE IF NOT EXISTS posts
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255),
    content       TEXT,
    description   VARCHAR(255),
    creation_date VARCHAR(255),
    up_votes      INT,
    down_votes    INT,
    author_id     INT,
    topic_id      INT,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (topic_id) REFERENCES topics (id)
    );