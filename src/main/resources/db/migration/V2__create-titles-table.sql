CREATE TABLE titles (
	id int8 NOT NULL DEFAULT nextval('titles_id_seq'),
	"name" varchar(255) NULL,
	release_year int4 NULL,
	created_at timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	updated_at timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT titles_name_unique UNIQUE (name),
	CONSTRAINT titles_id_pk PRIMARY KEY (id)
);
