package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {

    private final ScannerService scannerService;

    private final MessageService messageService;

    @Override
    public String selectLocale() {
        if (getLanguage() == 2) {
            Locale.setDefault(Locale.forLanguageTag("ru-RU"));
        } else {
            Locale.setDefault(Locale.forLanguageTag("und"));
        }
        return messageService.getMessage("language.selected");
    }

    private Integer getLanguage() {
        System.out.println(messageService.getMessage("language.selector"));
        try {
            int selectedLanguage = Integer.parseInt(scannerService.getScannerInNext());
            if (!(selectedLanguage == 1 || selectedLanguage == 2)) {
                throw new NumberFormatException();
            }
            return selectedLanguage;
        } catch (NumberFormatException e) {
            System.out.println(messageService.getMessage("language.selector.error"));
            return getLanguage();
        }
    }
}