USE spendwise_db;

-- Insert User 1 and 15 expenses
INSERT INTO users (username, password, budget) VALUES ('johndoe', 'password123', 5000);
SET @u1 = LAST_INSERT_ID();
INSERT INTO expenses (user_id, amount, category, expense_date, payment_mode, description) VALUES
(@u1, 120, 'Food', '2023-05-01', 'Card', 'Groceries at Walmart'),
(@u1, 45, 'Transport', '2023-05-02', 'Cash', 'Uber ride'),
(@u1, 200, 'Utilities', '2023-05-03', 'Bank Transfer', 'Electricity Bill'),
(@u1, 1500, 'Rent', '2023-05-04', 'Bank Transfer', 'May Rent'),
(@u1, 80, 'Entertainment', '2023-05-05', 'Card', 'Movie Tickets'),
(@u1, 55, 'Food', '2023-05-06', 'Cash', 'Lunch at Cafe'),
(@u1, 30, 'Transport', '2023-05-07', 'Card', 'Subway pass'),
(@u1, 250, 'Shopping', '2023-05-08', 'Card', 'New shoes'),
(@u1, 95, 'Utilities', '2023-05-09', 'Bank Transfer', 'Water Bill'),
(@u1, 40, 'Food', '2023-05-10', 'Card', 'Dinner Delivery'),
(@u1, 110, 'Shopping', '2023-05-11', 'Cash', 'Clothes'),
(@u1, 60, 'Entertainment', '2023-05-12', 'Card', 'Game subscription'),
(@u1, 75, 'Transport', '2023-05-13', 'Card', 'Gas station'),
(@u1, 130, 'Food', '2023-05-14', 'Card', 'Weekend Groceries'),
(@u1, 90, 'Utilities', '2023-05-15', 'Bank Transfer', 'Internet Bill');

-- Insert User 2 and 15 expenses
INSERT INTO users (username, password, budget) VALUES ('janedoe', 'password123', 6000);
SET @u2 = LAST_INSERT_ID();
INSERT INTO expenses (user_id, amount, category, expense_date, payment_mode, description) VALUES
(@u2, 110, 'Food', '2023-06-01', 'Card', 'Whole Foods'),
(@u2, 50, 'Transport', '2023-06-02', 'Cash', 'Taxi'),
(@u2, 180, 'Utilities', '2023-06-03', 'Bank Transfer', 'Power Bill'),
(@u2, 1600, 'Rent', '2023-06-04', 'Bank Transfer', 'June Rent'),
(@u2, 90, 'Entertainment', '2023-06-05', 'Card', 'Concert'),
(@u2, 65, 'Food', '2023-06-06', 'Card', 'Restaurant'),
(@u2, 35, 'Transport', '2023-06-07', 'Card', 'Train ticket'),
(@u2, 300, 'Shopping', '2023-06-08', 'Card', 'Jacket'),
(@u2, 85, 'Utilities', '2023-06-09', 'Bank Transfer', 'Gas Bill'),
(@u2, 45, 'Food', '2023-06-10', 'Cash', 'Takeout'),
(@u2, 120, 'Shopping', '2023-06-11', 'Card', 'Makeup'),
(@u2, 70, 'Entertainment', '2023-06-12', 'Card', 'Books'),
(@u2, 80, 'Transport', '2023-06-13', 'Card', 'Fuel'),
(@u2, 140, 'Food', '2023-06-14', 'Card', 'Supermarket'),
(@u2, 100, 'Utilities', '2023-06-15', 'Bank Transfer', 'Phone Bill');

