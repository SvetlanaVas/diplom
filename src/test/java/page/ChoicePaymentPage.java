package page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.*;

public class ChoicePaymentPage {

    private SelenideElement debitButton = $$("button").find(Condition.exactText("Купить"));
    private SelenideElement creditButton = $$("button").find(Condition.exactText("Купить в кредит"));


    public void openChoicePaymentPage() {
        open("http://localhost:8080");
    }


    public DebitPaymentPage openDebitPaymentPage() {
        debitButton.click();
        return page(DebitPaymentPage.class);
    }

    public CreditPaymentPage openCreditPaymentPage() {
        creditButton.click();
        return page(CreditPaymentPage.class);
    }
}