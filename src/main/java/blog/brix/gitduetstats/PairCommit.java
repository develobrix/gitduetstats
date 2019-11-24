package blog.brix.gitduetstats;

import java.util.Date;

public class PairCommit {

    private final Date date;
    private final String committer1;
    private final String committer2;

    public PairCommit(Date date, String committer1, String committer2) {
        this.date = date;
        this.committer1 = committer1;
        this.committer2 = committer2;
    }

    public Date getDate() {
        return date;
    }

    public String getCommitter1() {
        return committer1;
    }

    public String getCommitter2() {
        return committer2;
    }
}