-- Insert User 3 and 15 expenses
INSERT INTO users (username, password, budget) VALUES ('alice_smith', 'password123', 7000);
SET @u3 = LAST_INSERT_ID();
INSERT INTO expenses (user_id, amount, category, expense_date, payment_mode, description) VALUES
(@u3, 105, 'Food', '2023-07-01', 'Card', 'Trader Joes'),
(@u3, 40, 'Transport', '2023-07-02', 'Cash', 'Bus'),
(@u3, 190, 'Utilities', '2023-07-03', 'Bank Transfer', 'Electric'),
(@u3, 1700, 'Rent', '2023-07-04', 'Bank Transfer', 'July Rent'),
(@u3, 85, 'Entertainment', '2023-07-05', 'Card', 'Theater'),
(@u3, 60, 'Food', '2023-07-06', 'Card', 'Cafe'),
(@u3, 25, 'Transport', '2023-07-07', 'Cash', 'Metro'),
(@u3, 220, 'Shopping', '2023-07-08', 'Card', 'Pants'),
(@u3, 75, 'Utilities', '2023-07-09', 'Bank Transfer', 'Water'),
(@u3, 50, 'Food', '2023-07-10', 'Card', 'Pizza'),
(@u3, 130, 'Shopping', '2023-07-11', 'Card', 'Watch'),
(@u3, 65, 'Entertainment', '2023-07-12', 'Card', 'Music'),
(@u3, 70, 'Transport', '2023-07-13', 'Cash', 'Gas'),
(@u3, 150, 'Food', '2023-07-14', 'Card', 'Costco'),
(@u3, 110, 'Utilities', '2023-07-15', 'Bank Transfer', 'Internet');

-- Insert User 4 and 15 expenses
INSERT INTO users (username, password, budget) VALUES ('bob_jones', 'password123', 4500);
SET @u4 = LAST_INSERT_ID();
INSERT INTO expenses (user_id, amount, category, expense_date, payment_mode, description) VALUES
(@u4, 115, 'Food', '2023-08-01', 'Card', 'Aldi'),
(@u4, 55, 'Transport', '2023-08-02', 'Cash', 'Cab'),
(@u4, 210, 'Utilities', '2023-08-03', 'Bank Transfer', 'Power'),
(@u4, 1400, 'Rent', '2023-08-04', 'Bank Transfer', 'August Rent'),
(@u4, 95, 'Entertainment', '2023-08-05', 'Card', 'Club'),
(@u4, 70, 'Food', '2023-08-06', 'Card', 'Diner'),
(@u4, 45, 'Transport', '2023-08-07', 'Card', 'Train'),
(@u4, 280, 'Shopping', '2023-08-08', 'Card', 'Sneakers'),
(@u4, 85, 'Utilities', '2023-08-09', 'Bank Transfer', 'Gas'),
(@u4, 55, 'Food', '2023-08-10', 'Cash', 'Burger'),
(@u4, 140, 'Shopping', '2023-08-11', 'Card', 'Shirt'),
(@u4, 80, 'Entertainment', '2023-08-12', 'Card', 'Streaming'),
(@u4, 90, 'Transport', '2023-08-13', 'Card', 'Petrol'),
(@u4, 160, 'Food', '2023-08-14', 'Card', 'Target'),
(@u4, 120, 'Utilities', '2023-08-15', 'Bank Transfer', 'Phone');

-- Insert User 5 and 15 expenses
INSERT INTO users (username, password, budget) VALUES ('charlie_brown', 'password123', 5500);
SET @u5 = LAST_INSERT_ID();
INSERT INTO expenses (user_id, amount, category, expense_date, payment_mode, description) VALUES
(@u5, 125, 'Food', '2023-09-01', 'Card', 'Market'),
(@u5, 60, 'Transport', '2023-09-02', 'Cash', 'Uber'),
(@u5, 195, 'Utilities', '2023-09-03', 'Bank Transfer', 'Electricity'),
(@u5, 1550, 'Rent', '2023-09-04', 'Bank Transfer', 'September Rent'),
(@u5, 100, 'Entertainment', '2023-09-05', 'Card', 'Show'),
(@u5, 75, 'Food', '2023-09-06', 'Card', 'Brunch'),
(@u5, 50, 'Transport', '2023-09-07', 'Cash', 'Bus pass'),
(@u5, 260, 'Shopping', '2023-09-08', 'Card', 'Boots'),
(@u5, 90, 'Utilities', '2023-09-09', 'Bank Transfer', 'Water'),
(@u5, 60, 'Food', '2023-09-10', 'Card', 'Sushi'),
(@u5, 150, 'Shopping', '2023-09-11', 'Card', 'Jeans'),
(@u5, 85, 'Entertainment', '2023-09-12', 'Card', 'Games'),
(@u5, 95, 'Transport', '2023-09-13', 'Card', 'Gasoline'),
(@u5, 170, 'Food', '2023-09-14', 'Card', 'Walmart'),
(@u5, 130, 'Utilities', '2023-09-15', 'Bank Transfer', 'Cable');
