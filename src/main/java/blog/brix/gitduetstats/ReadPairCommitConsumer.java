package blog.brix.gitduetstats;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class ReadPairCommitConsumer implements Consumer<String> {

    private final List<PairCommit> pairCommits;

    public ReadPairCommitConsumer(List<PairCommit> pairCommits) {
        this.pairCommits = pairCommits;
    }

    @Override
    public void accept(String gitLogEntry) {
        String[] parts = gitLogEntry.split("--");
        if (parts.length < 3) {
            out.printf("INFO: Skipped entry '%s' because no pair commit.%n", gitLogEntry);
            return;
        }

        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date entryDate;
        try {
            entryDate = df1.parse(parts[0].trim());
        } catch (ParseException e) {
            out.printf("WARN: Could not parse date '%s'%n", parts[0].trim());
            return;
        }

        String entryCommitter1 = parts[1].trim();

        String entryCommitter2;
        Pattern pattern = Pattern.compile(".*Signed-off-by:\\s(.*)\\s<.*>");
        Matcher m = pattern.matcher(parts[2]);
        if (m.find())
            entryCommitter2 = m.group(1);
        else {
            out.printf("INFO: Could not get second committer from string '%s'%n", parts[2]);
            return;
        }

        pairCommits.add(new PairCommit(entryDate, entryCommitter1, entryCommitter2));
    }

}
