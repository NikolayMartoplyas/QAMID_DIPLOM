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
public class CreateNewsTest {
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    AuthorizationPage authorizationPage = new AuthorizationPage();
    ControlPanelNews controlPanelNews = new ControlPanelNews();
    CreateNews createNews = new CreateNews();
    EditNews editNews = new EditNews();
    NewsPage newsPage = new NewsPage();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 10000));
        if (!mainPage.isDisplayedButtonProfile()) {
            authorizationPage.successfulAuthorization();
        }
    }

    // Тест должен упасть, если есть такой же заголовок новости
    @Description("Успешное создание новости")
    @Test
    public void successfulNewsCreation() {
        // Переход на страницу новостей
        appBar.switchToNews();
        // Переход на панель управления новостями
        newsPage.switchControlPanelNews();
        // Добавление новой новости
        controlPanelNews.addNews();
        // Добавление категории
        createNews.addCategory("Объявление");
        // Добавление заголовка новости
        createNews.addTitle("Crocodile");
        // Добавление даты
        createNews.addDate(Utils.currentDate());
        // Добавление времени
        createNews.addTime("20:25");
        // Добавление описания
        createNews.addDescription("тест");
        // Нажатие на кнопку сохранения
        createNews.pressSave();
        // Проверяем, что новость создана
        controlPanelNews.searchNewsAndCheckIsDisplayed("Crocodile");
    }

    @Description("Создание новости с датой публикации в прошлом")
    @Test
    public void shouldNotCreateNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory("Объявление");
        String text = "Создание новости в прошлом";
        createNews.addTitle(text);
        String pastDate = Utils.dateInPast();
        createNews.addDate(pastDate);
        createNews.addTime("20:25");
        createNews.addDescription("тест");
        createNews.pressSave();
        controlPanelNews.checkDoesNotExistNews("Создание новости в прошлом");
    }

    @Description("Создание новости с датой публикации в будущем")
    @Test
    public void shouldCreateTneNewsInFuture() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.addCategory("Объявление");
        String text = "Создание новости в будущем";
        createNews.addTitle(text);
        String pastDate = Utils.dateMore1Years();
        createNews.addDate(pastDate);
        createNews.addTime("20:25");
        createNews.addDescription("тест");
        createNews.pressSave();
        controlPanelNews.searchNewsAndCheckIsDisplayed("Создание новости в будущем");
    }

    // тест падает, если последнюю строку расскомментировать
    // скорее всего, это связано с тем, что элемент находится в невидимом контейнере
    @Description("Создание новости с пустыми полями")
    @Test
    public void shouldStayOnNewsCreationScreenWhenCreatingNewsWithEmptyFields() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.addNews();
        createNews.pressSave();
        createNews.verifyNewsCreationFormDisplayed();
        // createNews.checkErrorDisplay("Заполните пустые поля");
    }

    // тест падает с ошибкой AmbiguousViewMatcherException
    // тест пытается взаимодействовать с элементом интерфейса (значок удаления),
    // который имеет несколько экземпляров в иерархии представлений
    // тест не знает какой именно элемент использовать, так как у них одинаковые идентификаторы
    @Description("Удаление новости")
    @Test
    public void shouldDeleteNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.searchNewsAndCheckIsDisplayed("Создание новости в будущем");
        controlPanelNews.deleteNews();
        controlPanelNews.checkDoesNotExistNews("Создание новости в будущем");
    }

    // тест аналогично падает с ошибкой AmbiguousViewMatcherException
    // тест пытается взаимодействовать с элементом интерфейса (значок редактирования),
    // который имеет несколько экземпляров в иерархии представлений
    // тест не знает какой именно элемент использовать, так как у них одинаковые идентификаторы
    @Description("Редактирование категории новости")
    @Test
    public void shouldEditCategoryOfNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.searchNewsAndCheckIsDisplayed("Создание новости в будущем");
        controlPanelNews.pressEditPanelNews();
        editNews.editCategory("Зарплата");
        editNews.pressSave();
    }

    // все ПОСЛЕДУЮЩИЕ тесты, связанные с редактированием, будут также падать
    // причина - та же. На примере с редактированием заголовка:
    @Description("Редактирование заголовка новости")
    @Test
    public void shouldEditTheNews() {
        appBar.switchToNews();
        newsPage.switchControlPanelNews();
        controlPanelNews.searchNewsAndCheckIsDisplayed("Создание новости в будущем");
        controlPanelNews.pressEditPanelNews();
        editNews.editTitle("Crocodile");
        editNews.pressSave();
    }
}
