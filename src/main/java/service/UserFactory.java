package service;

import model.Maintainer;
import model.Sponsor;
import model.User;

public class UserFactory {

    public static User createUser(String type, String name, String email, String password) {

        if (type == null) {
            return new User(name, email, password, "User");
        }

        switch (type.toLowerCase()) {
            case "sponsor":
                return new Sponsor(name, email, password);
            case "maintainer":
                return new Maintainer(name, email, password);
            default:
                return new User(name, email, password, "User");
        }
    }
}
