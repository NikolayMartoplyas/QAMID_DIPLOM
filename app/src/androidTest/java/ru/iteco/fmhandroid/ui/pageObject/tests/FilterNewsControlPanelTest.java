package ru.iteco.fmhandroid.ui.pageObject.tests;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.pageObject.Utils;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.AppBar;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.AuthorizationPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.FilterNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class FilterNewsControlPanelTest {
    AppBar appBar = new AppBar();
    FilterNews filterNews = new FilterNews();
    NewsPage newsPage = new NewsPage();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 5000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    // тест падает с ошибкой view.getVisibility() was <GONE>
    @Description("Фильтрация новостей с активными чек-боксами")
    @Test
    public void filterNewsByValidDateAndCheckBoxes() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("Объявление");
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.confirmFilter();
        newsPage.visibilityOfControlPanelLabel();
    }

    // тест падает с ошибкой view.getVisibility() was <GONE>
    @Description("Фильтрация новостей c неактивными чек-боксами")
    @Test
    public void filterNewsWithoutCheckBoxes() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("Зарплата");
        filterNews.setDateFromFilter(Utils.currentDate());
        filterNews.setDateToFilter(Utils.currentDate());
        filterNews.setCheckBoxActive();
        filterNews.setCheckBoxNotActive();
        filterNews.confirmFilter();
        newsPage.visibilityOfControlPanelLabel();
    }

    @Description("Фильтрация новостей c пустой формой")
    @Test
    public void filterNewsEmptyForm() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        newsPage.openFormFilterNews();
        filterNews.addCategoryFilter("");
        filterNews.setDateFromFilter("");
        filterNews.setDateToFilter("");
        filterNews.setCheckBoxActive();
        filterNews.setCheckBoxNotActive();
        filterNews.confirmFilter();
        newsPage.visibilityOfControlPanelLabel();
    }
}
