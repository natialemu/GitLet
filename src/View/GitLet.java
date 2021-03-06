package View;

/**
 * Created by Nathnael on 8/17/2017.
 */
public interface GitLet {
    Void init();
    Void add(String filename);
    Void commit(String message);
    Void rm(String filename);
    Void log();
    Void globalLog();
    Void find(String message);
    Void status();
    Void checkout(String filename);
    Void checkout(int commitId, String filename);
    Void checkoutV2(String brachname);
    Void branch(String branchName);
    Void rmBranch(String branchName);
    Void reset(int commitId);
    Void merge(String brachName);
    Void rebase(String brachName);
    Void interactiveRebase(String brachName);

    boolean isBranchName(String argument);

}
