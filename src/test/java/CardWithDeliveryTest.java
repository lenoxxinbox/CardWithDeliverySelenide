import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class CardWithDeliveryTest {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();

    @BeforeEach
    public void eachTest() {

        Configuration.holdBrowserOpen = true;
        Configuration.startMaximized = true;
        open("http://localhost:9999");
    }

    @Test
    void scheduleDeliverySuccessfullySingleSurname() {
        Date newDate = DateUtils.addDays(date, 3);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void scheduleDeliverySuccessfullyDoubleSurnameWithSpace() {
        Date newDate = DateUtils.addDays(date, 4);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Двойная Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void scheduleDeliverySuccessfullyDoubleHyphenatedName() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя-Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void scheduleDeliverySuccessfullyTenthDay() {
        Date newDate = DateUtils.addDays(date, 10);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    void negativeScheduleDeliveryNotACity() {
        Date newDate = DateUtils.addDays(date, 10);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("деревня");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=city].input_invalid .input__sub")).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void negativeScheduleDeliveryZeroValueCity() {
        Date newDate = DateUtils.addDays(date, 10);
        String value =  formatter.format(newDate);
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=city].input_invalid .input__sub")).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void negativeScheduleDeliveryTomorrow() {
        Date newDate = DateUtils.addDays(date, 1);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void negativeScheduleDeliveryYesterday() {
        Date newDate = DateUtils.addDays(date, -1);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldHave(exactText("Заказ на выбранную дату невозможен"));
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

        $(By.cssSelector("[data-test-id='date'] span.input__sub")).shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void negativeScheduleDeliveryNameLatinAlphabet() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Last Name First Name");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void negativeScheduleDeliveryNameSpecialCharacters() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("#$%");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void negativeScheduleDeliveryZeroValueName() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void negativeScheduleDeliveryPhoneWithoutPlus() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void negativeScheduleDeliveryPhoneTenDigits() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+7800555555");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void negativeScheduleDeliveryPhoneTwelveDigits() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+780055555500");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void negativeScheduleDeliveryZeroValuePhone() {
        Date newDate = DateUtils.addDays(date, 5);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Рязань");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void negativeScheduleDeliveryCheckboxNotMarked() {
        Date newDate = DateUtils.addDays(date, 10);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Волгоград");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $(By.className("button")).click();

        $(By.cssSelector("[data-test-id='agreement'].input_invalid")).shouldBe(Condition.visible);
    }

    @Test
    void scheduleDeliveryDropDownListOfCities() {
        Date newDate = DateUtils.addDays(date, 10);
        String value =  formatter.format(newDate);
        $("[data-test-id=city] input").setValue("Петропа");
        $x("//span[contains(text(),'Камчатский')]").click();
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(value);
        $("[data-test-id=name] input").setValue("Фамилия Имя");
        $("[data-test-id=phone] input").setValue("+78005555550");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();

        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}
