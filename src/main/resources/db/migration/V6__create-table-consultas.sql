/*create table consultas(

    id bigint not null auto_increment,
    medico_id varchar(100) not null unique,
    paciente_id varchar(100) not null unique,
    data datetime not null,

    primary key (id),

    constraint fk_consultas_medico_id foreign key(medico_id) references medicos(id),
    constraint fk_consultas_paciente_id foreign key(paciente_id) references pacientes(id)
);*/
