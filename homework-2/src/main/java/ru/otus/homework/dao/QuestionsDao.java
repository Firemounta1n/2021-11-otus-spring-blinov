package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;

import java.util.List;

public interface QuestionsDao {

    List<Question> getAll();
}
