package model;

public class Sponsor extends User {
    private int credits;

    public Sponsor() {
        super();
        setUserType("Sponsor");
    }

    public Sponsor(String name, String email, String password) {
        super(name, email, password, "Sponsor");
        this.credits = 0;
    }

    public Sponsor(int userId, String name, String email, String password, int credits) {
        super(userId, name, email, password, "Sponsor");
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void addCredits(int amount) {
        this.credits += amount;
    }
}
