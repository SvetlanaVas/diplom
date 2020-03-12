package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPaymentPage {

    private SelenideElement headingCredit = $$(".heading").find(Condition.exactText("Кредит по данным карты"));


    public CreditPaymentPage() {
        headingCredit.shouldBe(Condition.visible);
    }


}

