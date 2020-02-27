package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class InvalidData {
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInputField = $("[placeholder='08']");
    private SelenideElement yearInputField = $("[placeholder='22']");
    private SelenideElement cvcInputField = $("[placeholder='999']");
    private SelenideElement ownerField = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__box > input");
    private SelenideElement continueButton = $$("button").find(Condition.exactText("Продолжить"));


    private SelenideElement cardErrorText = $("div:nth-child(1) > span > span > span.input__sub");
    private SelenideElement monthErrorText = $("div:nth-child(2) > span > span:nth-child(1) > span > span > span.input__sub");
    private SelenideElement yearErrorText = $("div:nth-child(2) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement cvcErrorText = $("div:nth-child(3) > span > span:nth-child(2) > span > span > span.input__sub");
    private SelenideElement ownerErrorText = $("div:nth-child(3) > span > span:nth-child(1) > span > span > span.input__sub");

    public void putEmptyCardNumber(DataHelper.CardInfo info) {
        cardNumberField.setValue("");
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        cardErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void getCardNumber(DataHelper.CardNumber info) {
        cardNumberField.setValue(info.getCardNumber());
    }

    public void putEmptydMonth(DataHelper.CardInfo info) {
        monthInputField.setValue("");
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        monthErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void putEmptyYear(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue("");
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        yearErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void putEmptyCVC(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue("");
        ownerField.setValue(info.getOwner());
        continueButton.click();

        cvcErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void putInvalidCVC(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue("7");
        ownerField.setValue(info.getOwner());
        continueButton.click();

        cvcErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void putEmptyOwner(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue("");
        continueButton.click();

        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    public void putInvalidMonth(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue("20");
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();

        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }
}
