package test;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import page.PaymentForm;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import data.DataHelper.*;
import page.DebitPaymentPage;
import page.CreditPaymentPage;
import page.ChoicePaymentPage;
import sqlUtils.SQLutils;
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
    @DisplayName("должен быть успешно куплен тур  Approved дебетовой картой  при заполнении заявки валидными данными, в таблицах Order_Entity и Payment_entity БД появляются записи")
    void shouldBuyTourWithDebitApprovedCardAndValidData() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        //val metods = new PaymentForm();
        val metods = debitPaymentPage.form();
        metods.putValidDataApprovedCard(cardInfo);
        val paymentEntityId = getPaymentEntityId(DataHelper.approvedCardInfo().getStatus());
        assertNotEquals("", paymentEntityId);
        val orderId = getOrderEntityId(paymentEntityId);
        assertNotEquals("", orderId);
    }

    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined дебетовой картой при заполнении заявки валидными данными, в таблице Order_Entity БД не должно быть записей")
    void shouldGetErrorWithDebitDeclinedCardAndValidData() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.putValidDataDeclinedCard(cardInfo);
        val paymentEntityId = getPaymentEntityId(DataHelper.declinedCardInfo().getStatus());
        assertNotEquals("", paymentEntityId);
        checkEmptyOrderEntity();

    }

    @Test
    @DisplayName("должен быть успешно куплен тур Approved кредитной картой при заполнении заявки валидными данными, в  таблицах Order_Entity и Credit_request_entity  БД появляются записи")
    void shouldBuyTourWithCreditApprovedCardAndValidData() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.putValidDataApprovedCard(cardInfo);
        val creditRequestEntityId = getCreditRequestEntityId(DataHelper.approvedCardInfo().getStatus());
        assertNotEquals("", creditRequestEntityId);
        val orderId = getOrderEntityId(creditRequestEntityId);
        assertNotEquals("", orderId);
    }

    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined кредитной картой при заполнении заявки валидными данными, в таблице Order_Entity БД не должно быть записей")
    void shouldGetErrorWithCreditDeclinedCardAndValidData() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.putValidDataDeclinedCard(cardInfo);
        val creditRequestEntityId = getCreditRequestEntityId(DataHelper.declinedCardInfo().getStatus());
        assertNotEquals("", creditRequestEntityId);
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должен быть получен ответ Approved от эмулятора банковского сервиса, если статус дебетовой карты Approved")
    void shouldGetResponseApprovedIfApprovedDebitCard() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.putValidDataApprovedCard(cardInfo);
        val actual = DataHelper.approvedCardInfo().getStatus();
        val expected = getDebitCardStatus();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("должен быть получен ответ Approved от эмулятора банковского сервиса, если статус кредитной карты Approved")
    void shouldGetResponseApprovedIfApprovedCreditCard() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.putValidDataApprovedCard(cardInfo);
        val actual = DataHelper.approvedCardInfo().getStatus();
        val expected = getCreditCardStatus();
        assertEquals(expected, actual);
    }
