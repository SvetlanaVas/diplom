package test;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DebitPaymentPage;
import page.CreditPaymentPage;
import page.ChoicePaymentPage;
import page.InvalidData;

public class PaymentPageTest {
    @Test
    @DisplayName("should get success notification if pay by approved debit card and valid data card")
    void shouldBuyTourWithDebitApprovedCardAndValidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.getCardNumber(approvedCardInfo);
        debitPaymentPage.getValidCardData(cardInfo);
        debitPaymentPage.validVerify();
    }
    @Test
    @DisplayName("should get error notification if pay by declined debit card and valid data card ")
    void shouldGetErrorWithDebitDeclinedCardAndValidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.getValidCardData(cardInfo);
        debitPaymentPage.errorVerify();
    }
    @Test
    @DisplayName("should get success notification if pay by approved credit card and valid data card")
    void shouldBuyTourWithCreditApprovedCardAndValidData() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        val creditPaymentPage = new CreditPaymentPage();
        creditPaymentPage.getCardNumber(approvedCardInfo);
        creditPaymentPage.getValidCardData(cardInfo);
        creditPaymentPage.validVerify();
    }
    @Test
    @DisplayName("should get error notification if pay by declined credit card and valid data card")
    void  shouldGetErrorWithCreditDeclinedCardAndValidData(){
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val declinedCardInfo = DataHelper.declinedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        val creditPaymentPage = new CreditPaymentPage();
        creditPaymentPage.getCardNumber(declinedCardInfo);
        creditPaymentPage.getValidCardData(cardInfo);
        creditPaymentPage.errorVerify();
    }
    @Test
    @DisplayName("should get error text if pay by approved debit card and invalid name owner's card")
    void shouldGetErrorWithDebitApprovedCardAndInvalidOwner() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfoRus();
        val debitPaymentPage = new DebitPaymentPage();
        debitPaymentPage.getCardNumber(approvedCardInfo);
        debitPaymentPage.getValidCardData(cardInfo);
        debitPaymentPage.errorVerify();
    }
    @Test
    @DisplayName("should get error text if pay by approved credit card and invalid name owner's card")
    void shouldGetErrorWithCreditApprovedCardAndInvalidOwner() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfoRus();
        val creditPaymentPage = new CreditPaymentPage();
        creditPaymentPage.getCardNumber(approvedCardInfo);
        creditPaymentPage.getValidCardData(cardInfo);
        creditPaymentPage.errorVerify();
    }
    @Test
    @DisplayName("should get error text if pay by approved credit card and empty name owner's card")
    void shouldGetErrorWithCreditApprovedCardAndEmptyOwner() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putEmptyOwner(cardInfo);
    }
    @Test
    @DisplayName("should get error text if pay by approved debit card and empty card number")
    void shouldGetErrorWithDebitApprovedCardAndEmptyCard() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val invalidData = new InvalidData();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.putEmptyCardNumber(cardInfo);
    }
    @Test
    @DisplayName("should get error text if pay by approved credit card and empty month")
    void shouldGetErrorWithCreditApprovedCardAndEmptyMonth() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putEmptydMonth(cardInfo);
    }
    @Test
    @DisplayName("should get error text if pay by approved debit card and invalid month")
    void shouldGetErrorWithDebitApprovedCardAndInvalidMonth() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo1();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putInvalidMonth(cardInfo);
    }
    @Test
    @DisplayName("should get error text if pay by approved credit card and empty year")
    void shouldGetErrorWithCreditApprovedCardAndEmptyYear() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putEmptyYear(cardInfo);
    }
    @Test
    @DisplayName("should get error text if pay by approved credit card and empty cvc")
    void shouldGetErrorWithCreditApprovedCardAndEmptyCVC() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openCreditPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putEmptyCVC(cardInfo);
    }
    @Test
    @DisplayName("should get error text if pay by approved credit card and invalid cvc")
    void shouldGetErrorWithCreditApprovedCardAndInvalidCVC() {
        val choicePaymentPage = new ChoicePaymentPage();
        choicePaymentPage.openChoicePaymentPage();
        choicePaymentPage.openDebitPaymentPage();
        val invalidData = new InvalidData();
        val approvedCardInfo = DataHelper.approvedCardInfo();
        val cardInfo = DataHelper.getCardInfo();
        invalidData.getCardNumber(approvedCardInfo);
        invalidData.putInvalidCVC(cardInfo);
    }
}

