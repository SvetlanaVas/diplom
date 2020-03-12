package page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitPaymentPage {

    private SelenideElement heading = $$(".heading").find(Condition.exactText("Оплата по карте"));

    public DebitPaymentPage() {

        heading.waitUntil(Condition.exist, 5000);
    }

}

