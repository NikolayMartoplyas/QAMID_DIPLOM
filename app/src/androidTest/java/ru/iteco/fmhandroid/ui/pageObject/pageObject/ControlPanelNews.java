package ru.iteco.fmhandroid.ui.pageObject.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pageObject.Utils;

public class ControlPanelNews {
    CreateNews createNews = new CreateNews();
    EditNews editNews = new EditNews();
    FilterNews filterNews = new FilterNews();

    // Объявление идентификаторов кнопок
    private final int buttonAddNews = R.id.add_news_image_view;
    private final int buttonEditNews = R.id.edit_news_item_image_view;
    private final int buttonDeleteNews = R.id.delete_news_item_image_view;
    private final ViewInteraction buttonOk = onView(withId(android.R.id.button1));

    public int getButtonAddNews() {
        return buttonAddNews;
    }

    @Step("Нажатие на кнопку 'Добавить новость'")
    public void addNews() {
        Allure.step("Нажатие на кнопку 'Добавить новость'");
        // Ождаем, что элемент виден и на него можно нажать
        onView(withId(buttonAddNews)).check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        onView(withId(buttonAddNews)).perform(click());
        // Ожидаем загрузку формы
        onView(isRoot()).perform(waitDisplayed(createNews.getButtonSave(), 5000));
    }

    @Step("Нажатие на кнопку 'Редактировать новость'")
    public void pressEditPanelNews() {
        Allure.step("Нажатие на кнопку 'Редактировать новость'");
        // Ожидание и проверка видимости иконки редактирования новости
        onView(isRoot()).perform(Utils.waitDisplayed(buttonEditNews, 5000));
        ViewInteraction editButton = onView(withId(buttonEditNews));
        editButton.check(matches(allOf(isDisplayed(), isClickable())));
        // Клик по элементу
        editButton.perform(click());
        // Ожидание загрузки формы
        onView(isRoot()).perform(Utils.waitDisplayed(editNews.getButtonSave(), 5000));
    }

    @Step("Нажатие на кнопку удаления новости")
    public void deleteNews() {
        Allure.step("Нажатие на кнопку удаления новости");
        onView(withId(buttonDeleteNews)).check(matches(allOf(isDisplayed(), isClickable())));
        onView(withId(buttonDeleteNews)).perform(click());
        buttonOk.check(matches(isDisplayed()));
        buttonOk.perform(click());
    }

    @Step("Поиск новости с заголовком и проверка ее видимости")
    public void searchNewsAndCheckIsDisplayed(String text) {
        Allure.step("Поиск новости с заголовком и проверка ее видимости");
        // onView(isRoot()).perform(Utils.waitDisplayed(editNews.getButtonSave(), 2000));
        ViewInteraction textTitle = onView(allOf(withText(text), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        textTitle.check(matches(isDisplayed()));
        textTitle.check(matches(withText(endsWith(text))));
    }
    @Step("Проверка отсутствия новости с заголовком")
    public void checkDoesNotExistNews(String text) {
        Allure.step("Проверка отсутствия новости с заголовком");
        onView(withText(text)).check(doesNotExist());
    }
}
