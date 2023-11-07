create table if not exists navios(
    id serial,
    name varchar(30) not null,
    estaleiro_id int not null,
    constraint pk_navios primary key(id),
    constraint estaleiro_navios_fk foreign key (estaleiro_id) references estaleiros(id)
);
