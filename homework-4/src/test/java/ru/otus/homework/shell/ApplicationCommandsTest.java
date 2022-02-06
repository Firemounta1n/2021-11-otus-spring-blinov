package ru.otus.homework.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.homework.service.MessageService;
import ru.otus.homework.service.ScannerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Тест команд shell")
@SpringBootTest
public class ApplicationCommandsTest {

    @MockBean
    private ScannerService scannerService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private Shell shell;

    private static final String GREETING_PATTERN = "Привет %s %s";
    private static final String DEFAULT_LASTNAME = "LastName";
    private static final String DEFAULT_FIRSTNAME = "FirstName";
    private static final String COMMAND_LOGIN = "login";
    private static final String COMMAND_LOCALE = "locale";
    private static final String COMMAND_LOGOUT = "logout";
    private static final String COMMAND_LOGIN_SHORT = "ln";
    private static final String COMMAND_LOCALE_SHORT = "le";
    private static final String COMMAND_LOGOUT_SHORT = "lt";

    @DisplayName("Должен возвращать приветствие для всех форм команды логина")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
        when(scannerService.getScannerInNext()).thenReturn(DEFAULT_LASTNAME).thenReturn(DEFAULT_FIRSTNAME);

        String res = (String) shell.evaluate(() -> COMMAND_LOGIN);
        System.out.println(res);
        assertThat(res).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LASTNAME, DEFAULT_FIRSTNAME));

        shell.evaluate(() -> COMMAND_LOGOUT);

        when(scannerService.getScannerInNext()).thenReturn(DEFAULT_LASTNAME).thenReturn(DEFAULT_FIRSTNAME);

        String res2 = (String) shell.evaluate(() -> COMMAND_LOGIN_SHORT);
        System.out.println(res);
        assertThat(res2).isEqualTo(String.format(GREETING_PATTERN, DEFAULT_LASTNAME, DEFAULT_FIRSTNAME));
    }

    @DisplayName("Должен возвращать сообщение о выбранном английском языке для всех форм команды логина")
    @Test
    void shouldReturnEnglishSelectedLanguageAfterLanguageCommandEvaluated() {
        when(scannerService.getScannerInNext()).thenReturn("1");

        String res = (String) shell.evaluate(() -> COMMAND_LOCALE);
        System.out.println(res);
        assertThat(res).isEqualTo(messageService.getMessage("language.selected"));

        String res2 = (String) shell.evaluate(() -> COMMAND_LOCALE_SHORT);
        System.out.println(res2);
        assertThat(res2).isEqualTo(messageService.getMessage("language.selected"));
    }

    @DisplayName(" Должен возвращать сообщение о выбранном русском языке для всех форм команды логина")
    @Test
    void shouldReturnRussianSelectedLanguageAfterLanguageCommandEvaluated() {
        when(scannerService.getScannerInNext()).thenReturn("2");

        String res = (String) shell.evaluate(() -> COMMAND_LOCALE);
        System.out.println(res);
        assertThat(res).isEqualTo(messageService.getMessage("language.selected"));

        String res2 = (String) shell.evaluate(() -> COMMAND_LOCALE_SHORT);
        System.out.println(res2);
        assertThat(res2).isEqualTo(messageService.getMessage("language.selected"));
    }

    @DisplayName("Должен вернуть сообщение о необходимлсти входа для всех форм команды выхода")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void shouldReturnGoodbyeAfterLogoutCommandEvaluated() {
        Object res = shell.evaluate(() -> COMMAND_LOGOUT);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
        CommandNotCurrentlyAvailable error = (CommandNotCurrentlyAvailable) res;
        assertThat(error.getAvailability().getReason()).isEqualTo("\n" + messageService.getMessage("student.unavailable"));

        Object res2 = shell.evaluate(() -> COMMAND_LOGOUT_SHORT);
        System.out.println(res2);
        assertThat(res2).isInstanceOf(CommandNotCurrentlyAvailable.class);
        CommandNotCurrentlyAvailable error2 = (CommandNotCurrentlyAvailable) res;
        assertThat(error2.getAvailability().getReason()).isEqualTo("\n" + messageService.getMessage("student.unavailable"));

    }
}
