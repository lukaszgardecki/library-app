package com.example.authservice.core.registration;

import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.person.Person;
import com.example.authservice.domain.model.person.values.*;
import com.example.authservice.domain.model.user.RegisterToSave;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;

class FakeUserGenerator {
    private static final Faker faker = new Faker(Locale.getDefault());
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "@#$%^&*()-_=+<>?";

    static RegisterToSave generate() {
        LocalDate dateOfBirth = faker.date().birthday(12, 80).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new RegisterToSave(
                new Email(faker.internet().emailAddress()),
                new Password(generateStrongPassword()),
                new Person(
                        null,
                        new PersonFirstName(faker.name().firstName()),
                        new PersonLastName(faker.name().lastName()),
                        Gender.values()[faker.random().nextInt(Gender.values().length)],
                        new Pesel(generatePesel(dateOfBirth)),
                        new BirthDate(dateOfBirth),
                        new Nationality(faker.country().name()),
                        new FatherName(faker.name().firstName()),
                        new MotherName(faker.name().firstName()),
                        new Address(
                                new StreetAddress(faker.address().streetAddress()),
                                new City(faker.address().city()),
                                new State(faker.address().state()),
                                new ZipCode(faker.address().zipCode()),
                                new Country(faker.address().country())

                        ),
                        new PhoneNumber(faker.phoneNumber().cellPhone() )
                )
        );
    }

    public static String generatePesel(LocalDate dateOfBirth) {
        int year = dateOfBirth.getYear();
        int month = dateOfBirth.getMonthValue();
        int day = dateOfBirth.getDayOfMonth();

        int sexDigit = new Random().nextInt(10);
        String dateOfBirthString = String.format("%02d%02d%02d", year % 100, month, day);
        int randomOrder = new Random().nextInt(1000);
        String randomOrderString = String.format("%03d", randomOrder);
        String peselWithoutControl = dateOfBirthString + randomOrderString + sexDigit;
        String controlDigit = calculatePeselControlDigit(peselWithoutControl);
        return peselWithoutControl + controlDigit;
    }

    private static String calculatePeselControlDigit(String peselWithoutControl) {
        int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3}; // Wagi dla cyfr PESEL
        int sum = 0;

        for (int i = 0; i < peselWithoutControl.length(); i++) {
            sum += Character.getNumericValue(peselWithoutControl.charAt(i)) * weights[i];
        }

        int controlDigit = (10 - (sum % 10)) % 10;
        return Integer.toString(controlDigit);
    }

    public static String generateStrongPassword() {
        Random random = new Random();

        // Losowa długość hasła (min 8, max 12)
        int length = 8 + random.nextInt(5);

        String lowerCase = getRandomCharacters(LOWERCASE, 1); // 1 mała litera
        String upperCase = getRandomCharacters(UPPERCASE, 1); // 1 wielka litera
        String digit = getRandomCharacters(DIGITS, 1); // 1 cyfra
        String specialChar = getRandomCharacters(SPECIAL_CHARACTERS, 1); // 1 znak specjalny

        StringBuilder password = new StringBuilder(lowerCase + upperCase + digit + specialChar);

        while (password.length() < length) {
            password.append(getRandomCharacters(LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS, 1));
        }

        return shuffleString(password.toString());
    }

    private static String getRandomCharacters(String characterSet, int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characterSet.charAt(random.nextInt(characterSet.length())));
        }
        return sb.toString();
    }

    private static String shuffleString(String str) {
        Random random = new Random();
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            int j = random.nextInt(array.length);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new String(array);
    }
}
