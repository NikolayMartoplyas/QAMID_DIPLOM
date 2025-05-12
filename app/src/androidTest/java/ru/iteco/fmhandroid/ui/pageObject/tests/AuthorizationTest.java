package ru.iteco.fmhandroid.ui.pageObject.tests;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

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
import ru.iteco.fmhandroid.ui.pageObject.pageObject.WarningError;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AuthorizationTest {
    AuthorizationPage authorizationPage = new AuthorizationPage();
    AppBar appBar = new AppBar();
    MainPage mainPage = new MainPage();
    WarningError warningError = new WarningError();

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() {
        Espresso.onView(isRoot()).perform(Utils.waitDisplayed(appBar.getAppBarFragmentMain(), 15000));
        if (mainPage.isDisplayedButtonProfile()) {
            appBar.logOut();
        }
    }

    @Description("Авторизация с валидными данными")
    @Test
    public void successfulAuthorization() {
        authorizationPage.visibilityElement();
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        mainPage.isDisplayedButtonProfile();
    }

    @Description("Авторизация с некорректными данными")
    @Test
    public void authorizationFailedLogin() {
        authorizationPage.inputInFieldLogin("logiiin22");
        authorizationPage.inputInFieldPassword("passsword22");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с пробелами вместо логина")
    @Test
    public void authorizationSpacesInLogin() {
        authorizationPage.inputInFieldLogin("        ");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с пробелами вместо пароля")
    @Test
    public void authorizationSpacesInPassword() {
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("         ");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с пробелами в полях ввода логина и пароля")
    @Test
    public void authorizationSpacesInputsField() {
        authorizationPage.inputInFieldLogin("         ");
        authorizationPage.inputInFieldPassword("         ");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с пустым логином")
    @Test
    public void authorizationWithEmptyLogin() {
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация с пустым паролем")
    @Test
    public void authorizationWithEmptyPassword() {
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword(" ");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    // падает с ошибкой "No views in hierarchy found matching:
    // an instance of android.widget.TextView and view.getText()
    // with or without transformation to match: is "Логин и пароль не могут быть пустыми"
    // возможно, проблема связана с тем, что элемент находится в контейнере, который невидим
    // Если переключить проверку на вызов метода видимости другого элемента - закомментированную строку - то тест проходит
    @Description("Авторизация с пустыми полями ввода логина и пароля")
    @Test
    public void authorizationWhenEmptyInputFields() {
        authorizationPage.pressButton();
        warningError.windowEmptyInputField();
        //authorizationPage.visibilityElement();
    }

    // должен упасть, так как здесь ошибка - авторизация с таким методом ввода проходит
    @Description("Авторизация с пробелом в начале логина")
    @Test
    public void authorizationSpaceInBeginningOfLogin() {
        authorizationPage.inputInFieldLogin(" login2");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
        warningError.windowError();
    }

    // должен упасть, так как здесь ошибка - авторизация с таким методом ввода проходит
    @Description("Авторизация с прбелом в конце логина")
    @Test
    public void authorizationSpaceInTheEndOfLogin() {
        authorizationPage.inputInFieldLogin("login2 ");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
        warningError.windowError();
    }

    // должен упасть, так как здесь ошибка - авторизация с таким методом ввода проходит
    @Description("Авторизация с пробелом в начале пароля")
    @Test
    public void authorizationSpaceInBeginningOfPassword() {
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword(" password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
        warningError.windowError();
    }

    // должен упасть, так как здесь ошибка - авторизация с таким методом ввода проходит
    @Description("Авторизация с пробелом в конце пароля")
    @Test
    public void authorizationSpaceInTheEndOfPassword() {
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("password2 ");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
        warningError.windowError();
    }

    @Description("Авторизация. Логин в разном регистре")
    @Test
    public void authorizationUsingRegisterInLogin() {
        authorizationPage.inputInFieldLogin("LOgIn2");
        authorizationPage.inputInFieldPassword("password2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Авторизация. Пароль разным регистром")
    @Test
    public void authorizationUsingRegisterInPassword() {
        authorizationPage.inputInFieldLogin("login2");
        authorizationPage.inputInFieldPassword("PAssWOrD2");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }

    @Description("Ввод спецсимволов в поля ввода логина и пароля")
    @Test
    public void usingSpecialSymbolInFields() {
        authorizationPage.inputInFieldLogin("log#№%@<&?*");
        authorizationPage.inputInFieldPassword("pas#№%@<&?*");
        authorizationPage.pressButton();
        authorizationPage.visibilityElement();
    }
}
