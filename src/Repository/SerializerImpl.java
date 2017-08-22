package Repository;

import Model.CommitTree;
import Model.State.GitVCS;
import Model.Tools.RandomFileGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by Nathnael on 8/19/2017.
 */
public class SerializerImpl implements Serializer {
    String commitTreeSerializedFileName = "commitTree.ser";

    @Override
    public String serializeTextFile(String filename, int commitID) {
        File toBeSerialized = new File(GitVCS.RESOURCES_DIRECTORY + filename);
        String newFilename = filename.substring(0, filename.indexOf('.'));
        String serializedName = newFilename + commitID+".ser";
        try {
            File newFileInGitLet = new File(GitVCS.RESOURCES_DIRECTORY + ".gitlet/" + newFilename + commitID + ".txt");
            newFileInGitLet.createNewFile();
            RandomFileGenerator.copyFile(toBeSerialized,newFileInGitLet);


        if (!toBeSerialized.exists()) {
            return null;
        }

            FileOutputStream fileout = new FileOutputStream(GitVCS.RESOURCES_DIRECTORY +".gitlet/" + serializedName);
            ObjectOutputStream output = new ObjectOutputStream(fileout);
            output.writeObject(newFileInGitLet);
            output.flush();
            output.close();
            fileout.close();



        } catch (IOException io) {
            io.printStackTrace();

        }
        return serializedName;
    }

    @Override
    public void serializeCommitTree(CommitTree commitTree) {
        File toBeSerialize = new File(GitVCS.RESOURCES_DIRECTORY + ".gitlet/" + commitTreeSerializedFileName);

        try {
            FileOutputStream fileout = new FileOutputStream(GitVCS.RESOURCES_DIRECTORY + ".gitlet/" + commitTreeSerializedFileName);
            ObjectOutputStream output = new ObjectOutputStream(fileout);
            output.writeObject(commitTree);
            output.close();
            fileout.close();



        } catch (IOException io) {
            io.printStackTrace();

        }


    }
}
