package Model.State;

import Model.CommitTree;
import Repository.Deserializer;
import Repository.DeserializerImpl;
import Repository.Serializer;
import Repository.SerializerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class GitVCS {

    private List<String> workingDirectory;
    private GitLetStateMachine currentState;//default should be uninitialized

    public Deserializer getDeserializer() {
        return deserializer;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    private Deserializer deserializer;

    private Serializer serializer;

    private CommitTree commitTree = new CommitTree();

    public static final String RESOURCES_DIRECTORY = "./workingDirectory/";

    public void setCommitTree(CommitTree commitTree){
        currentState.setCommitTree(commitTree);
    }


    public GitLetStateMachine getINITIALIZED() {
        return INITIALIZED;
    }

    public GitLetStateMachine getUNINITIALIZED() {
        return UNINITIALIZED;
    }

    GitLetStateMachine INITIALIZED = new InitializedState(this,commitTree);

    GitLetStateMachine UNINITIALIZED = new UnInitializedState(this,commitTree);

    public GitVCS(){


        workingDirectory = new ArrayList<>();
        setGitState(UNINITIALIZED);

        serializer = new SerializerImpl();
        deserializer = new DeserializerImpl();
        initalize();
    }

    public Void setGitState(GitLetStateMachine state)
    {
        currentState = state;
        return null;
    }

    public void toInitializedState(){
        setGitState(INITIALIZED);
    }
    public Void initalize()
    {

        currentState.init();
        return null;
    }

    public Void add(String filenmae){
        currentState.add(filenmae);
        return null;
    }

    public Void commit(String message){
        currentState.commit(message);
        return null;
    }

    public Void rm(String filename){
        currentState.rm(filename);
        return null;
    }


    public Void log() {
        currentState.log();
        return null;
    }


    public Void globalLog() {
        currentState.globalLog();
        return null;
    }


    public Void find(String message) {
        currentState.find(message);
        return null;
    }


    public Void status() {
        currentState.status();
        return null;
    }


    public Void checkout(String filename) {
        currentState.checkout(filename);
        return null;
    }


    public Void checkout(int commitId, String filename) {
        currentState.checkout(commitId,filename);
        return null;
    }


    public Void checkoutv2(String brachname) {
        currentState.checkoutv2(brachname);
        return null;
    }


    public Void branch(String branchName) {
        currentState.branch(branchName);
        return null;
    }


    public Void rmBranch(String branchName) {
        currentState.rmBranch(branchName);
        return null;
    }


    public Void reset(int commitId) {
        currentState.reset(commitId);
        return null;
    }


    public Void merge(String branchName) {
        currentState.merge(branchName);
        return null;
    }


    public Void rebase(String branchName) {
        currentState.rebase(branchName);
        return null;
    }


    public Void interactiveRebase(String branchName) {
        currentState.interactiveRebase(branchName);
        return null;
    }
}
