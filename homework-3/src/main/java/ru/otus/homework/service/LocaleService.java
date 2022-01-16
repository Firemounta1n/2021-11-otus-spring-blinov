package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocaleService implements LocaleServiceImpl {

    private final ScannerService scannerService;

    private final MessageSource messageSource;

    @Override
    public void selectLocale() {
        if (getLanguage() == 2) {
            Locale.setDefault(Locale.forLanguageTag("ru-RU"));
        } else {
            Locale.setDefault(Locale.forLanguageTag("und"));
        }
    }

    private Integer getLanguage() {
        System.out.println(messageSource.getMessage("language.selector", null,
                Locale.forLanguageTag("und")) + " ");
        try {
            int selectedLanguage = Integer.parseInt(scannerService.getScannerInNext());
            if (!(selectedLanguage == 1 || selectedLanguage == 2)) {
                throw new NumberFormatException();
            }
            return selectedLanguage;
        } catch (NumberFormatException e) {
            System.out.println(messageSource.getMessage("language.selector.error", null,
                    Locale.forLanguageTag("und")));
            return getLanguage();
        }
    }
}