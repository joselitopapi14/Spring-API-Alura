
create table usuarios(

    id bigint not null auto_increment,
    user varchar(100) not null unique,
    password varchar(300) not null,

    primary key(id)

);
