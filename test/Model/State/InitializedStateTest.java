package Model.State;

import Model.CommitTree;
import Model.Snapshot;
import Model.Tools.RandomFileGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Nathnael on 8/18/2017.
 */
class InitializedStateTest {
    //private InitializedState initializedState;

    private GitVCS gitVCS;

    String firstTestFileName;
    String secondTestFileName;
    String thirdTestFileName;
    String fourthTestFileName;
    String fifthTestFileName;
    String sixthTestFileName;
    String seventhTestFileName;

    @BeforeAll
    public void beforeClass(){

         firstTestFileName = RandomFileGenerator.createRandomText();
         secondTestFileName = RandomFileGenerator.createRandomText();
         thirdTestFileName = RandomFileGenerator.createRandomText();
         fourthTestFileName = RandomFileGenerator.createRandomText();
         fifthTestFileName = RandomFileGenerator.createRandomText();
         sixthTestFileName = RandomFileGenerator.createRandomText();
         seventhTestFileName = RandomFileGenerator.createRandomText();
    }
    @BeforeEach
    public void setUp(){
        gitVCS = new GitVCS();
        //initializedState = new InitializedState(gitVCS);
        gitVCS.initalize();

        /*
        Create some files
         */


    }

    @Test
    void init() {

    }

    @Test
    void add() {
        String filename = RandomFileGenerator.createRandomText();
        gitVCS.add(filename);
        InitializedState initializedState = (InitializedState) gitVCS.getINITIALIZED();
        assert(initializedState.isInWorkingDirector(filename));

        String nonExistingFilename = "abc";
        assert(!initializedState.isInWorkingDirector(nonExistingFilename));

        gitVCS.commit("commited File 1");

        gitVCS.add(filename);
        initializedState = (InitializedState)gitVCS.getINITIALIZED();
        assert(!initializedState.isInWorkingDirector(filename));
        String secondFile = RandomFileGenerator.createRandomText();

        gitVCS.add(secondFile);

        assert(initializedState.isInWorkingDirector(secondFile));


    }

    @Test
    void writeToFile() {

    }


    @Test
    void commit() {

        gitVCS.add(firstTestFileName);
        String firstCommitMessage = "commit for first file";
        gitVCS.commit(firstCommitMessage);
        InitializedState initializedState = (InitializedState) gitVCS.getINITIALIZED();
        assert (!initializedState.isInWorkingDirector(fifthTestFileName));


        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);
        gitVCS.commit("commited second and third files");
        assertEquals (initializedState.getWorkingDirectory().size(),0);



