package ru.iteco.fmhandroid.ui.pageObject.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.pageObject.Utils;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.FilterNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsTest {
    AppBar appBar = new AppBar();
    FilterNews filterNews = new FilterNews();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();

    ControlPanelNews controlPanelNews = new ControlPanelNews();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 5000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    @Description("Выбор каждой категории из выпадающего списка")
    @Test
    public void inputCategoriesNews() {
        //Переход на старницу новостей
        appBar.switchToNews();
        //Нажатие на кнопку фильтровать(Открытие формы фильтрации)
        newsPage.openFormFilterNews();
        // Список категорий
        List<String> categories = Arrays.asList(
                "День рождения",
                "Объявление",
                "Зарпалата",
                "Профсоюз",
                "Праздник",
                "Массаж",
                "Благодарность",
                "Нужна помощь"
        );
        // Ввод всех категорий поочередно
        for (String category : categories) {
            filterNews.addCategoryFilter(category);
        }
    }

    @Description("Фильтрация новостей с пустой формой")
    @Test
    public void filterNewsEmptyForm() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("");
        filterNews.setDateFromFilter("");
        filterNews.setDateToFilter("");
        filterNews.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей с валидным периодом дат")
    @Test
    public void filterNewsValidDate() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей отдельно по категории")
    @Test
    public void filterNewsOnlyCategory() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("Зарплата");
        filterNews.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей по дате ОТ")
    @Test
    public void filterNewsByCategoryAndDateFrom() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.confirmFilter();
        filterNews.checkErrorFilterNews("Неверно указан период");
    }

    @Description("Фильтрация новостей по дате ДО")
    @Test
    public void filterNewsByCategoryAndDateTo() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.confirmFilter();
        filterNews.checkErrorFilterNews("Неверно указан период");
    }

    @Description("Фильтрация новостей по указанному периоду (текущая дата)")
    @Test
    public void filterNewsByDatePeriod() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Фильтрация новостей с валидно указанным периодом (прошлое-будущее)")
    @Test
    public void filterNewsValidPeriod() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateFromFilter(Utils.dateMore1Years());
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.confirmFilter();
        newsPage.checkNews();
    }

    @Description("Отображение 'Здесь пока ничего нет' (с будущим периодом)")
    @Test
    public void filterNewsDateInPast() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateFromFilter(Utils.dateMore1Month());
        filterNews.setDateToFilter(Utils.dateMore1Month());
        filterNews.confirmFilter();
        filterNews.elementThereNothingHereYet();
    }

    // тест будет падать - баг (система даёт поставить даты некорректно)
    @Description("Фильтрация новостей, где первая дата в будущем, вторая в прошлом")
    @Test
    public void shouldCheckErrorWithInvalidDates() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateFromFilter(Utils.dateMore1Month());
        filterNews.setDateToFilter(Utils.dateInPast());
        filterNews.confirmFilter();
        filterNews.checkErrorFilterNews("Неверно указан период");
    }

    @Description("Отмена фильтрации после заполнения формы с помощью кнопки 'Отмена'")
    @Test
    public void filterNewsCancel() {
        appBar.switchToNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("День рождения");
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.cancelFilter();
        newsPage.checkNews();
    }
}


