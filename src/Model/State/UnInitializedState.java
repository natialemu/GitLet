package Model.State;

import java.io.File;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class UnInitializedState implements GitLetStateMachine {
    //String directoryPath;
    GitVCS gitlet;
    public UnInitializedState(){
        gitlet = new GitVCS();
        //directoryPath = "";
    }
    @Override
    public Void init() {

        File gitFile = new File(".gitlet");
        try{
            gitFile.mkdir();
            gitlet.setGitState(new InitializedState());
            gitlet.commit("Initial Commit");
            //call the first commit of gitlet here
        } catch (SecurityException se){

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
}