package Repository;

import Model.CommitTree;
import Model.State.GitVCS;
import Model.Tools.RandomFileGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Nathnael on 8/19/2017.
 */
class SerializerImplTest {
    private SerializerImpl serializer;

    private static List<File> createdFiles;

    private CommitTree commitTree;

    private GitVCS gitVCS;

    @BeforeAll
    public static void beforeClass(){
        createdFiles = new ArrayList<>();
    }
    @BeforeEach
    public void setUp(){

        gitVCS = new GitVCS();

        serializer = new SerializerImpl();
        commitTree = new CommitTree();

    }
    @Test
    void serializeTextFile() {

        String firstFile = RandomFileGenerator.createRandomText();

        String firstFileActual = firstFile.substring(0,firstFile.indexOf("."));
        File firstF = new File(GitVCS.RESOURCES_DIRECTORY+firstFile);
        createdFiles.add(firstF);
        assert (firstF.exists());
        int commitID1 = 0;
        String secondFile = RandomFileGenerator.createRandomText();
        String secondFileActual = secondFile.substring(0,secondFile.indexOf("."));

        File secondF = new File(GitVCS.RESOURCES_DIRECTORY+secondFile);
        createdFiles.add(secondF);
        assert (secondF.exists());
        int commitID2 = 1;

        serializer.serializeTextFile(firstFile,commitID1);
        File file = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet/"+firstFileActual+commitID1+".ser");
        createdFiles.add(file);
        assert (file.exists());

        serializer.serializeTextFile(secondFile,commitID2);

        file = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet/"+secondFileActual+commitID2+".ser");
        assert (file.exists());
        createdFiles.add(file);
        //create a textfile
        //serialize it
        //make sure the serialized file exists and with the right name

    }

    @Test
    void serializeCommitTree() {

        //Create a commitTree
        //serialize
        serializer.serializeCommitTree(commitTree);
        File commitTreeFile = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet/commitTree.ser");
        createdFiles.add(commitTreeFile);
        assert(commitTreeFile.exists());
        //make sure that it exists

    }

    @AfterEach
    public void tearDown(){
        File file = new File(GitVCS.RESOURCES_DIRECTORY+".gitlet/");
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f: files){
                f.delete();
            }
        }

    }

}