package ru.otus.homework.service;

public interface MessageService {

    String getMessage(String code);

    String getMessage(String code, Object[] args);
}