package page;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;


import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DebitPaymentPage {
    private SelenideElement heading = $$(".heading").find(Condition.exactText("Оплата по карте"));
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

    public DebitPaymentPage() {

        heading.waitUntil(Condition.exist, 5000);
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

    public void putValidDataApprovedCard(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        successfullNotification.waitUntil(Condition.visible, 35000);
    }
    public void putValidDataDeclinedCard(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.declinedCardInfo().getCardNumber(), info.getMonth(), info.getYear(),
                info.getOwner(), info.getCvc());
        errorNotification.waitUntil(Condition.visible, 35000);
    }

    public void checkAllInvalidData() {
        putCardData("123", "0", "0", " ", "7");
        cardErrorText.shouldHave(Condition.exactText("Неверный формат"));
        monthErrorText.shouldHave(Condition.exactText("Неверный формат"));
        yearErrorText.shouldHave(Condition.exactText("Неверный формат"));
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат"));
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void checkEmptyNumber (DataHelper.ValidCardInfo info) {
        putCardData("", info.getMonth(), info.getYear(), info.getOwner(), info.getCvc());
        cardErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEmptyOwner (DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(), "", info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void checkRussianOwnerName(String ownerNameRus, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(), ownerNameRus, info.getCvc());
        ownerErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkPastMonth(String pastMonth, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), pastMonth, "20", info.getOwner(), info.getCvc());
        monthErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void checkEmptyMonth (DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), "", info.getYear(), info.getOwner(), info.getCvc());
        monthErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkFutureYear(String futureYear, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), futureYear, info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void checkPastYear(String pastYear, DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), pastYear, info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void checkEmptyYear(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(),"", info.getOwner(), info.getCvc());
        yearErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEmptyCode(DataHelper.ValidCardInfo info) {
        putCardData(DataHelper.approvedCardInfo().getCardNumber(), info.getMonth(), info.getYear(), info.getOwner(), "");
        cvcErrorText.shouldHave(Condition.exactText("Неверный формат"));
    }



    /*public void getValidCardData(DataHelper.CardInfo info) {
        monthInputField.setValue(info.getMonth());
        yearInputField.setValue(info.getYear());
        cvcInputField.setValue(info.getCvc());
        ownerField.setValue(info.getOwner());
        continueButton.click();
    }
    public void getCardNumber(DataHelper.CardNumber info) {
        cardNumberField.setValue(info.getCardNumber());
    }*/

    public void validVerify() {
        successfullNotification.waitUntil(Condition.visible, 35000);
    }

    public void errorVerify() {
        errorNotification.waitUntil(Condition.visible, 35000);
    }
}


