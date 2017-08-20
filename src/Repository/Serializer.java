package Repository;

import Model.CommitTree;

/**
 * Created by Nathnael on 8/19/2017.
 */
public interface Serializer {


    String serializeTextFile(String filename, int commitID);

    void serializeCommitTree(CommitTree commitTree);

    //Serialize Tree to build the Commit Tree
}
