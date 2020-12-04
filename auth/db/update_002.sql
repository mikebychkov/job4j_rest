
DROP TABLE IF EXISTS employee_persons;
DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(1000),
    last_name VARCHAR(1000),
    tax_number VARCHAR(1000),
    hired TIMESTAMP
);

CREATE TABLE employee_persons (
    employee_id INT REFERENCES employees(id),
    person_id INT REFERENCES person(id),
    PRIMARY KEY (employee_id, person_id)
);

INSERT INTO employees (first_name, last_name, tax_number, hired)
    VALUES ('Jack', 'Nicholson', 'QE1298', '20200101'),
           ('Mary', 'Ann', 'XC7542', '20200101'),
           ('Andrew', 'Lloyd', 'ER4562', '20200101');

INSERT INTO person (login, password)
    VALUES ('jack@gmail.com', '1234'),
           ('mary@gmail.com', '1234'),
           ('ann@gmail.com', '1234'),
           ('all@gmail.com', '1234');

INSERT INTO employee_persons (employee_id, person_id) VALUES (
            (SELECT id FROM employees WHERE first_name = 'Jack'),
            (SELECT id FROM person WHERE login = 'jack@gmail.com'));

INSERT INTO employee_persons (employee_id, person_id) VALUES (
             (SELECT id FROM employees WHERE first_name = 'Mary'),
             (SELECT id FROM person WHERE login = 'mary@gmail.com'));

INSERT INTO employee_persons (employee_id, person_id) VALUES (
             (SELECT id FROM employees WHERE first_name = 'Mary'),
             (SELECT id FROM person WHERE login = 'ann@gmail.com'));

INSERT INTO employee_persons (employee_id, person_id) VALUES (
             (SELECT id FROM employees WHERE first_name = 'Andrew'),
             (SELECT id FROM person WHERE login = 'all@gmail.com'));
