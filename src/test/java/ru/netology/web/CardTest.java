package ru.netology.web;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void firstPositiveTest() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").should(Condition.text("Встреча успешно забронирована на " + planDate)).should(Condition.visible);
    }

    @Test
    void negativeCityTest() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").should(Condition.text("Поле обязательно для заполнения")).should(Condition.visible);
    }

    @Test
    void negativeCityTest2() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москваaaaa");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='city'] .input__sub").should(Condition.text("Доставка в выбранный город недоступна")).should(Condition.visible);
    }

    @Test
    void negativeDateTest() {
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='date'] .input__inner .input__sub").should(Condition.text("Неверно введена дата")).should(Condition.visible);
    }

    @Test
    void negativeNameTest() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").should(Condition.text("Поле обязательно для заполнения")).should(Condition.visible);

    }

    @Test
    void negativeNameTest2() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='name'] .input__sub").should(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).should(Condition.visible);

    }

    @Test
    void negativePhoneTest() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("М М---М");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").should(Condition.text("Поле обязательно для заполнения")).should(Condition.visible);

    }

    @Test
    void negativePhoneTest2() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("М ---М");
        $("[data-test-id='phone'] input").setValue("79111+111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $("[data-test-id='phone'] .input__sub").should(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).should(Condition.visible);
    }

    @Test
    void negativeCheckTest() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("М ---М");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $$("button").find(text("Забронировать")).click();
        $(".input_invalid").should(Condition.visible);
    }

    @Test
    void firstPositiveTestForElements() {
        int days = 30;
        String planDate = generateDate(days, "d");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Каз");
        $$(".menu-item__control").findBy(text("Владикавказ")).click();
        $("[data-test-id='date'] input").click();
        if (!(generateDate(3, "MM") == generateDate(days, "MM"))) {
            $(".calendar__title>[data-step='1']").click();
        }
        $$("tbody td").findBy(text(planDate)).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").should(Condition.text("Встреча успешно забронирована на " + generateDate(days, "dd.MM.yyyy"))).should(Condition.visible);
    }
}