        /*
             Things to test:
             1. try commiting without adding anything
             2. try commiting without a commit message
             3. make sure commiting will add the right Snapshot
             4. make sure the current branch stays the same
             5. make sure the current branch points to the most recent commit
         */

    }

    @Test
    void rm() {

        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);
        gitVCS.add(firstTestFileName);

        gitVCS.commit("Commited three files");

        gitVCS.rm(firstTestFileName);

        InitializedState initializedState = (InitializedState)gitVCS.getINITIALIZED();
        CommitTree commitTree = initializedState.getTree();
        Snapshot snapshot = commitTree.getLastCommit();
        assert(snapshot.isFileMarked(firstTestFileName));
        assert(!snapshot.isFileMarked(secondTestFileName));
        assert (!snapshot.isFileMarked(thirdTestFileName));




    }



    @Test
    void checkout() {
        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);
        gitVCS.add(firstTestFileName);
        gitVCS.add(fourthTestFileName);
        gitVCS.commit("commited four files");
        File beforeModification = new File(GitVCS.RESOURCES_DIRECTORY+fourthTestFileName);

        RandomFileGenerator.randomlyModifyFile(fourthTestFileName);

        File afterModification = new File(GitVCS.RESOURCES_DIRECTORY+fourthTestFileName);

        assert(!beforeModification.equals(afterModification));

        gitVCS.checkout(fourthTestFileName);

        File afterCheckout = new File(GitVCS.RESOURCES_DIRECTORY+fourthTestFileName);

        assertEquals(beforeModification,afterCheckout);







    }

    @Test
    void copyFile() {
        //make sure that a new file is created
        //also make sure that the two files have the same content
        String source = RandomFileGenerator.createRandomText();
        String destination = RandomFileGenerator.createRandomText();
        File sourceFile = new File(GitVCS.RESOURCES_DIRECTORY+source);
        File destinationFile = new File(GitVCS.RESOURCES_DIRECTORY+destination);
        assert (!sourceFile.equals(destinationFile));
        InitializedState initializedState = (InitializedState) gitVCS.getINITIALIZED();
        initializedState.copyFile(sourceFile,destinationFile);
        assertEquals(sourceFile,destinationFile);

    }


    @Test
    void rmBranch() {

        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);
        gitVCS.add(firstTestFileName);

        gitVCS.commit("commited 3 files");

        gitVCS.branch("develop");
        gitVCS.branch("feature");

        gitVCS.rmBranch("develop");

        InitializedState initializedState =(InitializedState)gitVCS.getINITIALIZED();
        assertEquals(initializedState.getTree().getBranches().size(),2);
        assert (!initializedState.getTree().getBranches().contains("develop"));


    }

    @Test
    void reset() {

        File beforeModFirstFile = new File(GitVCS.RESOURCES_DIRECTORY+firstTestFileName);
        File beforeModSecondFile = new File(GitVCS.RESOURCES_DIRECTORY+secondTestFileName);
        File beforeModThirdFile = new File(GitVCS.RESOURCES_DIRECTORY+thirdTestFileName);

        gitVCS.add(firstTestFileName);
        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);

        String firstCommitMessage = "commited three test files";
        gitVCS.commit(firstCommitMessage);

        RandomFileGenerator.randomlyModifyFile(firstTestFileName);
        RandomFileGenerator.randomlyModifyFile(secondTestFileName);

        gitVCS.add(firstCommitMessage);
        gitVCS.add(secondTestFileName);

        String secondCommitMessage = "commited 2 test files";

        gitVCS.commit(secondCommitMessage);


        InitializedState initializedState = (InitializedState)gitVCS.getINITIALIZED();

        int firstCommitId = initializedState.getTree().getCommitId(firstCommitMessage);

        gitVCS.reset(firstCommitId);

        File resetFirstFile = new File(GitVCS.RESOURCES_DIRECTORY+firstTestFileName);
        File resetSecondFile = new File(GitVCS.RESOURCES_DIRECTORY+secondTestFileName);
        File resetThirdFile = new File(GitVCS.RESOURCES_DIRECTORY+thirdTestFileName);

        assertEquals(beforeModFirstFile,resetFirstFile);
        assertEquals(beforeModSecondFile,resetSecondFile);
        assertEquals(beforeModThirdFile,resetThirdFile);

        //commit 4 files
        //modify the first 3 files
        //add all
        //commit all
        //modify file 3
        //add and commit

        //get commit id of first commit
        //call reset
        //create file objects for all 4
        //make sure the

    }

    @Test
    void merge() {

        RandomFileGenerator.randomlyModifyFile(firstTestFileName);
        File firstFileOnMaster = new File(GitVCS.RESOURCES_DIRECTORY+firstTestFileName);
        gitVCS.add(firstTestFileName);
        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);
        gitVCS.add(fourthTestFileName);

        gitVCS.commit("commited for the first time ");
        RandomFileGenerator.randomlyModifyFile(secondTestFileName);
        RandomFileGenerator.randomlyModifyFile(thirdTestFileName);

        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);

        gitVCS.commit("commited for the second time");

        RandomFileGenerator.randomlyModifyFile(firstTestFileName);
        RandomFileGenerator.randomlyModifyFile(secondTestFileName);
        RandomFileGenerator.randomlyModifyFile(thirdTestFileName);

        gitVCS.add(firstTestFileName);
        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);

        gitVCS.commit("commited for the third time");

        //     ---------------------------------------------------
        //                  Create a new Branch
        //     ---------------------------------------------------
        gitVCS.branch("develop");

        gitVCS.checkoutv2("develop");



        RandomFileGenerator.randomlyModifyFile(secondTestFileName);
        //File firstFileOnMaster = new File(GitVCS.RESOURCES_DIRECTORY+firstTestFileName);

        //gitVCS.add(firstTestFileName);
        gitVCS.add(secondTestFileName);
        //gitVCS.add(thirdTestFileName);
        //gitVCS.add(fourthTestFileName);

        gitVCS.commit("commited for the first time in develop");
        RandomFileGenerator.randomlyModifyFile(secondTestFileName);
        RandomFileGenerator.randomlyModifyFile(thirdTestFileName);

        gitVCS.add(secondTestFileName);
        gitVCS.add(thirdTestFileName);

        gitVCS.commit("commited for the second time in develop");


        RandomFileGenerator.randomlyModifyFile(thirdTestFileName);


        gitVCS.add(thirdTestFileName);

        //
        File developSecondFile = new File(GitVCS.RESOURCES_DIRECTORY+secondTestFileName);
        File developThirdFile = new File(GitVCS.RESOURCES_DIRECTORY+thirdTestFileName);
        File developFourthFile = new File(GitVCS.RESOURCES_DIRECTORY+fourthTestFileName);


        gitVCS.commit("commited for the third time in develop");


        /*
        REALLY THINK ABT THIS
              Testing case 1: create 4 Files and 3 commits by modifiying them:
                                          -> modify file 1 in commit 1
                                          -> modify file 2 and 3 in commit 2
                                          -> modify 1 , 2 ,3 in commit 3
                            : create a new branch
                            : create 3 commits with new branch set as current branch:
                                   -> modify file 2 during commit1
                                   -> modify file 2 and 3 during commit 2
                                   -> modify only file 3 during commit 3

                            : merge the new branch with master
                            :Result:
                                  -> files 2 and 3 on master's most re should be same as most recent changes in new branch
                                  -> file 4 should stay the same
                                  -> file 1 should be same as prior master
                                  ->

         */


    }

    @Test
    void rebase() {

    }

    @Test
    void interactiveRebase() {

    }

    @AfterEach
    public void tearDown(){
        //remove any files in the working directory
        //remove any files serialized
        File rootFile = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet");
        rootFile.delete();

        //problem:

        /*
           The tree exists only for the duration of the program even if the files persist. what to do?

           Everytime a change is made to the tree, overwrite the TreeSettings.ser file with the new state of the tree

           Everytime the application launches, check to see if the said file exists, if so, build the tree out of the deserialized file
         */

    }

}