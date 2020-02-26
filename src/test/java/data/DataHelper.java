package data;
import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() { }


    @Value
    public static class CardInfo {
        private String month;
        private String year;
        private String cvc;
        private String owner;
    }

    public static CardInfo getCardInfo() {
        String month = getRandomMonth();
        String year = getRandomYear();
        String cvc = getRandomCVC();
        String owner = transliterate(getRussianOwnerName());
        return new CardInfo(month, year, cvc, owner);
    }

    public static String getRandomMonth() {
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        Random random = new Random();
        int index = random.nextInt(months.length);
        return (months[index]);
    }

    public static String getRandomYear() {
        String[] years = {"20", "21", "22"};
        Random random = new Random();
        int index = random.nextInt(years.length);
        return (years[index]);
    }

    public  static String getRandomCVC() {
        int cvc = 100 + (int) (Math.random() * 899);
        return (Integer.toString(cvc));
    }

        public static String getRussianOwnerName(){
            Faker faker = new Faker(new Locale("ru"));
            return faker.name().firstName() + " " + faker.name().lastName();
        }

        public static String transliterate(String ownerRussian){
            char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
            String[] abcLat = {" ","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja","A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ownerRussian.length(); i++) {
                for (int x = 0; x < abcCyr.length; x++ ) {
                    if (ownerRussian.charAt(i) == abcCyr[x]) {
                        builder.append(abcLat[x]);
                    }
                }
            }
            return builder.toString();
        }

    @Value
    public static class CardNumber {
        private String cardNumber;
    }
    public static CardNumber approvedCardInfo() {

        return new CardNumber("4444 4444 4444 4441");
    }

    public static CardNumber declinedCardInfo() {

        return new CardNumber("4444 4444 4444 4442");
    }
}



