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

    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void firstPositiveTest() {
        String planDate = generateDate(4,"dd.MM.yyyy");
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
        String actual = $("[data-test-id='notification'] .notification__content").text();
        assertEquals("Встреча успешно забронирована на "+ planDate, actual.trim());
    }

    @Test
    void negativeCityTest() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        String actual = $("[data-test-id='city'] .input__sub").text();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void negativeCityTest2() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москваaaaa");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        String actual = $("[data-test-id='city'] .input__sub").text();
        assertEquals("Доставка в выбранный город недоступна", actual.trim());
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
        String actual = $("[data-test-id='date'] .input__inner .input__sub").text();
        assertEquals("Неверно введена дата", actual.trim());
    }

    @Test
    void negativeNameTest() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        String actual = $("[data-test-id='name'] .input__sub").text();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void negativeNameTest2() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        String actual = $("[data-test-id='name'] .input__sub").text();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual.trim());
    }

    @Test
    void negativePhoneTest() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("М М---М");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        String actual = $("[data-test-id='phone'] .input__sub").text();
        assertEquals("Поле обязательно для заполнения", actual.trim());
    }

    @Test
    void negativePhoneTest2() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("М ---М");
        $("[data-test-id='phone'] input").setValue("79111+111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        String actual = $("[data-test-id='phone'] .input__sub").text();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual.trim());
    }

    @Test
    void negativeCheckTest() {
        String planDate = generateDate(4,"dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planDate);
        $("[data-test-id='name'] input").setValue("М ---М");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $$("button").find(text("Забронировать")).click();
        boolean actual = $(".input_invalid").isDisplayed();
        assertTrue(actual);
    }

    @Test
    void firstPositiveTestForElements() {
        int days = 30;
        String planDate = generateDate(days,"d");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Каз");
        $$(".menu-item__control").findBy(text("Владикавказ")).click();
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        if(!(LocalDate.now().format(DateTimeFormatter.ofPattern("MM"))==LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("MM")))){
            $(".calendar__title>[data-step='1']").click();
        }
        $(".input__icon button").click();
        $$("tbody td").findBy(text(planDate)).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement'] span").click();
        $$("button").find(text("Забронировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.visible, Duration.ofSeconds(15));
        String actual = $("[data-test-id='notification'] .notification__content").text();
        assertEquals("Встреча успешно забронирована на "+ generateDate(days,"dd.MM.yyyy"), actual.trim());
    }
}