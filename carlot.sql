insert into employee (employeeid, firstname, lastname, "password") values (1, 'Christine', 'James', 'BellaBart');
insert into employee (employeeid, firstname, lastname, "password") values (0, 'Dennis', 'James', 'Baseball');

insert into customer (customerid, firstname, lastname, "password") values (102, 'Artemis', 'Crock', 'Arrow');


select * from employee;
select * from customer;
select * from offer order by status;
update offer set status = 'Active' where status = 'Rejected';
update offer set status = 'Active' where status = 'Accepted';
delete from offer where Status = 'Active';
select * from car;
update car set ownerid = null, lotid = 1000, status = 'Active' where make = 'Pontiac';
select * from payment;
delete from payment where payment_num = 2;

truncate table employee;

alter table car add constraint car_ownerid_fk foreign key (ownerid) references customer (customerid);
alter table offer add constraint offer_userid_fk foreign key (userid) references customer (customerid);
alter table offer add constraint offer_license_fk foreign key (car_license) references car (licensenum);


drop table payment;

delete from offer where userid = 100;
truncate table car;
truncate table offer;
truncate table payment;

update car set status = 'Active' where make = 'Pontiac';

delete from employee where employeeid = 0;

delete from employee where employeeid = 0;

delete from car where not status = 'Active';

drop table payment;

select max(payment_num) from payment;
select amount_remaining from payment where userid = 100 and car_license = 12562532;

drop function payment_amount_remaining;

create or replace function payment_amount_remaining(in u integer, in l integer, out paymentNum integer, out amtRemaining numeric(10,2), out amt numeric(10,2))
as $$
begin
	select into paymentNum max(payment_num) from payment;
	select into amtRemaining min(amount_remaining) from payment where userid = u and car_license = l;
	select into amt max(amount) from payment where userid = u and car_license = l;
end
$$ language plpgsql;

create or replace function get_amount_remaining(in u integer, in l integer, out amtRemaining integer)
as $$
begin
	select into amtRemaining min(amount_remaining) from payment where userid = u and car_license = l;
end
$$ language plpgsql;

select * from payment_amount_remaining(100, 12562532);
select * from get_amount_remaining(100, 12562532);

select * from car