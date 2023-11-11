create table users (
    username varchar(50) not null primary key,
    password varchar(500) not null,
    enabled boolean not null,
    full_name varchar(100)
);
ALTER TABLE IF EXISTS authorities
    ADD CONSTRAINT fk_authority_user
    FOREIGN KEY (username) REFERENCES app_user(username);

