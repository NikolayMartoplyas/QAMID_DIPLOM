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

import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import androidx.test.espresso.ViewInteraction;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;

public class EditNews {
    private final ViewInteraction editCategory = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    private final ViewInteraction editTitle = onView(withId(R.id.news_item_title_text_input_edit_text));
    private final ViewInteraction editTime = onView(withId(R.id.news_item_publish_time_text_input_edit_text));
    private final ViewInteraction editDate = onView(withId(R.id.news_item_publish_date_text_input_edit_text));
    private final ViewInteraction editDescription = onView(withId(R.id.news_item_description_text_input_edit_text));
    private final ViewInteraction save = onView(withId(R.id.save_button));

    private final int buttonSave = R.id.save_button;

    public int getButtonSave() {
        return buttonSave;
    }

    @Step("Редактирование значения в поле 'Категория'")
    public void editCategory(String text) {
        Allure.step("Редактирование значения в поле 'Категория'");
        editCategory.check(matches(isDisplayed()));
        editCategory.perform(replaceText(text), closeSoftKeyboard());
    }

    @Step("Редактирование значения в поле заголовка")
    public void editTitle(String text) {
        Allure.step("Редактирование значения в поле заголовка" + text);
        editTitle.check(matches(isDisplayed()));
        editTitle.perform(replaceText(text), closeSoftKeyboard());
    }

    // не использован, так как тесты с редактированием будут падать
    @Step("Редактирование значения в поле дата")
    public void editDate(String text) {
        Allure.step("Редактирование значения в поле дата" + text);
        editDate.check(matches(isDisplayed()));
        editDate.perform(replaceText(text), closeSoftKeyboard());
    }

    // не использован, так как тесты с редактированием будут падать
    @Step("Редактирование значения в поле время")
    public void editTime(String text) {
        Allure.step("Редактирование значения в поле время" + text);
        editTime.check(matches(isDisplayed()));
        editTime.perform(replaceText(text), closeSoftKeyboard());
    }

    // не использован, так как тесты с редактированием будут падать
    @Step("Редактирование значения в поле описания")
    public void editDescription(String text) {
        Allure.step("Редактирование значения в поле описания" + text);
        editDescription.check(matches(isDisplayed()));
        editDescription.perform(replaceText(text), closeSoftKeyboard());
    }
    @Step("Нажатие на кнопку 'Сохранить'")
    public void pressSave() {
        Allure.step("Нажатие на кнопку 'Сохранить'");
        onView(isRoot()).perform(waitDisplayed(buttonSave, 5000));
        save.check(matches(isDisplayed()));
        save.perform(scrollTo()).perform(click());
    }
}
