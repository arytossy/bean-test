use test_database;

create table if not exists test_table (
    num_column int
    , str_column varchar(100)
    , date_column date
    , char_column char
);