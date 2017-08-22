package View;

import Model.State.GitVCS;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class GitLetFacade implements GitLet {
    GitVCS gitlet;

    public GitLetFacade(){
        gitlet = new GitVCS();
    }
    @Override
    public Void init() {
        gitlet.initalize();
        return null;
    }

    @Override
    public Void add(String filename) {
        gitlet.add(filename);
        return null;
    }

    @Override
    public Void commit(String message) {
        gitlet.commit(message);
        return null;
    }

    @Override
    public Void rm(String filename) {
        gitlet.rm(filename);
        return null;
    }

    @Override
    public Void log() {
        gitlet.log();
        return null;
    }

    @Override
    public Void globalLog() {
        gitlet.globalLog();
        return null;
    }

    @Override
    public Void find(String message) {
        gitlet.find(message);
        return null;
    }

    @Override
    public Void status() {
        gitlet.status();
        return null;
    }

    @Override
    public Void checkout(String filename) {
        gitlet.checkout(filename);
        return null;
    }

    @Override
    public Void checkout(int commitId, String filename) {
        gitlet.checkout(commitId,filename);
        return null;
    }

    @Override
    public Void checkoutV2(String branchName) {
        checkoutV2(branchName);

        return null;
    }

    @Override
    public Void branch(String branchName) {
        gitlet.branch(branchName);
        return null;
    }

    @Override
    public Void rmBranch(String branchName) {
        gitlet.rmBranch(branchName);
        return null;
    }

    @Override
    public Void reset(int commitId) {
        gitlet.reset(commitId);
        return null;
    }

    @Override
    public Void merge(String branchName) {
        gitlet.merge(branchName);
        return null;
    }

    @Override
    public Void rebase(String branchName) {
        gitlet.rebase(branchName);
        return null;
    }

    @Override
    public Void interactiveRebase(String branchName) {
        gitlet.interactiveRebase(branchName);
        return null;
    }
}
