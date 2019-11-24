package blog.brix.gitduetstats

import spock.lang.Specification

class ReadPairCommitConsumerTest extends Specification {

    ReadPairCommitConsumer pairCommitReader
    List<PairCommit> pairCommits

    void setup() {
        pairCommits = new ArrayList<>()
        pairCommitReader = new ReadPairCommitConsumer(pairCommits);
    }

    def "accept - converts pre-formatted git log output line to a pair commit"() {
        given:
        def gitLog1 = "2019-05-10T16:00:00+02:00--Michael Jackson--Signed-off-by: Whitney Houston <whitney@houston.com>"
        def gitLog2 = "2019-08-20T12:00:00+02:00--Freddie Mercury--Signed-off-by: Michael Jackson <michael@jackson.com>"

        when:
        pairCommitReader.accept(gitLog1)
        pairCommitReader.accept(gitLog2)

        then:
        pairCommits.size() == 2
        pairCommits[0].committer1 == "Michael Jackson"
        pairCommits[0].committer2 == "Whitney Houston"
        pairCommits[0].date ==
                new GregorianCalendar(2019, Calendar.MAY, 10, 16, 0).getTime()
        pairCommits[1].committer1 == "Freddie Mercury"
        pairCommits[1].committer2 == "Michael Jackson"
        pairCommits[1].date ==
                new GregorianCalendar(2019, Calendar.AUGUST, 20, 12, 0).getTime()
    }

    def "accept - ignores invalid inputs and empty lines"() {
        given:
        def wrongDateLine = "2019-05-10T16:00:00--Michael Jackson--Signed-off-by: Whitney Houston <whitney@houston.com>"
        def wrongSecondCommitterLine = "2019-08-20T12:00:00+02:00--Freddie Mercury--I did not use git duet."
        def emptyLine = ""

        when:
        pairCommitReader.accept(wrongDateLine)
        pairCommitReader.accept(wrongSecondCommitterLine)
        pairCommitReader.accept(emptyLine)
        pairCommitReader.accept(System.lineSeparator())

        then:
        pairCommits.size() == 0
    }

}
