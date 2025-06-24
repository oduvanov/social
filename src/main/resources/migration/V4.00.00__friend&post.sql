CREATE TABLE IF NOT EXISTS friend
(
    user_id UUID NOT NULL,
    friend_id UUID NOT NULL,
    PRIMARY KEY(user_id, friend_id),
    CONSTRAINT friend_user_id_fk FOREIGN KEY (user_id) REFERENCES user_profile (id),
    CONSTRAINT friend_friend_id_fk FOREIGN KEY (friend_id) REFERENCES user_profile (id)
);

CREATE TABLE IF NOT EXISTS post
(
    id UUID NOT NULL PRIMARY KEY,
    text TEXT NOT NULL,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT post_user_id_fk FOREIGN KEY (user_id) REFERENCES user_profile (id)
);
CREATE INDEX IF NOT EXISTS post_created_at_idx ON post USING btree (created_at);
CREATE INDEX IF NOT EXISTS post_user_id_idx ON post USING btree (user_id);
