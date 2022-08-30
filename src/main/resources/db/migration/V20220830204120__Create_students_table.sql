-- For H2 Database
create table students (
  id bigserial not null primary key,
  name varchar(512) not null,
  enrollment int not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
