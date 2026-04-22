package model;

public class Maintainer extends User {
    private int creditsEarned;

    public Maintainer() {
        super();
        setUserType("Maintainer");
    }

    public Maintainer(String name, String email, String password) {
        super(name, email, password, "Maintainer");
        this.creditsEarned = 0;
    }

    public Maintainer(int userId, String name, String email, String password, int creditsEarned) {
        super(userId, name, email, password, "Maintainer");
        this.creditsEarned = creditsEarned;
    }

    public int getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(int creditsEarned) {
        this.creditsEarned = creditsEarned;
    }
}
