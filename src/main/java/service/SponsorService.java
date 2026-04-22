package service;

import model.Sponsor;

public class SponsorService {

    public void addCredits(Sponsor sponsor, int credits) {
        if (sponsor != null && credits > 0) {
            sponsor.addCredits(credits);
        }
    }
}
