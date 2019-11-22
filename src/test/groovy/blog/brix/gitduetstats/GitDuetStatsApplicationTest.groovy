package blog.brix.gitduetstats

import spock.lang.Specification

class GitDuetStatsApplicationTest extends Specification {

    def "It works"() {
        when:
        GitDuetStatsApplication.main(new String[0])

        then:
        true
    }

}
