package Model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class CommitTree {
    private HashMap<String,Snapshot> branchPointers;
    private HashMap<Snapshot,Snapshot> mappedCommits;

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

        // commitTree = new ArrayList<>();
        currentBranchName = "Master";//by default should be master
        currentMaxID = 0;

    }
    public Void addSnapshotToTree(Snapshot snap){
        //CommitNode newSnap = new CommitNode(snap.getId(),currentBranchName);
        //commitTree.add(newSnap);
        Snapshot mostRecentCommit = branchPointers.get(currentBranchName);
        if(mappedCommits.containsKey(snap)){
            mappedCommits.put(snap,mostRecentCommit);
        }else{

            mappedCommits.put(snap,mostRecentCommit);
        }

        branchPointers.put(currentBranchName,snap);
        //something may not be right!

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


    //not quite right!!
    public Snapshot getCommit(int commitID){
        //TODO: implement this
        return null;


    }

    public boolean fileIsInLastCommit(String filename){
        //TODO:

        return false;

    }

    public boolean fileNotModifiedSinceLastCommit(String filename){
        //TODO:

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

    public Snapshot getLastSnapshot(){
        //TODO
        return null;

    }

    /*public List<CommitNode> getCommitTree(){
        return mappedCommits;
    }
    /* methods we need

    1. method that adds a snapshot into the commit tree: this will need to adjust
        where the pointer of the currentBranchName is!

     */
}
