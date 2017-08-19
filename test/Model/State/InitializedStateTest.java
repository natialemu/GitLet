package Model.State;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;

/**
 * Created by Nathnael on 8/18/2017.
 */
class InitializedStateTest {
    private InitializedState initializedState;

    private GitVCS gitVCS;

    @BeforeEach
    public void setUp(){
        gitVCS = new GitVCS();
        initializedState = new InitializedState(gitVCS);
    }
    @Test
    void init() {

    }

    @Test
    void add() {
        //when adding  a file make sure file exists
        //
        //after adding the file
        //make sure the added files are in the working directory


    }

    @Test
    void writeToFile() {

    }

    @Test
    void deserializeFile() {

    }

    @Test
    void serializeFile() {

    }

    @Test
    void commit() {

    }

    @Test
    void rm() {

    }

    @Test
    void log() {

    }

    @Test
    void globalLog() {

    }

    @Test
    void find() {

    }

    @Test
    void status() {

    }

    @Test
    void checkout() {

    }

    @Test
    void copyFile() {

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

}