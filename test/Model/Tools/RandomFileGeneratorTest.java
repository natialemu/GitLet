package Model.Tools;

import Model.State.GitVCS;
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

        RandomFileGenerator.randomlyModifyFile(createdFileName);

        File fileAfterModification = new File(GitVCS.RESOURCES_DIRECTORY+createdFileName);

        assert (!fileBeforeModification.equals(fileAfterModification));



    }

}