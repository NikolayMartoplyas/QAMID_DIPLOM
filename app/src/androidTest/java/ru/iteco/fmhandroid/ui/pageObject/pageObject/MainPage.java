package ru.iteco.fmhandroid.ui.pageObject.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class MainPage {
    private final int containerNews = R.id.container_list_news_include_on_fragment_main;
    private final int buttonAllNews = R.id.all_news_text_view;
    public ViewInteraction textViewNewsOnPageMain = onView(withText("Новости"));
    public ViewInteraction allNews = onView(withText("Все новости"));

    public int getContainerNews() {
        return containerNews;
    }

    @Step("Проверка видимости 'Новости'")
    public void checkNews() {
        Allure.step("Проверка видимости элемента с текстом Новости");
        onView(withId(containerNews)).check(matches(isDisplayed()));
        textViewNewsOnPageMain.check(matches(withText("Новости")));
    }

    @Step("Нажатие на кнопку 'Все новости'")
    public void setButtonAllNews() {
        onView(withId(buttonAllNews));
        allNews.check(matches(isDisplayed()));
        allNews.perform(click());
    }

    // здесь булево значение - если элемент виден (true), то это главный экран
    // если не виден (false) - авторизация
    @Step("Определение, в каком состоянии находится система (главный экран или страница авторизации)")
    public Boolean isDisplayedButtonProfile() {
        try {
            onView(withId(containerNews)).check(matches(isDisplayed()));
            return true;
        } catch (NoMatchingViewException noMatchingViewException) {
            return false;
        }
    }
}