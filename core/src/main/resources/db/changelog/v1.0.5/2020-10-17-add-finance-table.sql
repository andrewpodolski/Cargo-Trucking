CREATE TABLE IF NOT EXISTS finance(
    id BIGSERIAL PRIMARY KEY,
    sys_admin_payment INTEGER NOT NULL,
    db_admin_payment INTEGER NOT NULL,
    server_payment INTEGER NOT NULL
);
ALTER TABLE finance ADD COLUMN IF NOT EXISTS creation_date TIMESTAMP NOT NULL;