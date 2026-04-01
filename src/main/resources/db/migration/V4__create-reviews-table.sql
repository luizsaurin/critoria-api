CREATE TABLE reviews (
	id int8 NOT NULL DEFAULT nextval('reviews_id_seq'),
	title_id int8 NULL,
	email varchar(255) NULL,
	rating int4 NULL,
	description varchar(255) NULL,
	created_at timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT reviews_email_title_id_unique UNIQUE (email, title_id),
	CONSTRAINT reviews_id_pk PRIMARY KEY (id),
	CONSTRAINT title_id_fk FOREIGN KEY (title_id) REFERENCES titles(id)
);
