package page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class DebitPaymentPage {

    private SelenideElement heading = $$(".heading").find(Condition.exactText("Оплата по карте"));
    private PaymentForm form;

    public  DebitPaymentPage() {

        heading.waitUntil(Condition.exist, 5000);

    }

    public PaymentForm form(){

        return new PaymentForm();
    }

}

