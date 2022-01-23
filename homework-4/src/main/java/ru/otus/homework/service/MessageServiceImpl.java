package ru.otus.homework.service;

public interface MessageServiceImpl {

    String getMessage(String code);

    String getMessage(String code, Object[] args);
}