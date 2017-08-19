package Model.Tools;

import Model.State.GitVCS;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Created by Nathnael on 8/19/2017.
 */
public class RandomFileGenerator {

    public static int FILE_NAME_SIZE = 8;

    public static int TEXT_SIZE = 10;

    public static String createRandomText(){
        String filename = randomFileName();
        try {
            //PrintWriter writer = new PrintWriter(GitVCS.RESOURCES_DIRECTORY+filename,"UTF-8");
            File file = new File(GitVCS.RESOURCES_DIRECTORY+filename);
            file.createNewFile();
            populateText(filename);

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return filename;
    }

    private static void populateText(String filename) {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(randomText());
            bufferedWriter.write("\n");
            bufferedWriter.write(randomText());
        }catch (IOException ioe){
            ioe.printStackTrace();
        }


    }

    public static boolean randomlyModifyFile(String filename){
        try {
            File file = new File(GitVCS.RESOURCES_DIRECTORY + filename);
            if (!file.exists()) {
                return false;
            } else {
                String fileContext = FileUtils.readFileToString(file);
                fileContext = fileContext.concat(randomText());
                FileUtils.write(file,fileContext);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    private static String randomText(){
        StringBuilder text = new StringBuilder();

        for(int i = 0; i < TEXT_SIZE; i++){
            char A = 'A';
            int rand = (int)(Math.random()*26);
            char newCharacter = (char) (A + rand);
            text.append(newCharacter);
        }
    }


    private static String randomFileName() {

        StringBuilder filename = new StringBuilder();

        for(int i = 0; i < FILE_NAME_SIZE; i++){
            char A = 'A';
            int rand = (int)(Math.random()*26);
            char newCharacter = (char) (A + rand);
            filename.append(newCharacter);
        }

        return filename.toString();


    }
}
