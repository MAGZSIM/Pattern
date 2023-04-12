import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryOrder {

    @BeforeEach
    void setup () {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("FillingForm")
    void fillingForm() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("RU");
       int daysToFirstAppointment = 4;
       String firstAppointment = DataGenerator.generateDate(daysToFirstAppointment);
        int daysToSecondAppointment = 7;
        String secondAppointment = DataGenerator.generateDate(daysToSecondAppointment);
    $x("//span [@data-test-id = 'city'] //input").setValue(validUser.getCity());
    $x("//span [@data-test-id = 'date'] //input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    $x("//span [@data-test-id='date'] //input").sendKeys(firstAppointment);
    $x("//span [@data-test-id='name'] //input").setValue(validUser.getName());
    $x("//span [@data-test-id='phone'] //input").setValue(validUser.getPhone());
    $x("//label [@data-test-id='agreement']").click();
    $x("//span [@class = 'button__text']").click();
    $x("//div [@class = 'notification__content']")
        .shouldBe(visible, Duration.ofSeconds(15))
        .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstAppointment));
    $x("//span [@data-test-id = 'date'] //input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    $x("//span [@data-test-id='date'] //input").sendKeys(secondAppointment);
    $x("//span [@class = 'button__text']").click();
    $x("//div [@data-test-id='replan-notification']")
            .shouldBe(visible)
            .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
    $x("//span[@class = 'button__text'][contains(text(), 'Перепланировать')]").click();
    //$x("//div [@data-test-id='replan-notification']//button").click();
    $x("//div [@class = 'notification__content']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondAppointment));

    }
}
