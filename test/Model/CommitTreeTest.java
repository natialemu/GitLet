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
        assertEquals(returnedSnapshot.getId(),secondSnapshot.getId());

        String commitMessage3 = "third message";
        Snapshot thirdSnapshot = new Snapshot(04,commitMessage3);
        commitTree.addSnapshotToTree(thirdSnapshot);

        returnedSnapshot = commitTree.getLastCommit();
        assertEquals(returnedSnapshot.getId(),thirdSnapshot.getId());

    }


    @Test
    void getCommit() {

        String commitMessage = "first commit message";
        Snapshot snapshot = new Snapshot(0,commitMessage);
        commitTree.addSnapshotToTree(snapshot);

        String commitMessage2 = "second message";
        Snapshot secondSnapshot = new Snapshot(2,commitMessage2);
        commitTree.addSnapshotToTree(secondSnapshot);

        Snapshot returnedSnapshot = commitTree.getCommit(0);
        assertEquals(returnedSnapshot,snapshot);

        String commitMessage3 = "third message";
        Snapshot thirdSnapshot = new Snapshot(4,commitMessage3);
        commitTree.addSnapshotToTree(thirdSnapshot);

        returnedSnapshot = commitTree.getCommit(4);
        assertEquals(returnedSnapshot,thirdSnapshot);
    }

    @Test
    public void getSplitPoint(){
        //commit 5 snapshots
        //add a branch
        //commit again on both branches
        //the split point should just be the next snapshot
        //TODO
        //
        String commitMessage = "first commit message";
        Snapshot snapshot = new Snapshot(00,commitMessage);
        commitTree.addSnapshotToTree(snapshot);

        String commitMessage2 = "second message";
        Snapshot secondSnapshot = new Snapshot(02,commitMessage2);
        commitTree.addSnapshotToTree(secondSnapshot);

        String commitMessage3 = "third commit message";
        Snapshot snapshot3 = new Snapshot(10,commitMessage3);
        commitTree.addSnapshotToTree(snapshot3);



        String commitMessage5 = "fifth message";
        Snapshot snapshot5 = new Snapshot(13,commitMessage5);
        commitTree.addSnapshotToTree(snapshot5);

        Snapshot actualSplitPoint = commitTree.getLastCommit();

        //add a branch
        commitTree.addBranch("develop");
        commitTree.addSnapshotToTree(new Snapshot(15,"sixth on master commit"));

        String commitMessage4 = "fourth message";
        Snapshot secondSnapshot4 = new Snapshot(12,commitMessage4);
        commitTree.addSnapshotToTree(secondSnapshot4);
        
        //change branch
        commitTree.setCurrentBranchName("develop");
        commitTree.addSnapshotToTree(new Snapshot(16,"sixth on develop commit"));

        Snapshot expectedSplitPoint = commitTree.getSplitPoint("Master");

        assertEquals(expectedSplitPoint,actualSplitPoint);

    }


}