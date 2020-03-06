package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPaymentPage {

    private SelenideElement headingCredit = $$(".heading").find(Condition.exactText("Кредит по данным карты"));
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInputField = $("[placeholder='08']");
    private SelenideElement yearInputField = $("[placeholder='22']");
    private SelenideElement cvcInputField = $("[placeholder='999']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));
    private SelenideElement sendingButton = $(byText("Отправляем запрос в Банк..."));
    private SelenideElement successfullNotification = $(withText("Операция одобрена Банком."));
    private SelenideElement errorNotification = $(withText("Ошибка! Банк отказал в проведении операции."));

    private SelenideElement cardErrorText = $("div:nth-child(1) > span > span > span.input__sub");
    private SelenideElement monthErrorText = $("div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private SelenideElement yearErrorText = $("div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement cvcErrorText = $("div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement ownerErrorText = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");

    public CreditPaymentPage() {
        headingCredit.shouldBe(Condition.visible);
    }

    public void getCardNumber(DataHelper.CardNumber info) {
        cardNumberField.setValue(info.getCardNumber());
    }

    public void putCardData (String number, String month, String year, String owner, String code) {
        cardNumberField.setValue(number);
        monthInputField.setValue(month);
        yearInputField.setValue(year);
        ownerField.setValue(owner);
        cvcInputField.setValue(code);
        continueButton.click();
    }

    public void putValidDataApprovedCard(DataHelper.CardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        successfullNotification.waitUntil(Condition.visible, 35000);
    }
    public void putValidDataDeclinedCard(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        errorNotification.waitUntil(Condition.visible, 35000);
    }
    public void putValidDataDeclinedCard1(DataHelper.CardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        successfullNotification.waitUntil(Condition.visible, 35000);
    }


    public void checkAllInvalidData() {
        putCardData("123", "0", "0", " ", "7");
        cardErrorText.shouldHave(Condition.exactText("Неверный формат"));
        monthErrorText.shouldHave(Condition.exactText("Неверный формат"));
        yearErrorText.shouldHave(Condition.exactText("Неверный формат"));
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат"));
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void checkUnrealCardNumber(DataHelper.CardInfo info) {
        putCardData(info.getUnrealCardNum(), info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        errorNotification.waitUntil(Condition.visible, 35000);
    }

}

