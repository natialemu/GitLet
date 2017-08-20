package Model;

import Model.FileModel.FileInfo;
import Model.State.GitVCS;
import Model.Tools.IDGenerator;
import Repository.Deserializer;
import Repository.DeserializerImpl;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class CommitTree implements Serializable {
    private HashMap<String,Snapshot> branchPointers;
    private HashMap<Snapshot,Snapshot> mappedCommits;
    Deserializer deserializer;

    //useful for efficiently executing finc command
    private HashMap<String, List<Snapshot>> mapMessageToCommit;

    public void setCurrentBranchName(String currentBranchName) {
        this.currentBranchName = currentBranchName;
    }

    //private List<CommitNode> commitTree;
    private String currentBranchName;
    private int currentMaxID;

    public CommitTree(){
        branchPointers = new HashMap<>();
        mappedCommits  = new HashMap<>();
        mapMessageToCommit = new HashMap();
        deserializer = new DeserializerImpl();

        // commitTree = new ArrayList<>();
        currentBranchName = "Master";//by default should be master
        currentMaxID = 0;

    }
    public Void addSnapshotToTree(Snapshot snap){
        //CommitNode newSnap = new CommitNode(snap.getId(),currentBranchName);
        //commitTree.add(newSnap);
        Snapshot mostRecentCommit = branchPointers.get(currentBranchName);


        mappedCommits.put(snap,mostRecentCommit);

        branchPointers.put(currentBranchName,snap);

        String commitMessage = snap.getCommitMessage();

        if(mapMessageToCommit.containsKey(commitMessage)){
            List<Snapshot> commits = mapMessageToCommit.get(commitMessage);
            commits.add(snap);
            mapMessageToCommit.put(commitMessage,commits);
        }else{
            List<Snapshot> snapshots = new ArrayList<>();
            snapshots.add(snap);
            mapMessageToCommit.put(commitMessage,snapshots);
        }
        //something may not be right!

        return null;

    }

    public Snapshot getCommitAtBranch(String branchName){
        if(branchPointers.containsKey(branchName)){
            return branchPointers.get(branchName);
        }
        return null;
    }

    public List<Snapshot> getCommitWithMessage(String message){
        if (mapMessageToCommit.containsKey(message)) {
            return mapMessageToCommit.get(message);

        }else{
            return null;
        }
    }

    public Snapshot getLastCommit(){
        if(mappedCommits.size() > 0){
            return mappedCommits.get(branchPointers.get(currentBranchName));
        }
        else {
            return null;
        }

    }

    public void addBranch(String branchName){
        branchPointers.put(branchName,branchPointers.get(currentBranchName));
    }


    //not quite right!!
    public Snapshot getCommit(int commitID){
        //TODO: implement this

        Snapshot currentSnapshot = branchPointers.get(currentBranchName);

        while(!mappedCommits.containsKey(currentSnapshot)){
            if(currentSnapshot.getId() == commitID){
                return currentSnapshot;
            }
            currentSnapshot = mappedCommits.get(currentSnapshot);
        }

        return null;

    }

    public boolean fileIsInLastCommit(String filename){
        //TODO:
        Snapshot lastcommit = branchPointers.get(currentBranchName);

        for(FileInfo fileInfo: lastcommit.getFiles()){
            if(fileInfo.getFilename().equals(filename)){
                return true;
            }
        }

        return false;

    }

    public boolean fileNotModifiedSinceLastCommit(String filename){
        File currentState = new File(GitVCS.RESOURCES_DIRECTORY+filename);

        Snapshot lastCommit = branchPointers.get(currentBranchName);
        if(fileIsInLastCommit(filename)){
            String serializedFilename = lastCommit.getSerializedFile(filename);
            File serializedFile = deserializer.deserializeTextFile(serializedFilename);
            if(serializedFile.equals(currentState)){
                return true;
            }

        }

        return false;
    }
    private void printLog(Snapshot currentCommit, int i){
        System.out.println("=====");
        System.out.println();
        System.out.println("Commit " + i);
        Date date = currentCommit.getDateCreated();
        System.out.println(date);
        System.out.println();

        System.out.println(currentCommit.getCommitMessage());
    }
    public String getCurrentBranch(){
        return currentBranchName;
    }
    public Set<String> getBranches(){
        return branchPointers.keySet();
    }

    private void displayLog(String branchName, Snapshot currentCommit, int i){
        if(!mappedCommits.containsKey(currentCommit)){//initial commit
            printLog(currentCommit,i);
            return;

        }else {
            printLog(currentCommit, i);

            displayLog(branchName, mappedCommits.get(currentCommit), ++i);
        }




    }

    public void displayLog(){
        System.out.println("Branch: " + currentBranchName + "\n");
        displayLog(currentBranchName,branchPointers.get(currentBranchName),0);
        //TODO: Iterate through the mapped commits starting from the current branch
        //Print the branch name
        //then for each snapshot:
        //    print ====
        //    print the rank of the commit
        //    print the date of the commit
        //    print the commit message

        //use a helper method that takes branchname and snapshot being pointed by branch name
        // to recurively iterate throught the list
    }

    public void displayGlobalLog(){
        for(String branchName: branchPointers.keySet()){
            displayLog(branchName,branchPointers.get(branchName),0);
        }
    }



    public boolean branchExists(String branchName) {

        return branchPointers.containsKey(branchName);
    }

    public boolean removeBranch(String branchName) {
        if(branchExists(branchName)){
            branchPointers.remove(branchName);
            return true;
        }else{
            return false;
        }
    }

    public boolean fileHasBeenModified(String filename, String branchName){
        Snapshot snapshot;
        boolean isModified =false;
        for(snapshot = branchPointers.get(branchName); !mappedCommits.containsKey(snapshot); snapshot = mappedCommits.get(snapshot)){

            if(snapshot.exists(filename) && snapshot.isFileMarked(filename)){
                return false;
            }else if(snapshot.exists(filename)){
                isModified = true;
            }
            //do same thing as the other method
        }
        return isModified;
        //TODO
    }

    public boolean fileHasBeenModified(String filename, String branchName, Snapshot finalCommit){
        //start from the commit of the branchname. then iteratively
        // if file has been removed at some point, then return false
        // otherwise, return true

        Snapshot snapshot;
        boolean isModified = false;
        for(snapshot = branchPointers.get(branchName); snapshot.equals(finalCommit); snapshot = mappedCommits.get(snapshot)){
            if(snapshot.exists(filename) && snapshot.isFileMarked(filename)){
                return false;
            }else if(snapshot.exists(filename)){
                isModified = true;
            }

        }
        return isModified;
        //TODO
    }

    public Snapshot getLastCommit(String branch){
        return branchPointers.get(branch);
    }
    public Snapshot getSplitPoint(String brachName) {

        Set<Snapshot> visitedCommit = new HashSet<>();

        Snapshot currentCommit = branchPointers.get(currentBranchName);
        Snapshot givenCommit = branchPointers.get(brachName);
        visitedCommit.add(currentCommit);
        visitedCommit.add(givenCommit);
        Snapshot splitPoint = null;
        while(!mappedCommits.containsKey(currentCommit) || !mappedCommits.containsKey(givenCommit)){
            if(!visitedCommit.contains(currentCommit)){
                visitedCommit.add(currentCommit);
            }else{
                splitPoint = currentCommit;
                break;
            }
            if(!visitedCommit.contains(givenCommit)){
                visitedCommit.add(givenCommit);

            }else{
                splitPoint = givenCommit;
                break;
            }


            currentCommit = mappedCommits.get(currentCommit);
            givenCommit = mappedCommits.get(givenCommit);
        }
        return splitPoint;
    }

    public void rebase(String branchName, Snapshot splitPoint) {
        //iterate from the currentBranch until the split point
        //when you get to the point, change the value of that commit
        //in the map to be the current commit of the branchName
        Snapshot currentCommit = branchPointers.get(currentBranchName);
        Snapshot s;
        Snapshot commitBeforeCurrentBranchName = null;
        for(s = currentCommit; s.equals(splitPoint); s = mappedCommits.get(s)){

            Snapshot newSnapShot = new Snapshot(currentCommit,IDGenerator.getCommitId());
            if(commitBeforeCurrentBranchName == null){
                mappedCommits.put(newSnapShot,branchPointers.get(branchName));
                branchPointers.put(currentBranchName,newSnapShot);
                commitBeforeCurrentBranchName = newSnapShot;
            }else{
                mappedCommits.put(newSnapShot,mappedCommits.get(commitBeforeCurrentBranchName));
                mappedCommits.put(commitBeforeCurrentBranchName,newSnapShot);
                commitBeforeCurrentBranchName = newSnapShot;
            }
                //create a new snapshot with a new id
                //insertIntoTree(branchName,newSnapshot); star from current branch and when you get to branchName

                  // create a new commit with a new id
                  // if commitBeforecurrentBranch is null:
                  //
                  //       newCommit points to snapshot with branchName
                  //       currentBranchName now points to this branch
                  //       newCommit is assigned to commitBeforeCurrentBranchName
                  // else:
                  //       newCommit has the same value as commitBeforecurrent ....
                 //        commitBefore...  has value of newCommit
                  //       commitBefoe... becomes new Commit

                if(!mappedCommits.get(s).equals(splitPoint)){
                    break;
                }

        }
    }

    public void interactiveRebase(String branchName, Snapshot splitPoint) {
        Snapshot currentCommit = branchPointers.get(currentBranchName);
        Snapshot s;
        Snapshot commitBeforeCurrentBranchName = null;
        System.out.println("Currently replaying: ");
        int i =0;
        for(s = currentCommit; s.equals(splitPoint); s = mappedCommits.get(s)) {

            Snapshot newSnapShot = new Snapshot(currentCommit, IDGenerator.getCommitId());
            printLog(newSnapShot,i);
            System.out.println("Would you like to (c)ontinue, (s)kip this commit, or change this commit's (m)essage? ");
            Scanner scan = new Scanner(System.in);
            String input = scan.next().toLowerCase();
            while(!input.equals("c") || !input.equals("s") || !input.equals("m")){
                System.out.print("Provide a valid response");
                System.out.println("Would you like to (c)ontinue, (s)kip this commit, or change this commit's (m)essage? ");

                scan = new Scanner(System.in);
                input = scan.next().toLowerCase();
            }

            if(input.equals("m")){
                System.out.println("Please enter a new message for this commit.");
                scan = new Scanner(System.in);
                String commitMessage = scan.nextLine();
                newSnapShot.setCommitMessage(commitMessage);
            }
            if(s.equals(splitPoint) && input.equals("s") || commitBeforeCurrentBranchName == null && input.equals("s")){
                while(input.equals("s")){
                    System.out.print("You can't skip an initial commit");
                    System.out.println("Would you like to (c)ontinue, (s)kip this commit, or change this commit's (m)essage? ");

                    scan = new Scanner(System.in);
                    input = scan.next().toLowerCase();

                }
            }
            if (commitBeforeCurrentBranchName == null) {

                mappedCommits.put(newSnapShot, branchPointers.get(branchName));
                branchPointers.put(currentBranchName, newSnapShot);
                commitBeforeCurrentBranchName = newSnapShot;
            } else {
                if(input.equals("s") && !s.equals(splitPoint)){
                    continue;
                }

                mappedCommits.put(newSnapShot, mappedCommits.get(commitBeforeCurrentBranchName));
                mappedCommits.put(commitBeforeCurrentBranchName, newSnapShot);
                commitBeforeCurrentBranchName = newSnapShot;
            }
            //create a new snapshot with a new id
            //insertIntoTree(branchName,newSnapshot); star from current branch and when you get to branchName

            // create a new commit with a new id
            // if commitBeforecurrentBranch is null:
            //
            //       newCommit points to snapshot with branchName
            //       currentBranchName now points to this branch
            //       newCommit is assigned to commitBeforeCurrentBranchName
            // else:
            //       newCommit has the same value as commitBeforecurrent ....
            //        commitBefore...  has value of newCommit
            //       commitBefoe... becomes new Commit

            if (!mappedCommits.get(s).equals(splitPoint)) {
                break;
            }
            i++;
        }
    }

    public int getCommitId(String commitMessage){

        Snapshot s;
        for(s = branchPointers.get(currentBranchName); mappedCommits.containsKey(s);s = mappedCommits.get(s)){
            if(s.getCommitMessage().equals(commitMessage)){
                return s.getId();
            }
        }
        return -1;
    }
    /*public List<CommitNode> getCommitTree(){
        return mappedCommits;
    }
    /* methods we need

    1. method that adds a snapshot into the commit tree: this will need to adjust
        where the pointer of the currentBranchName is!

     */
}
