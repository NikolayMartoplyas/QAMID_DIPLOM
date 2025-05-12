package ru.iteco.fmhandroid.ui.pageObject.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pageObject.Utils;

public class CreateNews {

    private final ViewInteraction category = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    private final ViewInteraction title = onView(withId(R.id.news_item_title_text_input_edit_text));
    private final ViewInteraction time = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    private final ViewInteraction date = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    private final ViewInteraction description = onView(withId(R.id.news_item_description_text_input_edit_text));
    private final ViewInteraction save = onView(withId(R.id.save_button));
    private final int containerCreateEditNews = R.id.container_custom_app_bar_include_on_fragment_create_edit_news;
    private final int buttonSave = R.id.save_button;

    public int getButtonSave() {
        return buttonSave;
    }

    @Step("Ввод в поле категории")
    public void addCategory(String text) {
        Allure.step("Ввод в поле категории");
        category.check(matches(isDisplayed()));
        category.perform(replaceText(text), closeSoftKeyboard());
    }

    @Step("Ввод в поле заголовка")
    public void addTitle(String text) {
        Allure.step("Ввод в поле заголовка");
        title.check(matches(isDisplayed()));
        title.perform(replaceText(text), closeSoftKeyboard());
    }

    @Step("Ввод в поле даты")
    public void addDate(String text) {
        Allure.step("Ввод в поле даты");
        date.check(matches(isDisplayed()));
        date.perform(replaceText(text), closeSoftKeyboard());
    }

    @Step("Ввод в поле времени")
    public void addTime(String text) {
        Allure.step("Ввод в поле времени");
        time.check(matches(isDisplayed()));
        time.perform(replaceText(text), closeSoftKeyboard());

    }

    @Step("Ввод в поле описания")
    public void addDescription(String text) {
        Allure.step("Ввод в поле описания");
        description.check(matches(isDisplayed()));
        description.perform(replaceText(text), closeSoftKeyboard());
    }

    @Step("Нажатие на кнопку 'Сохранить'")
    public void pressSave() {
        Allure.step("Нажатие на кнопку 'Сохранить'");
        onView(isRoot()).perform(waitDisplayed(buttonSave, 5000));
        save.check(matches(isDisplayed()));
        save.perform(scrollTo()).perform(click());
    }

    @Step("Проверка, что осталась открыта форма 'Создания новости'")
    public void verifyNewsCreationFormDisplayed() {
        Allure.step("Проверка, что форма создания новости осталась отображена");
        onView(withId(containerCreateEditNews)).check(matches(isDisplayed()));
    }
}
