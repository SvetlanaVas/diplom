package test;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DebitPaymentPage;
import page.CreditPaymentPage;
import page.ChoicePaymentPage;


public class PaymentPageTest {

    //Happy Tests
    @Test
    @DisplayName("должен быть успешно куплен тур  Approved дебетовой картой  при заполнении заявки валидными данными")
    void shouldBuyTourWithDebitApprovedCardAndValidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.getCardNumber(approvedCardInfo);
        debitPaymentPage.putValidDataApprovedCard(cardInfo);

    }

    //Bug
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined дебетовой картой при заполнении заявки валидными данными")
    void shouldGetErrorWithDebitDeclinedCardAndValidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.putValidDataDeclinedCard(cardInfo);
        debitPaymentPage.errorVerify();
    }

    @Test
    @DisplayName("должен быть успешно куплен тур Approved кредитной картой при заполнении заявки валидными данными")
    void shouldBuyTourWithCreditApprovedCardAndValidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPaymentPage = new CreditPaymentPage();
        creditPaymentPage.getCardNumber(approvedCardInfo);
        creditPaymentPage.putValidDataApprovedCard(cardInfo);
        creditPaymentPage.validVerify();
    }
    @Test
    @DisplayName("должен быть отказ в проведении операции с Declined кредитной картой при заполнении заявки валидными данными")
    void  shouldGetErrorWithCreditDeclinedCardAndValidData(){
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val creditPaymentPage = new CreditPaymentPage();
        creditPaymentPage.getCardNumber(declinedCardInfo);
        creditPaymentPage.putValidDataDeclinedCard(cardInfo);
        creditPaymentPage.errorVerify();
    }

    //Sad Tests
    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении всех полей невалидными значениями и дебетовой карте")
    void shouldGetErrorWithDebitCardAndAllInvalidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkAllInvalidData();
    }

    @Test
    @DisplayName("должна показаться строка-напоминание об ошибке при заполнении всех полей невалидными значениями и кредитной карте")
    void shouldGetErrorWithCreditCardAndAllInvalidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val creditPaymentPage = new CreditPaymentPage();
        creditPaymentPage.checkAllInvalidData();
    }
//Bug
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Владелец кириллицей при Approved дебетовой карте")
    void shouldGetErrorWithDebitCardAndInvalidOwner() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkRussianOwnerName(DataHelper.InvalidCardInfo.getInvalidCardInfo().getOwnerNameRus(),cardInfo);
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Месяц значением предыдущего месяца текущего года")
    void shouldGetErrorWithDebitCardAndPastMonth() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkPastMonth(DataHelper.InvalidCardInfo.getInvalidCardInfo().getPastMonth(),cardInfo);
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля Месяц")
    void shouldGetErrorWithCreditApprovedCardAndEmptyMonth() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkEmptyMonth(cardInfo);
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля Номер карты")
    void shouldGetErrorWithDebitCardAndEmptyCard() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkEmptyNumber(cardInfo);
    }
    @Test
    @DisplayName("должна появиться строка-напоминание о необходимости заполнения поля Владелец")
    void shouldGetErrorWithDebitCardAndEmptyOwner() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkEmptyOwner(cardInfo);
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при заполнения поля Год значением на 10 лет больше текущего года")
    void shouldGetErrorWithDebitCardAndFutureYear() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkFutureYear(DataHelper.InvalidCardInfo.getInvalidCardInfo().getFutureYear(),cardInfo);
    }
    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля Год")
    void shouldGetErrorWithDebitCardAndInvalidMonth() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkEmptyYear(cardInfo);
    }

    @Test
    @DisplayName("должна появиться строка-напоминание об ошибке при незаполнении поля CVC")
    void shouldGetErrorWithDebitCardAndEmptyCVC() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getValidCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.checkEmptyCode(cardInfo);
    }

}

