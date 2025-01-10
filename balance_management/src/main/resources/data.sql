insert into authority_list
(authority, admin, client)
values
('ROLE_ADMIN',true,false),
('ROLE_CLIENT',false,true);

insert into users
(username,password,enabled)
values
('admin1','{noop}1225',1),
('client1','{noop}1225',1);

insert into authorities (username,authority) select 'admin1',authority from authority_list where admin=true;
insert into authorities (username,authority) select 'client1',authority from authority_list where client=true;

insert into admins
(name,username)
values
('Muhammed','admin1');

insert into clients
(name,email,username)
values
('Ibrahim','esedovmuhammed0@gmail.com','client1');

insert into categories
(name,type,username)
values
('Salary','INCOME','admin1'),
('BookSale','INCOME','admin1'),
('Credit','EXPENSE','admin1'),
('Food','EXPENSE','admin1'),
('Rent house','INCOME','client1'),
('Salary','INCOME','client1'),
('Clothes','EXPENSE','client1'),
('Food','EXPENSE','client1');

insert into incomes
(amount,category_id,date,username)
values
(2000,1,'2024-12-13','admin1'),
(100,2,'2024-12-11','admin1'),
(500,5,'2025-01-10','client1'),
(1200,6,'2025-01-07','client1');

insert into expenses
(amount,category_id,date,username)
values
(300,3,'2024-12-17','admin1'),
(200,4,'2024-12-19','admin1'),
(120,7,'2025-01-02','client1'),
(180,8,'2025-01-05','client1');

insert into plans
(begin_date, end_date, budget, username)
values
('2025-01-01', '2025-01-31', 500, 'admin1'),
('2025-02-01', '2025-02-28', 400, 'admin1'),
('2025-01-01', '2025-01-31', 800, 'client1'),
('2025-02-01', '2025-02-28', 1000, 'client1');
