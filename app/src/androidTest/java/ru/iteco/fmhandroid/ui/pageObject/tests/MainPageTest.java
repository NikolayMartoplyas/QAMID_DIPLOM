package ru.iteco.fmhandroid.ui.pageObject.tests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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
import ru.iteco.fmhandroid.ui.pageObject.pageObject.MainPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.NewsPage;
import ru.iteco.fmhandroid.ui.pageObject.pageObject.ThematicArticle;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class MainPageTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    ThematicArticle thematicArticle = new ThematicArticle();
    NewsPage newsPage = new NewsPage();

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

    @Description("Открытие навигационного меню")
    @Test
    public void openNavigationMenu() {
        appBar.buttonMainMenu.perform(click());
        onView(withText("Новости")).check(matches(isDisplayed()));
        onView(withText("О приложении")).check(matches(isDisplayed()));
        onView(withText("Главная")).check(matches(isDisplayed()));
    }

    @Description("Переход на страницу 'Новости'")
    @Test
    public void openPageNews() {
        appBar.switchToNews();
        newsPage.checkNews();
    }

    @Description("Переход на страницу 'О приложении'")
    @Test
    public void openPageAboutApplication() {
        mainPage.isDisplayedButtonProfile();
        appBar.AboutApp();
    }

    @Description("Переход на страницу 'Новости' через вкладку 'Все новости'")
    @Test
    public void openPageNewsThroughAllNewsTab() {
        mainPage.setButtonAllNews();
        newsPage.checkNews();
    }

    @Description("Выход из ЛК пользователя")
    @Test
    public void logOut() {
        appBar.logOut();
    }
}
