package site.stellarburger.order;

import com.github.javafaker.Faker;

public class OrderRandomizer {
    private static final Faker faker = new Faker();

    public static Order randomOrder() {
        return new Order(new String[]{faker.lorem().characters(24)});
    }
}
