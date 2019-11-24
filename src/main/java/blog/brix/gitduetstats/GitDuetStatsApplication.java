package blog.brix.gitduetstats;

import java.io.*;
import java.util.*;

public class GitDuetStatsApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        assert (args.length == 1) : "The path to the git repository needs to be passed as the only argument";
        String gitDirectory = args[0];

        ProcessBuilder builder = new ProcessBuilder();
        String gitLogCommand = "git log --pretty=format:'%cI--%an--%b'";
        boolean isWindows = System.getProperty("os.name").toLowerCase()
                .startsWith("windows");
        if (isWindows) {
            builder.command("cmd.exe", "/c", gitLogCommand);
        } else {
            builder.command("sh", "-c", gitLogCommand);
        }
        builder.directory(new File(gitDirectory));

        List<PairCommit> pairCommits = new ArrayList<>();
        ReadPairCommitConsumer readPairCommitConsumer = new ReadPairCommitConsumer(pairCommits);
        Process process = builder.start();
        new BufferedReader(new InputStreamReader(process.getInputStream()))
                .lines()
                .filter(line -> !line.isBlank())
                .forEach(readPairCommitConsumer);

        int exitCode = process.waitFor();
        assert exitCode == 0 : "The git log command could not been executed successfully";

        Map<Pair, Integer> commitsPerPair = new PairCommitService().countCommitsPerPair(pairCommits);
        List<Pair> sortedPairs = new ArrayList<>(commitsPerPair.keySet());
        sortedPairs.sort((o1, o2) -> commitsPerPair.get(o2).compareTo(commitsPerPair.get(o1)));
        sortedPairs.forEach(pair -> System.out.printf("%-20s + %-20s = %3s Commits%n", pair.getDeveloper1(), pair.getDeveloper2(), commitsPerPair.get(pair)));

        System.exit(0);

    }

}
