package blog.brix.gitduetstats

import spock.lang.Specification

class PairCommitServiceTest extends Specification {

    PairCommitService pairCommitService

    void setup() {
        pairCommitService = new PairCommitService()
    }

    def "countCommitsPerPair - counts commits correctly and ignores the order of the committers"() {
        given:
        def pairCommitList = new ArrayList<PairCommit>()
        pairCommitList.addAll([
                new PairCommit(new Date(0), "Alfred", "Barbara"),
                new PairCommit(new Date(0), "Barbara", "Alfred"),
                new PairCommit(new Date(0), "Alfred", "Christine")
        ])

        when:
        def commitsPerPair = pairCommitService.countCommitsPerPair(pairCommitList)

        then:
        commitsPerPair.size() == 2
        commitsPerPair.get(new Pair("Alfred", "Barbara")) == 2
        commitsPerPair.get(new Pair("Barbara", "Alfred")) == 2
        commitsPerPair.get(new Pair("Alfred", "Christine")) == 1
        !commitsPerPair.containsKey(new Pair("Barbara", "Christine"))
    }
}
