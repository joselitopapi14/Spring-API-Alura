create table pacientes(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    identificacion varchar(300) not null unique,
    telefono varchar(100) not null unique,
    email varchar(100) not null unique,
    calle varchar(100) not null,
    distrito varchar(100) not null,
    complemento varchar(100),
    numero varchar(20),
    ciudad varchar(100) not null,

    primary key(id)

);
