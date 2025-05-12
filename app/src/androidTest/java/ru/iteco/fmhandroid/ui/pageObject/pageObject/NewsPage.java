package ru.iteco.fmhandroid.ui.pageObject.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pageObject.Utils;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.FilterNews;

public class NewsPage {
    ControlPanelNews controlPanelNews = new ControlPanelNews();
    FilterNews filterNewsPage = new FilterNews();
    private final int buttonSortingNews = R.id.sort_news_material_button;
    private final int buttonControlPanelNews = R.id.edit_news_material_button;
    private final int containerPageNews = R.id.container_list_news_include;
    public ViewInteraction textViewNewsOnPageNews = onView(withText("Новости"));
    private final int buttonFilterNews = R.id.filter_news_material_button;

    private final int containerControlPanel = R.id.layout_background_image_view;

    public int getButtonFilterNews() {
        return buttonFilterNews;
    }

    public int getContainerNews() {
        return containerPageNews;
    }

    @Step("Проверка видимости 'Новости'")
    public void checkNews() {
        Allure.step("Проверка видимости элемента с текстом 'Новости'");
        onView(withId(containerPageNews)).check(matches(ViewMatchers.isDisplayed()));
        textViewNewsOnPageNews.check(matches(withText("Новости")));
    }

    @Step("Нажатие на кнопку 'Сортировать' новости")
    public void buttonSortingNews() {
        Allure.step("Нажатие на кнопку 'Сортировать' новости");
        // ожидаем, что элемент виден на экране, и на него можно нажать
        onView(withId(buttonSortingNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonSortingNews)).perform(ViewActions.click());
    }

    @Step("Нажатие на кнопку фильтрации новостей")
    public void openFormFilterNews() {
        Allure.step("Нажатие на кнопку фильтрации новостей");
        // Проверяем, что элемент видим и можно на него нажать
        onView(withId(buttonFilterNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonFilterNews)).perform(ViewActions.click());
        // Ожидание загрузки формы
        onView(isRoot()).perform(Utils.waitDisplayed(filterNewsPage.getFilter(), 5000));
    }

    @Step("Переход на 'Панель управления'")
    public void switchControlPanelNews() {
        Allure.step("Переход на 'Панель управления'");
        //  Переход на страницу с панелью управления
        onView(isRoot()).perform(Utils.waitDisplayed(buttonControlPanelNews, 5000));
        // Проверяем, что элемент видно и что можно нажать
        onView(withId(buttonControlPanelNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonControlPanelNews)).perform(ViewActions.click());
        // Ожидаем загрузку панели управления
        onView(isRoot()).perform(Utils.waitDisplayed(controlPanelNews.getButtonAddNews(), 5000));
    }
    @Step("Проверка видимости элемента с текстом 'Панель управления'")
    public void visibilityOfControlPanelLabel() {
        Allure.step("Проверка видимости элемента с текстом 'Панель упараления'");
        onView(withId(containerControlPanel)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.layout_background_image_view))
                .check(matches(isDisplayed()));
        onView(isRoot()).perform(Utils.waitDisplayed(containerControlPanel, 5000));
    }
}