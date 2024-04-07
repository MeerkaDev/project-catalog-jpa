insert into categories(name)
values ('Процессоры'),
       ('Мониторы');

insert into specs_categories(category_id, name)
values (1, 'Производитель'),
       (1, 'Количество ядер'),
       (1, 'Сокет'),
       (2, 'Производитель'),
       (2, 'Диагональ'),
       (2, 'Матрица'),
       (2, 'Разрешение');

insert into products(category_id, name, price)
values (1, 'Intel Core I9 9900', 265000),
       (1, 'AMD Ryzen R7 7700', 130000),
       (2, 'Samsung SU556270', 110000),
       (2, 'AOC Z215S659', 85000);

insert into values_specs(specs_categories_id, product_id, value)
values (1, 1, 'Intel'),
       (2, 1, '8'),
       (3, 1, '1250'),
       (1, 2, 'AMD'),
       (2, 2, '12'),
       (3, 2, 'AM4'),
       (4, 3, 'Samsung'),
       (5, 3, '27'),
       (6, 3, 'TN'),
       (7, 3, '2560*1440'),
       (4, 4, 'AOC'),
       (5, 4, '21.5'),
       (6, 4, 'AH-IPS'),
       (7, 4, '1920*1080');