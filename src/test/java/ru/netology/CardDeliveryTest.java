package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    @BeforeAll
    static void setUpAll() {
        Configuration.baseUrl = "http://localhost:9999";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("/");
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void shouldReplanMeetingDate() {
        DataGenerator.UserInfo user = DataGenerator.Registration.generateUser("ru");
        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        $$("[data-test-id='city'] input").first().setValue(user.getCity());
        $$("[data-test-id='date'] input").first().doubleClick().sendKeys(Keys.BACK_SPACE);
        $$("[data-test-id='date'] input").first().setValue(firstDate);
        $$("[data-test-id='name'] input").first().setValue(user.getName());
        $$("[data-test-id='phone'] input").first().setValue(user.getPhone());
        $("[data-test-id='agreement']").click();
        $$("button").findBy(Condition.exactText("Запланировать")).click();

        $("[data-test-id='success-notification'].notification_visible")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstDate));

        $$("[data-test-id='date'] input").first().doubleClick().sendKeys(Keys.BACK_SPACE);
        $$("[data-test-id='date'] input").first().setValue(secondDate);
        $$("button").findBy(Condition.exactText("Запланировать")).click();

        $("[data-test-id='replan-notification'].notification_visible")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").findBy(Condition.exactText("Перепланировать")).click();

        $("[data-test-id='success-notification'].notification_visible")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondDate));
    }
}
