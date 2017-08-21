package Model.State;

import Model.CommitTree;

import java.io.File;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class UnInitializedState implements GitLetStateMachine {
    //String directoryPath;
    private CommitTree tree;
    GitVCS gitlet;
    public UnInitializedState(GitVCS gitlet){
        this.gitlet = gitlet;
        //directoryPath = "";
    }
    @Override
    public Void init() {

        File gitFile = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet");
        File settingsFile = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet/commitTree.ser");
        try{
            if(gitFile.exists() && settingsFile.exists()){
                //
                CommitTree commitTree = gitlet.getDeserializer().deserializeCommitTree();

                //tree.buildTree(settingsFile);
                gitlet.setCommitTree(commitTree);
                gitlet.toInitializedState();

                gitlet.setCommitTree(commitTree);
            }else{
                gitFile.mkdir();
                gitlet.toInitializedState();
                //gitlet.add(".gitlet");
                //gitlet.commit("Initial Commit");
            }

            //call the first commit of gitlet here
        } catch (SecurityException se){
            se.printStackTrace();

        }

        return null;
    }


    @Override
    public Void add(String filename) {
        return null;
    }

    @Override
    public Void commit(String message) {
        return null;
    }

    @Override
    public Void rm(String filename) {
        return null;
    }

    @Override
    public Void log() {
        return null;
    }

    @Override
    public Void globalLog() {
        return null;
    }

    @Override
    public Void find(String message) {
        return null;
    }

    @Override
    public Void status() {
        return null;
    }

    @Override
    public Void checkout(String filename) {
        return null;
    }

    @Override
    public Void checkout(int commitId, String filename) {
        return null;
    }

    @Override
    public Void checkoutv2(String brachname) {
        return null;
    }

    @Override
    public Void branch(String branchName) {
        return null;
    }

    @Override
    public Void rmBranch(String branchName) {
        return null;
    }

    @Override
    public Void reset(int commitId) {
        return null;
    }

    @Override
    public Void merge(String brachName) {
        return null;
    }

    @Override
    public Void rebase(String brachName) {
        return null;
    }

    @Override
    public Void interactiveRebase(String brachName) {
        return null;
    }

    @Override
    public void setCommitTree(CommitTree commitTree) {
        tree = commitTree;
    }
}