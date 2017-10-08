# GitLet


## Overview

Gitlet is a version control system similar to git. Its saves snapshots or commits of a file at different points in time locally. That way, if you mess them up later, you can return to earlier versions of your files. Most commands function in a simillar way to git with some differences


## Gitlet commands


- **gitlet add [file name]:** Indicates you want the file to be included in the upcoming commit as having been changed.
- **gitlet commit [commit message]:** Saves a snapshot of certain files that can be viewed or restored at a later time.
- **gitlet rm [file name]:** Mark the file for removal
- **gitlet log:** Display information about each commit backwards starting from current state of file
- **gitlet global-log:** displays information about all commits ever made
- **gitlet find [commit message]:** Prints out the id of the commit that has the given commit message
- **gitlet status:** Displays what branches currently exist, and marks the current branch with a *
- **gitlet checkout [file name]:** Restores the file in the working directory to its state at the commit at the head of the current branch
- **gitlet checkout [commit id] \[file name]:** Restores the given file in the working directory to its state at the given commit.
- **gitlet checkout [branch name]:** Restores all files to their versions in the commit at the head of the given branch
- **gitlet branch [branch name]:** Creates a new branch with the given name
- **gitlet rm-branch [branch name]:** Deletes the branch with the given name
- **gitlet reset [commit id]:** Restores all files to their versions in the commit with the given id
- **gitlet merge [branch name]:** Merges files from the head of the given branch into the head of the current branch
- **gitlet rebase [branch name]:** find the split point of the current branch and the given branch, then snaps off the current branch at this point, then reattaches the current branch to the head of the given branch
- **gitlet i-rebase [branch name]:** This is a user interactive version of the `rebase` command


## Tests 

Testing was an essential part of the developement process.


![Alt text](/resources/state_tests.png?raw=true "Tests for Gitlet Commands")

![Alt text](/resources/commit_tree_tests.png?raw=true "Tests for Commit Tree")

![Alt text](/resources/deserializer_tests.png?raw=true "Tests for the deserializer")

![Alt text](/resources/gitlet_tests.png?raw=true "")




## Design

