package ru.iteco.fmhandroid.ui.pageObject.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import io.qameta.allure.kotlin.Step;

public class WarningError {
    @Step("Проверка видимости поп-апа 'Неверный логин или пароль'")
    public void windowError() {
        onView(withText("Неверный логин или пароль"))
                .check(matches(isDisplayed()));
    }
    @Step("Проверка видимости поп-апа 'Логин и пароль не могут быть пустыми'")
    public void windowEmptyInputField() {
        onView(withText("Логин и пароль не могут быть пустыми"))
                .check(matches(isDisplayed()));
    }
}
