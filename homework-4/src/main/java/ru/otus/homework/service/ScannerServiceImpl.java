package ru.otus.homework.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerServiceImpl implements ScannerService {

    @Override
    public Scanner getScannerIn() {
        return new Scanner(System.in);
    }

    @Override
    public String getScannerInNext() {
        return new Scanner(System.in).next();
    }
}
