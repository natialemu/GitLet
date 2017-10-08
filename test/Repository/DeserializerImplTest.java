package Repository;

import Model.CommitTree;
import Model.Snapshot;
import Model.State.GitVCS;
import Model.Tools.RandomFileGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Nathnael on 8/19/2017.
 */
class DeserializerImplTest {

    private Serializer serializer;

    private Deserializer deserializer;

    private GitVCS gitVCS;

    @BeforeEach
    public void setUp(){
        serializer = new SerializerImpl();

        deserializer = new DeserializerImpl();

        gitVCS = new GitVCS();
    }
    @Test
    void deserializeTextFile() {

        String firstTestFile = RandomFileGenerator.createRandomText();

        File originalFile = new File(GitVCS.RESOURCES_DIRECTORY+firstTestFile);
        int commitIDofFirstTestFile = 0;
        String serializedTextFile = serializer.serializeTextFile(firstTestFile,commitIDofFirstTestFile);
        File deserializedFile = deserializer.deserializeTextFile(serializedTextFile);


        String secondFile = RandomFileGenerator.createRandomText();

        File originalSecondFile = new File(GitVCS.RESOURCES_DIRECTORY+secondFile);
        int commitIDofSecondFile = 1;
        String serializedTextFile2 = serializer.serializeTextFile(secondFile,commitIDofSecondFile);
        File deserializedFile2 = deserializer.deserializeTextFile(serializedTextFile2);

        try {
            assert (FileUtils.contentEquals(originalSecondFile,deserializedFile2));
            assert (FileUtils.contentEquals(originalFile, deserializedFile));
        }catch (IOException ex){
            ex.printStackTrace();
        }

    }

    @Test
    void deserializeCommitTree() {
        CommitTree commitTree = new CommitTree();
        commitTree.addSnapshotToTree(new Snapshot(2,"test message"));

        String branchCommitTree = commitTree.getCurrentBranch();
        serializer.serializeCommitTree(commitTree);

        CommitTree deserializedCommitTree = deserializer.deserializeCommitTree();

        String branchDeserializedTree = deserializedCommitTree.getCurrentBranch();
        assertEquals(commitTree,deserializedCommitTree);
        //assertEquals(branchCommitTree,branchDeserializedTree);

    }

}