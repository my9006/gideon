package com.almightyqa;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtils {

    public static final Random RANDOM = new Random();
    public static final Faker FAKER = new Faker();

    public static final Gson GSON = new Gson();

    public static int getRandomInt(int... bound) {
        return bound.length == 0 ? RANDOM.nextInt() : RANDOM.nextInt(bound[0]);
    }

    public static long getRandomLong(long... bound) {
        return bound.length == 0 ? RANDOM.nextInt() : RANDOM.nextLong(bound[0]);
    }

    public static long getRandomNumberOfGivenSize(int numberOfDigits) {
        long min = 1;
        if(numberOfDigits>19){
            throw new IllegalArgumentException("Max number for long is 19 digits long");
        }
        for (int i = 1; i < numberOfDigits; i++) {
            min *= 10;
        }
        return min + getRandomLong(9L * min) - 1;
    }

    public static String getRandomEmail(final String domain) {
        return FAKER.name().firstName() +
                Math.abs(getRandomInt()) +
                "@" + domain + ".com";
    }

    public static String getRandomBankAccount() {
        return String.format("%08d", getRandomInt(100000000));
    }

    public static String getRandomTaxpayerId() {
        return 11 + getRandomBankAccount();
    }

    public static <T extends Enum> T getRandomEnum(Class<T> enumClass) {
        final T[] enumConstants = enumClass.getEnumConstants();
        return Arrays.asList(enumConstants).get(getRandomInt(enumConstants.length));
    }

    public static <T> T getOneOf(T... items) {
        final int randomElementIndex = getRandomInt(items.length);
        return items[randomElementIndex];
    }


    public static String formatDate(final LocalDate date) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date);
    }

    public static Object[][] dataProviderFromList(final List<?> listOfItems) {
        final Object[][] dataProvider = new Object[listOfItems.size()][];
        for (int i = 0; i < listOfItems.size(); i++) {
            dataProvider[i] = new Object[]{listOfItems.get(i)};
        }
        return dataProvider;
    }

    public static String getRandomPhoneNumber() {
        return String.valueOf(getRandomNumberOfGivenSize(8));
    }

    public static String uppercaseLettersOnly(final String text) {
        return text.replaceAll("[\\s|[^\\w\\s]]", "").toUpperCase();
    }

    public static String generateRandomLetter(final int amount) {
        StringBuilder sb = new StringBuilder().append("TEST");
        IntStream.range(0, amount - 4).forEach(i -> sb.append((char) (TestUtils.getRandomInt(26) + 'A')));
        return sb.toString();
    }

    public static Object[][] createCombinationsOfLists(final List<?> items1, final List<?> items2) {
        final int combinationAmount = items1.size() * items2.size();
        final Object[][] combination = new Object[combinationAmount][];
        int arrayIndex = 0;
        for (Object o : items1) {
            for (Object value : items2) {
                combination[arrayIndex++] = new Object[]{o, value};
            }
        }
        return combination;
    }

    public static <T> T getRandomElementFromList(final List<T> list) {
        return list.get(getRandomInt(list.size()));
    }


    public static List<String> getInvalidEmails(final String validEmail) {

        return List.of(validEmail.replace("@", ""), validEmail.replace(".", ""),
                validEmail.substring(0, validEmail.indexOf(".")), validEmail.replaceAll("[^@\\.]+(\\.)", ""));


    }

    public static <T, R> List<R> toList(final Iterable<T> iterable, final Class<R> type) {
        final List<R> listValues = new ArrayList<>();
        iterable.forEach(item -> listValues.add(type.cast(item)));
        return listValues;
    }

}
