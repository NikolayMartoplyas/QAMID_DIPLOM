package ru.iteco.fmhandroid.ui.pageObject.pageObject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.instanceOf;
import static ru.iteco.fmhandroid.ui.pageObject.Utils.waitDisplayed;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;

import org.hamcrest.core.IsInstanceOf;

import io.qameta.allure.kotlin.Allure;
import io.qameta.allure.kotlin.Step;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.pageObject.Utils;

public class FilterNews {
    // Поля для поиска элементов
    private final ViewInteraction category = onView(withId(R.id.news_item_category_text_auto_complete_text_view));
    private final ViewInteraction dataFrom = onView(withId(R.id.news_item_publish_date_start_text_input_edit_text));
    private final ViewInteraction dataTo = onView(withId(R.id.news_item_publish_date_end_text_input_edit_text));
    private final ViewInteraction checkBoxActive = onView(withId(R.id.filter_news_active_material_check_box));
    private final ViewInteraction checkBoxNotActive = onView(withId(R.id.filter_news_inactive_material_check_box));
    private final ViewInteraction buttonFilter = onView(withId(R.id.filter_button));
    private final ViewInteraction buttonCancel = onView(withId(R.id.cancel_button));
    // private final ViewInteraction message = onView(withId(android.R.id.message));
    private final int cancel = R.id.cancel_button;
    private final int filter = R.id.filter_button;
    private final int buttonFilterNews = R.id.filter_news_material_button;

    // ID элементов
    private final int element = R.id.empty_news_list_text_view;

    private final int message = android.R.id.message;

    public int getCancel() {
        return cancel;
    }

    public int getFilter() {
        return filter;
    }

    @Step("Установка категории фильтра")
    public void addCategoryFilter(String text) {
        Allure.step("Установка категории " + text);
        category.check(matches(isDisplayed()));
        category.perform(replaceText(text), closeSoftKeyboard());
    }

    @Step("Установка начальной даты фильтра")
    public void setDateFromFilter(String dateFrom) {
        Allure.step("Установка начальной даты");
        dataFrom.check(matches(isDisplayed()));
        dataFrom.perform(replaceText(dateFrom), closeSoftKeyboard());
    }

    @Step("Установка конечной даты фильтра")
    public void setDateToFilter(String dateTo) {
        Allure.step("Установка конечной даты");
        dataTo.check(matches(isDisplayed()));
        dataTo.perform(replaceText(dateTo), closeSoftKeyboard());
    }

    @Step("Снятие чек-бокса 'Активная'")
    public void setCheckBoxActive() {
        checkBoxActive.perform(click());
    }

    @Step("Снятие чек-бокса 'Не активная'")
    public void setCheckBoxNotActive() {
        checkBoxNotActive.perform(click());
    }

    @Step("Подтверждение фильтрации")
    public void confirmFilter() {
        Allure.step("Нажать на кнопку 'Фильтровать");
        onView(isRoot()).perform(waitDisplayed(filter, 10000));
        buttonFilter.check(matches(isDisplayed()));
        buttonFilter.perform(click());
    }

    @Step("Отмена фильтрации")
    public void cancelFilter() {
        Allure.step("Нажать на кнопку 'Отменить'");
        onView(isRoot()).perform(waitDisplayed(cancel, 10000));
        buttonCancel.check(matches(isDisplayed()));
        buttonCancel.perform(click());
    }

    @Step("Проверка отображения ошибки 'Неверно указан период дат'")
    public void checkErrorFilterNews(String text) {
        Allure.step("Проверка отображения ошибки");
        onView(isRoot()).perform(Utils.waitDisplayed(message, 10000));
        onView(allOf(withId(message), withText(text), isDisplayed()))
                .check(matches(withText(text)));
    }

    @Step("Проверка видимости элемента с текстом 'Здесь пока ничего нет'")
    public void elementThereNothingHereYet() {
        Allure.step("Проверка видимости элемента с текстом 'Здесь пока ничего нет'");
        onView(isRoot()).perform(Utils.waitDisplayed(element, 5000));
    }
}