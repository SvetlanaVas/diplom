package test;
import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
//import data.AppProp;
import data.DataHelper.*;
import page.DebitPaymentPage;
import page.CreditPaymentPage;
import page.ChoicePaymentPage;
import sqlUtils.SQLutils;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static sqlUtils.SQLutils.*;




public class PaymentPageTest {
    static CardInfo cardInfo;
    //static AppProp props;


    @BeforeEach
    @DisplayName("Чистит базу данных перед каждым тестом")
    void cleanBase() throws SQLException {
       SQLutils.cleanDB();
    }

    /*@BeforeAll
    static void setupAll() {
        //SelenideLogger.addListener("allure", new AllureSelenide());
        cardInfo = DataHelper.getCardInfo();
        //props = AppProp.getAppProp();
    }

    /*@AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }*/

   static DebitPaymentPage getDebitPaymentPage () {
       val choicePaymentPage = new ChoicePaymentPage();
       choicePaymentPage.openChoicePaymentPage();
       choicePaymentPage.openDebitPaymentPage();
       return new DebitPaymentPage();
   }

    static CreditPaymentPage getCreditPaymentPage () {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        return new CreditPaymentPage();
    }
    //Happy Tests
    @Test
    @DisplayName("должен быть успешно куплен тур  Approved дебетовой картой  при заполнении заявки валидными данными")
    void shouldBuyTourWithDebitApprovedCardAndValidData() {
       val debitPaymentPage = getDebitPaymentPage();
       debitPaymentPage.putValidDataApprovedCard(cardInfo);

    }

    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined дебетовой картой при заполнении заявки валидными данными")
    void shouldGetErrorWithDebitDeclinedCardAndValidData() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.putValidDataDeclinedCard(cardInfo);

    }

    @Test
    @DisplayName("должен быть успешно куплен тур Approved кредитной картой при заполнении заявки валидными данными")
    void shouldBuyTourWithCreditApprovedCardAndValidData() {
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.putValidDataApprovedCard(cardInfo);

    }
    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined кредитной картой при заполнении заявки валидными данными")
    void  shouldGetErrorWithCreditDeclinedCardAndValidData(){
        val creditPaymentPage = getCreditPaymentPage();
        creditPaymentPage.putValidDataDeclinedCard(cardInfo);
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
    void shouldGetErrorWithDebitCardAndInvalidOwner() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkRussianOwnerName(cardInfo.getOwnerNameRus(),cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Месяц значением предыдущего месяца текущего года")
    void shouldGetErrorWithDebitCardAndPastMonth() {
        val debitPaymentPage = getDebitPaymentPage();
        debitPaymentPage.checkPastMonth(cardInfo.getPastMonth(),cardInfo);
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
        debitPaymentPage.checkFutureYear(cardInfo.getFutureYear(),cardInfo);
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
    void shouldGetErrorWithCreditCardAndUnrealCard(){
       val creditPaymentPage = getCreditPaymentPage();
       creditPaymentPage.checkUnrealCardNumber(cardInfo);
    }
}