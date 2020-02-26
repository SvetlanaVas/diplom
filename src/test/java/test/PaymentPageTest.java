package test;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.DebitPaymentPage;
import page.CreditPaymentPage;
import page.ChoicePaymentPage;

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
}
