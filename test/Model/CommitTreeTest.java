package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Nathnael on 8/18/2017.
 */
class CommitTreeTest {
    private CommitTree commitTree;

    @BeforeEach
    public void setUp(){
        commitTree = new CommitTree();
    }


    @Test
    void addSnapshotToTree() {
        Snapshot snapshot = new Snapshot(01,"first snapshot");
        Snapshot lastCommit = commitTree.getLastCommit();
        commitTree.addSnapshotToTree(snapshot);
        assertEquals(commitTree.getCommitAtBranch(commitTree.getCurrentBranch()),snapshot);

        /*
            Things to check:
            1. after addition, the current branch should point to it
            2. size of the map should increase by 1
            3. value of the map at key current snap should be privious commit

         */

    }

    @Test
    void getCommitWithMessage() {
        String commitMessage = "first commit message";
        Snapshot snapshot = new Snapshot(00,commitMessage);
        List<Snapshot> commits = new ArrayList<>();

        commits.add(snapshot);
        Snapshot lastCommit = commitTree.getLastCommit();

        commitTree.addSnapshotToTree(snapshot);

        List<Snapshot> returnedCommit = commitTree.getCommitWithMessage(commitMessage);
        assertEquals(commits,returnedCommit);



    }

    @Test
    void getLastCommit() {
        String commitMessage = "first commit message";
        Snapshot snapshot = new Snapshot(00,commitMessage);
        commitTree.addSnapshotToTree(snapshot);

        String commitMessage2 = "second message";
        Snapshot secondSnapshot = new Snapshot(02,commitMessage2);
        commitTree.addSnapshotToTree(secondSnapshot);

        Snapshot returnedSnapshot = commitTree.getLastCommit();
        assertEquals(returnedSnapshot,secondSnapshot);

        String commitMessage3 = "third message";
        Snapshot thirdSnapshot = new Snapshot(04,commitMessage3);

        returnedSnapshot = commitTree.getLastCommit();
        assertEquals(returnedSnapshot,thirdSnapshot);

    }


    @Test
    void getCommit() {

        String commitMessage = "first commit message";
        Snapshot snapshot = new Snapshot(00,commitMessage);
        commitTree.addSnapshotToTree(snapshot);

        String commitMessage2 = "second message";
        Snapshot secondSnapshot = new Snapshot(02,commitMessage2);
        commitTree.addSnapshotToTree(secondSnapshot);

        Snapshot returnedSnapshot = commitTree.getCommit(00);
        assertEquals(returnedSnapshot,snapshot);

        String commitMessage3 = "third message";
        Snapshot thirdSnapshot = new Snapshot(04,commitMessage3);

        returnedSnapshot = commitTree.getCommit(04);
        assertEquals(returnedSnapshot,thirdSnapshot);
    }

    @Test
    void fileIsInLastCommit() {

        //TODO
    }

    @Test
    void fileNotModifiedSinceLastCommit() {

        //TODO
    }

    @Test
    public void getSplitPoint(String branchName){
        //
        //TODO
        //
    }


}