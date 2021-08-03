drop table if exists person;
drop table if exists household;

create table household (
    id int not null AUTO_INCREMENT primary key,
    housing_type varchar(11) not null,
    total_income int not null default 0
);

create table person (
    id int not null AUTO_INCREMENT primary key,
    name varchar(30) not null,
    gender char(1) not null,
    marital_status bool not null,
    spouse_id int,
    spouse_name varchar(30),
    occupation_type varchar(10) not null,
    annual_income int not null,
    dob date not null,
    household_id int not null,
    foreign key (spouse_id) references person(id) ,
    constraint person_household_fk foreign key (household_id) references household(id) on delete cascade
);