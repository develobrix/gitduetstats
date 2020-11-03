# 📊 gitduetstats

Are you using [git-duet](https://github.com/git-duet/git-duet) for Pair Programming? This simple command line tool scans all the commits in your git repository and tells you which pairs created the most commits.

## How to build & use

1. Build jar file
    ```
    ./gradlew jar
    ```

2. Execute and pass git repository path to be checked
    ```
    java -jar ./build/libs/gitduetstats.jar /path/to/repository
    ```
