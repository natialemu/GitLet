package Repository;

import Model.CommitTree;
import Model.State.GitVCS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Nathnael on 8/19/2017.
 */
public class DeserializerImpl implements Deserializer {
    @Override
    public File deserializeTextFile(String filename) {
        File originalFile = null;
        String newFilename = filename.substring(0,filename.indexOf('.'));
        try{

            FileInputStream fileIn = new FileInputStream(GitVCS.RESOURCES_DIRECTORY+".gitlet/"+ newFilename + ".ser");
            ObjectInputStream input = new ObjectInputStream(fileIn);
            originalFile = (File) input.readObject();
            input.close();
            fileIn.close();



        } catch (IOException i){
            i.printStackTrace();

        } catch(ClassNotFoundException cn){
            cn.printStackTrace();

        }
        return originalFile;
    }

    @Override
    public CommitTree deserializeCommitTree() {

        CommitTree commitTree = null;
        try{

            FileInputStream fileIn = new FileInputStream(GitVCS.RESOURCES_DIRECTORY+".gitlet/commitTree.ser");
            ObjectInputStream input = new ObjectInputStream(fileIn);
            commitTree = (CommitTree) input.readObject();
            input.close();
            fileIn.close();



        } catch (IOException i){
            i.printStackTrace();

        } catch(ClassNotFoundException cn){
            cn.printStackTrace();

        }
        return commitTree;
    }
}
