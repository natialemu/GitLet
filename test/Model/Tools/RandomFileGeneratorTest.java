package Model.Tools;

import Model.State.GitVCS;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Nathnael on 8/19/2017.
 */
class RandomFileGeneratorTest {
    @Test
    void createRandomText() {
        String createdFileName = RandomFileGenerator.createRandomText();

        File newFile = new File(GitVCS.RESOURCES_DIRECTORY+createdFileName);

        assert (newFile.exists());

    }

    @Test
    void randomlyModifyFile() {
        String createdFileName = RandomFileGenerator.createRandomText();

        File fileBeforeModification = new File(GitVCS.RESOURCES_DIRECTORY+createdFileName);

        long lastModified = fileBeforeModification.lastModified();
        RandomFileGenerator.randomlyModifyFile(createdFileName);

        //File fileAfterModification = new File(GitVCS.RESOURCES_DIRECTORY+createdFileName);

        long lastModifiedAfter = fileBeforeModification.lastModified();
        assertNotEquals (lastModified,lastModifiedAfter);



    }
    @AfterAll
    public static void tearDown(){
        File file = new File(GitVCS.RESOURCES_DIRECTORY);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f: files){
                f.delete();
            }
        }
    }

}