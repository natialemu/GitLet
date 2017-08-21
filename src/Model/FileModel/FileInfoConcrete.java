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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileInfoConcrete that = (FileInfoConcrete) o;

        if (taggedForRemoval != that.taggedForRemoval) return false;
        if (filename != null ? !filename.equals(that.filename) : that.filename != null) return false;
        if (dateCreated != null ? !dateCreated.equals(that.dateCreated) : that.dateCreated != null) return false;
        return serializedFileName != null ? serializedFileName.equals(that.serializedFileName) : that.serializedFileName == null;
    }

    @Override
    public int hashCode() {
        int result = filename != null ? filename.hashCode() : 0;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (taggedForRemoval ? 1 : 0);
        result = 31 * result + (serializedFileName != null ? serializedFileName.hashCode() : 0);
        return result;
    }

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
