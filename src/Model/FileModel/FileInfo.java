package Model.FileModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nathnael on 8/17/2017.
 */
public interface FileInfo extends Serializable{
    public boolean tagged();
    public Date getDateCreated();
    public String getSerializedFileName();
    public Void tagForRemoval();
    public Void removeTag();
    public String getFilename();
}
