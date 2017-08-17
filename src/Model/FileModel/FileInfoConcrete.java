package Model.FileModel;

import java.util.Date;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class FileInfoConcrete implements FileInfo{
    private String filename;
    private Date dateCreated;
    private boolean taggedForRemoval;
    private String serializedFileName;

    public FileInfoConcrete(String filename, String serializedFileName){
        this.filename = filename;
        this.serializedFileName = serializedFileName;
        taggedForRemoval = false;
        dateCreated = new Date();
    }
    public boolean tagged(){
        return taggedForRemoval;
    }
    public Date getDateCreated(){
        return dateCreated;
    }
    public String getSerializedFileName(){
        return serializedFileName;
    }

    public Void tagForRemoval(){
        taggedForRemoval = true;
        return null;
    }


    public Void removeTag(){
        taggedForRemoval = false;
        return null;
    }
    public String getFilename(){
        return filename;
    }
}
