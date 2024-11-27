package site.stellarburger.user;

import com.github.javafaker.Faker;

public class UserRandomizer {
    private static final Faker faker = new Faker();

    public static User randomUser() {
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(8, 12),
                faker.name().fullName()
        );
    }

    public static User emptyName() {
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(8, 12),
                null
        );
    }

    public static User emptyEmail() {
        return new User(
                null,
                faker.internet().password(8, 12),
                faker.name().fullName()
        );
    }

    public static User emptyPassword() {
        return new User(
                faker.internet().emailAddress(),
                null,
                faker.name().fullName()
        );
    }

    public static User emptyNameEmail() {
        return new User(
                null,
                faker.internet().password(8, 12),
                null
        );
    }

    public static User emptyNamePassword() {
        return new User(
                faker.internet().emailAddress(),
                null,
                null
        );
    }

    public static User emptyEmailPassword() {
        return new User(
                null,
                null,
                faker.name().fullName()
        );
    }

    public static User allFieldsEmpty() {
        return new User(
                null,
                null,
                null
        );
    }
}
