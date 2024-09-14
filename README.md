# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

### All bugs found and modified are recorded in IndividualProject/bugs.txt.

### Static bug finder: PMD

#### Installation:
    cd $HOME
    curl -OL https://github.com/pmd/pmd/releases/download/pmd_releases%2F7.5.0/pmd-dist-7.5.0-bin.zip
    unzip pmd-dist-7.5.0-bin.zip
    alias pmd="$HOME/pmd-bin-7.5.0/bin/pmd"
    
#### Run Command:
    pmd check -d /path/to/file -R rulesets/java/quickstart.xml -f text

#### Output:
    No warnings or errors for all 5 class files and corresponding test files