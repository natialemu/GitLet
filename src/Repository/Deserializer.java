package Repository;

import Model.CommitTree;

import java.io.File;

/**
 * Created by Nathnael on 8/19/2017.
 */
public interface Deserializer {

    File deserializeTextFile(String filename);

    CommitTree deserializeCommitTree();

}
