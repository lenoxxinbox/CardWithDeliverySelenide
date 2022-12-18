package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CardWithDeliveryTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    public void eachTest() {

        Configuration.holdBrowserOpen = true;
        Configuration.startMaximized = true;
        open("http://localhost:9999");
    }

    @Test
    void scheduleDeliverySuccessfullySingleSurname() {
        String planningDate = generateDate(3);

        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void scheduleDeliverySuccessfullyDoubleSurnameWithSpace() {
        String planningDate = generateDate(4);

        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Двойная-Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void scheduleDeliverySuccessfullyDoubleHyphenatedName() {
        String planningDate = generateDate(5);

        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя-Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void scheduleDeliverySuccessfullyTenthDay() {
        String planningDate = generateDate(10);

        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryNotACity() {
        String planningDate = generateDate(10);

        $("[data-test-id=city] input").setValue("деревня");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=city].input_invalid .input__sub")).shouldHave(exactText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryZeroValueCity() {
        String planningDate = generateDate(3);

        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=city].input_invalid .input__sub")).shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryTomorrow() {
        String planningDate = generateDate(1);

        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldHave(exactText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryYesterday() {
        String planningDate = generateDate(-1);

        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldHave(exactText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryZeroValueDate() {
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldHave(exactText("Неверно введена дата")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryNameLatinAlphabet() {
        String planningDate = generateDate(5);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Last Name First Name");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryNameSpecialCharacters() {
        String planningDate = generateDate(5);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("#$%");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryZeroValueName() {
        String planningDate = generateDate(3);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryPhoneWithoutPlus() {
        String planningDate = generateDate(3);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void negativeScheduleDeliveryPhoneTenDigits() {
        String planningDate = generateDate(5);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+7800555555");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);

    }

    @Test
    void negativeScheduleDeliveryPhoneTwelveDigits() {
        String planningDate = generateDate(5);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+780055555500");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);

    }

    @Test
    void negativeScheduleDeliveryZeroValuePhone() {
        String planningDate = generateDate(5);

        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    @Test
    void negativeScheduleDeliveryCheckboxNotMarked() {
        String planningDate = generateDate(30);

        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='agreement'].input_invalid")).shouldBe(Condition.visible);
    }

    @Test
    void scheduleDeliveryDropDownListOfCities() {
        String planningDate = generateDate(50);
        $("[data-test-id=city] input").setValue("Пе");
        $x("//span[contains(text(),'Камчатский')]").click();
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
