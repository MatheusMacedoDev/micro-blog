CREATE TABLE authors (
    id INTEGER PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE posts (
    id  INTEGER PRIMARY KEY,
    author_id INTEGER NOT NULL,
    title VARCHAR NOT NULL,
    content TEXT NOT NULL,
    published_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_author FOREIGN KEY (author_id)
        REFERENCES authors(id)
);