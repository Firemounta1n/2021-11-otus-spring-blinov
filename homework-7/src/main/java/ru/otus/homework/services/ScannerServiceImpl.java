package ru.otus.homework.services;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ScannerServiceImpl implements ScannerService {

    @Override
    public String getScannerInNext() {
        return new Scanner(System.in).nextLine();
    }
}
