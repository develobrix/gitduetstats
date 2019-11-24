package blog.brix.gitduetstats;

import java.util.*;

public class PairCommitService {

    public Map<Pair, Integer> countCommitsPerPair(List<PairCommit> pairCommits) {
        Map<Pair, Integer> commitsPerPair = new HashMap<>();
        for (PairCommit pairCommit: pairCommits) {
            Pair pair = new Pair(pairCommit.getCommitter1(), pairCommit.getCommitter2());
            int pairCommitCount = 1;
            if (commitsPerPair.containsKey(pair))
                pairCommitCount = commitsPerPair.get(pair) + 1;
            commitsPerPair.put(pair, pairCommitCount);
        }
        return commitsPerPair;
    }

}
