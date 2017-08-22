package Model.Tools;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class IDGenerator {
    public static int COMMIT_ID = 1;
    public static int getCommitId(){
        COMMIT_ID++;
        return COMMIT_ID;
    }
}
