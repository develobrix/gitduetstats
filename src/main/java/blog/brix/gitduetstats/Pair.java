package blog.brix.gitduetstats;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Pair {

    private final String developer1;
    private final String developer2;

    public Pair(String developer1, String developer2) {
        if (developer1.compareTo(developer2) > 0) {
            this.developer1 = developer1;
            this.developer2 = developer2;
        }
        else {
            this.developer1 = developer2;
            this.developer2 = developer1;
        }
    }

    String getDeveloper1() {
        return developer1;
    }

    String getDeveloper2() {
        return developer2;
    }

}
