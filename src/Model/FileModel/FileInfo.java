package Model.FileModel;

import java.util.Date;

/**
 * Created by Nathnael on 8/17/2017.
 */
public interface FileInfo {
    public boolean tagged();
    public Date getDateCreated();
    public String getSerializedFileName();
    public Void tagForRemoval();
    public Void removeTag();
    public String getFilename();
}
