package ru.otus.homework.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.homework.service.ExamService;
import ru.otus.homework.service.LocaleService;
import ru.otus.homework.service.MessageService;
import ru.otus.homework.service.StudentService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationCommands {

    private final LocaleService localeService;

    private final MessageService messageService;

    private final StudentService studentService;

    private final ExamService examService;

    @ShellMethod(value = "Change locale", key = {"le", "locale"})
    public void locale() {
        localeService.selectLocale();
    }

    @ShellMethod(value = "Login student", key = {"ln", "login"})
    public void login() {
        studentService.createStudent();
    }

    @ShellMethod(value = "Logout", key = {"lt", "logout"})
    @ShellMethodAvailability(value = "isStudentLogin")
    public void logout() {
        studentService.cleanStudent();
    }

    @ShellMethod(value = "Make exam", key = {"e", "exam"})
    @ShellMethodAvailability(value = "isStudentLogin")
    public void exam() {
        examService.makeExam();
    }

    private Availability isStudentLogin() {
        return studentService.getStudent() == null
                ? Availability.unavailable("\n" + messageService.getMessage("student.unavailable"))
                : Availability.available();
    }
}
