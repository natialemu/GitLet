package View;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class GitLetFacade implements GitLet {
    @Override
    public Void init() {
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
    public Void checkoutV2(String brachname) {
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
