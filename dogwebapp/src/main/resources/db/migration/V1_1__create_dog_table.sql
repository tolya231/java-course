CREATE TABLE DOG (
  id       IDENTITY     NOT NULL PRIMARY KEY,
  name     VARCHAR(100) NOT NULL CHECK (length(name) >= 1),
  weight   INT          NOT NULL CHECK (weight >= 1),
  height   INT          NOT NULL CHECK (height >= 1),
  birthDay DATE CHECK (birthDay < CURRENT_DATE)
);