//BUG
    @Test
    @DisplayName("Должен быть получен ответ Declined, если статус дебетовой карты Declined, в таблице Order_Entity БД не должно быть записей")
    void shouldGetResponseDeclinedIfDeclinedDebitCard() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val actual = DataHelper.declinedCardInfo().getStatus();
        val metods = debitPaymentPage.form();
        metods.checkValidDataDeclinedCard(cardInfo);
        val expected = getDebitCardStatus();
        assertEquals(expected, actual);
        val paymentEntityId = getPaymentEntityId(actual);
        assertNotEquals("", paymentEntityId);
        checkEmptyOrderEntity();
    }
    //BUG
    @Test
    @DisplayName("Должен быть получен ответ Declined, если статус кредитной карты Declined, в таблице Order_Entity БД не должно быть записей")
    void shouldGetResponseDeclinedIfDeclinedCreditCard() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val actual = DataHelper.declinedCardInfo().getStatus();
        val metods = creditPaymentPage.form();
        metods.checkValidDataDeclinedCard(cardInfo);
        val expected = getCreditCardStatus();
        assertEquals(expected, actual);
        val creditEntityId = getCreditRequestEntityId(actual);
        assertNotEquals("", creditEntityId);
        checkEmptyOrderEntity();
    }

    //Sad Tests
    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении всех полей невалидными значениями и дебетовой Approved карте, в полях Order_Entity и Payment_entity БД не должно быть записей")
    void shouldGetErrorWithDebitCardAndAllInvalidData() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkAllInvalidData();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении всех полей невалидными значениями и кредитной Approved карте, в полях Order_Entity и Credit_request_entity БД не должно быть записей")
    void shouldGetErrorWithCreditCardAndAllInvalidData() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkAllInvalidData();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }
    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении полей Год и Месяц невалидными значениями и дебетовой карте, в полях Order_Entity и Payment_entity БД не должно быть записей")
    void shouldGetErrorWithDebitCardAndInvalidYearAndMonth() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkInvalidYearAndMonth(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }
    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении полей Год и Месяц невалидными значениями и кредитной карте, в полях Order_Entity и Credit_request_entity БД не должно быть записей")
    void shouldGetErrorWithCreditCardAndInvalidYearAndMonth() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkInvalidYearAndMonth(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }
    //Bug
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Владелец кириллицей при Approved дебетовой карте, в полях Order_Entity и Payment_entity БД не должно быть записей")
    void shouldGetErrorWithDebitCardAndRusNameOwner() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkRussianOwnerName(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    //Bug
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Владелец кириллицей при Approved кредитной карте, в полях Order_Entity и Credit_request_entity БД не должно быть записе")
    void shouldGetErrorWithCreditCardAndRusNameOwner() throws SQLException{
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkRussianOwnerName(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Месяц значением предыдущего месяца текущего года при Approved дебетовой карте")
    void shouldGetErrorWithDebitCardAndPastMonth() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkPastMonth(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Месяц значением предыдущего месяца текущего года при Approved кредитной карте")
    void shouldGetErrorWithCreditCardAndPastMonth() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkPastMonth(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении всех полей и Approved дебетовой карте")
    void shouldGetErrorWithDebitCardAndEmptyData() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkEmptyData();
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении всех полей и Approved кредитной карте")
    void shouldGetErrorWithCreditCardAndEmptyData() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkEmptyData();
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении  поля Номер дебетовой карты символами")
    void shouldGetErrorWithDebitApprovedCardAndTextInNumber() throws SQLException{
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkTextInCardNumberField(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении  поля Номер кредитной карты символами")
    void shouldGetErrorWithCreditApprovedCardAndTextInNumber() throws SQLException{
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkTextInCardNumberField(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }
    //Bug. Появляется сообщение Успешно!
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении  поля Владелец дебетовой  Approved карты произвольными символами g4hj$$$uy&tr")
    void shouldGetErrorWithDebitApprovedCardAndSymbolsInOwner() throws SQLException{
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkSymbolsInOwnerField(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Появляется сообщение Успешно!
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении  поля Владелец кредитной Approved карты произвольными символами g4hj$$$uy&tr")
    void shouldGetErrorWithCreditApprovedCardAndSymbolsInOwner() throws SQLException{
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkSymbolsInOwnerField(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Под полем Владелец появляется сообщение Поле обязательно для заполнения.
    @Test
    @DisplayName("должна появиться строка-напоминание об неверном формате при заполнении  всех полей дебетовой  Approved карты латиницей")
    void shouldGetErrorWithDebitApprovedCardAndLiterasInDataFields() throws SQLException{
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkLiterasInAllFields(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Под полем Владелец появляется сообщение Поле обязательно для заполнения.
    @Test
    @DisplayName("должна появиться строка-напоминание об неверном формате при заполнении  всех полей кредитной Approved карты латиницей")
    void shouldGetErrorWithCreditApprovedCardAndLiterasInDataFields() throws SQLException{
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkLiterasInAllFields(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Появляется сообщение Успешно!
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении поля Номер несуществующей дебетовой картой")
    void shouldGetErrorWithDebitApprovedCardAndUnrealNumber() throws SQLException{
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkUnrealCardNumber(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Появляется сообщение Успешно!
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении поля Номер несуществующей кредитной картой")
    void shouldGetErrorWithCreditApprovedCardAndUnrealNumber() throws SQLException{
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkUnrealCardNumber(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Появляется сообщение Успешно!
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении цифрами поля Владелец Approved дебитовой карты")
    void shouldGetErrorWithApprovedDebitCardAndNumberInOwner() throws SQLException{
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkNumbersInOwnerField(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    //Bug. Появляется сообщение Успешно!
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнении цифрами поля Владелец Approved кредитной карты")
    void shouldGetErrorWithApprovedCreditCardAndNumberInOwner() throws SQLException{
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkNumbersInOwnerField(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Год значением на 10 лет больше текущего года при Approved дебетовой карте")
    void shouldGetErrorWithDebitCardAndFutureYear() throws SQLException {
        val debitPaymentPage = getDebitPaymentPage();
        val metods = debitPaymentPage.form();
        metods.checkFutureYear(cardInfo);
        checkEmptyPaymentEntity();
        checkEmptyOrderEntity();
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Год значением на 10 лет больше текущего года при Approved кредитной карте")
    void shouldGetErrorWithreditCardAndFutureYear() throws SQLException {
        val creditPaymentPage = getCreditPaymentPage();
        val metods = creditPaymentPage.form();
        metods.checkFutureYear(cardInfo);
        checkEmptyCreditEntity();
        checkEmptyOrderEntity();
    }

}
