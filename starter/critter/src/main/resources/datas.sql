insert ignore into employee
   set id = 1,
       name = 'Gummy Badgers';

insert ignore into employee_skills
    set id = 1,
        skill = 'PETTING';

insert ignore into employee_skills
    set id = 1,
        skill = 'WALKING';

insert ignore into employee_days
    set id = 1,
        days = 'MONDAY';
insert ignore into customer
    set id = 1,
        name = 'Dungos',
        notes = 'total prick',
        phone_number = '12938483';

insert ignore into pet
    set id = 1,
        birth_date = '1991-08-03 21:00:00',
        name = 'Grero',
        notes = 'Bites very hard',
        type = 'OTHER',
        owner_id = 1;

insert ignore into customer_pets
    set customer_id = 1,
        pets_id = 1;