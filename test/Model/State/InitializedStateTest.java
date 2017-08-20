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

    }

    @Test
    void copyFile() {
        //make sure that a new file is created
        //also make sure that the two files have the same content

    }

    @Test
    void checkout1() {

    }

    @Test
    void checkoutv2() {

    }

    @Test
    void branch() {

    }

    @Test
    void rmBranch() {

    }

    @Test
    void reset() {

    }

    @Test
    void merge() {

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