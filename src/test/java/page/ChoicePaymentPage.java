package page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;


public class ChoicePaymentPage {
    private SelenideElement debitButton = $$("button").find(Condition.exactText("Купить"));
    private SelenideElement creditButton = $$("button").find(Condition.exactText("Купить в кредит"));


    public void openChoicePaymentPage() {
        open("http://localhost:8080");
    }

    public void openDebitPaymentPage() {
        debitButton.click();
    }

    public void openCreditPaymentPage() {
        creditButton.click();
    }
}

