import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;
import static org.openqa.selenium.By.partialLinkText;

@DisplayName(value = "Тесты представлены в трех вариантах")
public class ThreeVariantsTests {

    private static final String REPOSITORY_NAME = "iProkurator/qa_guru_hw6";
    private static final int ISSUE_NUMBER = 1;


    @Test
    @Owner("PilatovPavel")
    @DisplayName("1. Чистый Selenide (с Listener)")
    public void testPureSelenide() throws InterruptedException {

        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com/");
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(REPOSITORY_NAME);
        $(".header-search-input").submit();

        $(linkText("iProkurator/qa_guru_hw6")).click();
        $(partialLinkText("Issues")).click();
        $(withText("#" + ISSUE_NUMBER)).should(Condition.visible);

    }

    @Test
    @Owner("PilatovPavel")
    @DisplayName("2. Лямбда шаги через step (name, () -> {})")
    public void testLambdaSteps() {

        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com/");
        });
        step("Ищем репозиторий " + REPOSITORY_NAME, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(REPOSITORY_NAME);
            $(".header-search-input").submit();
        });
        step("Переходим по ссылке репозитория" + REPOSITORY_NAME, () -> {
            $(linkText(REPOSITORY_NAME)).click();
        });
        step("Кликаем на таб Issues", () -> {
            $(partialLinkText("Issues")).click();
        });
        step("Проверяем, что существует Issue с номером " + ISSUE_NUMBER, () -> {
            $(withText("#" + ISSUE_NUMBER)).click();
        });

    }

    @Test
    @Owner("PilatovPavel")
    @DisplayName("3. Шаги с аннотацией @Step")
    public void testGitHubIssue() {

        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY_NAME);
        steps.clickOnRepositoryLink(REPOSITORY_NAME);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE_NUMBER);
    }

}

