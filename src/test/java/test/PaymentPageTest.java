package test;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import data.DataHelper.*;
import page.DebitPaymentPage;
import page.CreditPaymentPage;
import page.ChoicePaymentPage;
import sqlUtils.SQLutils;
import java.util.Properties;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static sqlUtils.SQLutils.*;

import java.sql.SQLException;



public class PaymentPageTest {
    static CardInfo cardInfo;


    @BeforeEach
    @DisplayName("Чистит базу данных перед каждым тестом")
    void cleanBase() throws SQLException {
        SQLutils.cleanDB();
    }

    @BeforeAll
    static void setupAll() throws SQLException {
        SelenideLogger.addListener("allure", new AllureSelenide());
        cardInfo = DataHelper.getCardInfo();

    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    static DebitPaymentPage getDebitPaymentPage() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        return new DebitPaymentPage();
    }

    static CreditPaymentPage getCreditPaymentPage() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        return new CreditPaymentPage();
    }


    //Happy Tests
    @Test
    @DisplayName("должен быть успешно куплен тур  Approved дебетовой картой  при заполнении заявки валидными данными, в соответствующих таблицах БД появляются записи")
    void shouldBuyTourWithDebitApprovedCardAndValidData() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.putValidDataApprovedCard(cardInfo);
        val paymentEntityId = getPaymentEntityId(DataHelper.approvedCardInfo().getStatus());
        assertNotEquals("", paymentEntityId);
        val orderId = getOrderEntityId(paymentEntityId);
        assertNotEquals("", orderId);
    }

    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined дебетовой картой при заполнении заявки валидными данными")
    void shouldGetErrorWithDebitDeclinedCardAndValidData() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.putValidDataDeclinedCard(cardInfo);
        //если бы тест не упал, то далее были бы осуществлены проверки на появление записи в transaction_id таблицы payment_entity и в поле payment_id таблицы order_entity
        /*val paymentEntityId = getPaymentEntityId(DataHelper.declinedCardInfo().getStatus());
        assertNotEquals("", paymentEntityId);
        val orderId = getOrderEntityId(paymentEntityId);
        assertNotEquals("", orderId);*/
    }

    @Test
    @DisplayName("должен быть успешно куплен тур Approved кредитной картой при заполнении заявки валидными данными, в соответствующих таблицах БД появляются записи")
    void shouldBuyTourWithCreditApprovedCardAndValidData() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.putValidDataApprovedCard(cardInfo);
        val creditRequestEntityId = getCreditRequestEntityId(DataHelper.approvedCardInfo().getStatus());
        assertNotEquals("", creditRequestEntityId);
        val orderId = getOrderEntityId(creditRequestEntityId);
        assertNotEquals("", orderId);
    }

    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined кредитной картой при заполнении заявки валидными данными")
    void shouldGetErrorWithCreditDeclinedCardAndValidData() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.putValidDataDeclinedCard(cardInfo);
        //если бы тест не упал, то далее были бы осуществлены проверки на появление записи в поле bank_id таблицы credit_request_entity и в поле payment_id таблицы order_entity
        /*val creditRequestEntityId = getCreditRequestEntityId(DataHelper.declinedCardInfo().getStatus());
        assertNotEquals("", creditRequestEntityId);
        val orderId = getOrderEntityId(creditRequestEntityId);
        assertNotEquals("", orderId);*/
    }

    @Test
    @DisplayName("должен быть получен ответ Approved от эмулятора банковского сервиса, если статус дебетовой карты Approved")
    void shouldGetResponseApprovedIfApprovedDebitCard() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.putValidDataApprovedCard(cardInfo);
        val actual = DataHelper.approvedCardInfo().getStatus();
        val expected = getDebitCardStatus();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен быть получен ответ Approved от эмулятора банковского сервиса, если статус кредитной карты Approved")
    void shouldGetResponseApprovedIfApprovedCreditCard() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.putValidDataApprovedCard(cardInfo);
        val actual = DataHelper.approvedCardInfo().getStatus();
        val expected = getCreditCardStatus();
        assertEquals(expected, actual);
    }


    //Sad Tests
    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении всех полей невалидными значениями и дебетовой карте")
    void shouldGetErrorWithDebitCardAndAllInvalidData() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkAllInvalidData();
    }

    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении всех полей невалидными значениями и кредитной карте")
    void shouldGetErrorWithCreditCardAndAllInvalidData() {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.checkAllInvalidData();
    }

    //Bug
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Владелец кириллицей при Approved дебетовой карте")
    void shouldGetErrorWithDebitCardAndRusNameOwner() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkRussianOwnerName(cardInfo.getOwnerNameRus(), cardInfo);
    }

    //Bug
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Владелец кириллицей при Approved кредитной карте")
    void shouldGetErrorWithCreditCardAndRusNameOwner() {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.checkRussianOwnerName(cardInfo.getOwnerNameRus(), cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Месяц значением предыдущего месяца текущего года")
    void shouldGetErrorWithDebitCardAndPastMonth() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkPastMonth(cardInfo.getPastMonth(), cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля Месяц")
    void shouldGetErrorWithDebitApprovedCardAndEmptyMonth() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkEmptyMonth(cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля Номер карты")
    void shouldGetErrorWithDebitCardAndEmptyCard() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkEmptyNumber(cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание о необходимости заполнения поля Владелец")
    void shouldGetErrorWithDebitCardAndEmptyOwner() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkEmptyOwner(cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Год значением на 10 лет больше текущего года")
    void shouldGetErrorWithDebitCardAndFutureYear() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkFutureYear(cardInfo.getFutureYear(), cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля Год")
    void shouldGetErrorWithDebitCardAndInvalidMonth() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkEmptyYear(cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля CVC")
    void shouldGetErrorWithDebitCardAndEmptyCVC() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkEmptyCode(cardInfo);
    }

    @Test
    @DisplayName("должна появиться сообщение об ошибке и отклонении операции при заполнении поля Номер карты несуществующим значением")
    void shouldGetErrorWithCreditCardAndUnrealCard() {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.checkUnrealCardNumber(cardInfo);
    }
}