INSERT INTO AUTHOR (FIO)
VALUES ('L.N. Tolstoy'), ('L.N. Tolstoy 2'), ('L.N. Tolstoy 3'), ('L.N. Tolstoy 4');

INSERT INTO GENRE (NAME)
VALUES ('Epic novel'), ('Epic novel 2'), ('Epic novel 3');

INSERT INTO BOOK (TITLE, AUTHOR_ID, GENRE_ID)
VALUES ('Voina i mir', 1, 1), ('Voina i mir 2', 2, 2), ('Voina i mir 3', 3, 3), ('Voina i mir 4', 4, 3);

INSERT INTO COMMENT (TEXT, BOOK_ID)
VALUES ('Good!', 1), ('Awesome!', 1), ('Bad!', 2), ('Normal', 3), ('Normal', 4);