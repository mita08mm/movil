create table "users" (
  id bigint primary key generated always as identity,
  name text not null,
  email text unique not null,
  password text not null,
  registration_date timestamptz default now()
);

DROP TABLE categories;

create table categories (
  id bigint primary key generated always as identity,
  name text not null,
  description text,
  parent_category_id bigint references categories (id) on delete cascades
);

drop table notes;
create table notes (
  id BIGINT primary key generated always as identity,
  user_id BIGINT references users (id) on delete cascade,
  title text not null,
  content text,
  creation_date timestamptz default now(),
  category_id BIGINT references categories (id) on delete set null
);

create table tasks (
  id bigint primary key generated always as identity,
  user_id bigint references users (id) on delete cascade,
  title text not null,
  description text,
  creation_date timestamptz default now(),
  due_date timestamptz,
  status text CHECK (status IN('COMPLETED','PENDING','ARCHIVE'))
);

create table notifications (
  id bigint primary key generated always as identity,
  user_id bigint references users (id) on delete cascade,
  message text not null,
  read boolean default false,
  date timestamptz default now()
);