CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    author_id INTEGER NOT NULL,
    title VARCHAR NOT NULL,
    content TEXT NOT NULL,
    published_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_author FOREIGN KEY (author_id)
        REFERENCES authors(id)
);