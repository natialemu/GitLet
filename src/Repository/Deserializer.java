package Repository;

import Model.CommitTree;

import java.io.File;
import java.io.Serializable;

/**
 * Created by Nathnael on 8/19/2017.
 */
public interface Deserializer extends Serializable{

    File deserializeTextFile(String filename);

    CommitTree deserializeCommitTree();

}
