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
import ru.iteco.fmhandroid.ui.pageObject.pageObject.ControlPanelNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.CreateNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.EditNews;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.NewsPage;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class ControlPanelNewsTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    NewsPage newsPage = new NewsPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    ControlPanelNews controlPanelNews = new ControlPanelNews();

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

    @Description("Сортировка новостей")
    @Test
    public void sortingNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        newsPage.buttonSortingNews();
    }

    @Description("Открытие формы 'Cоздание новости'")
    @Test
    public void openFormCreateNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
    }
}

