package Model;

import Model.FileModel.FileInfo;

import java.io.File;
import java.util.*;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class Snapshot {
    private List<FileInfo> files;
    private final Date dateCreated;
    private final int id;

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    private String commitMessage;
    private boolean visited;
    Calendar calendar;

    public Date getDateCreated() {
        return dateCreated;
    }

    public Snapshot(Snapshot copySnapShot, int id){
        files = copySnapShot.getFiles();
        dateCreated = copySnapShot.getDateCreated();
        this.id = id;
        this.commitMessage = copySnapShot.getCommitMessage();
    }

    public Snapshot(int id, String commitMessage){
        //initialize the Date object above
        files = new ArrayList<>();
        this.id = id;
        this.commitMessage = commitMessage;
        visited = false;
        calendar = new GregorianCalendar();
        dateCreated = calendar.getTime();
    }

    public void addFiles(FileInfo file)
    {
        files.add(file);
    }

    public int getId(){
        return id;
    }


    public boolean exists(String filename){
        for(FileInfo file : files){
            if(file.getFilename() == filename){
                return true;
            }

        }
        return false;


    }

    public String getSerializeable(String fileName){
        for(FileInfo fileInfo: files){
            if(fileInfo.getFilename().equals(fileName)){
                return fileInfo.getSerializedFileName();
            }
        }
        return null;
    }

    public boolean isFileMarked(String filename){
        //check if file exists is marked for removal
        return false;

    }

    public void unMarkFile(String filename){
        //check if file is tagged for removal and if so, untag it
    }

    public boolean flagsExist(){
        for(FileInfo fi : files){
            if(fi.tagged()){
                return true;
            }
        }
        return false;

    }
    public String getFile(String fileName){
        for(FileInfo f: files){
            if(f.getFilename().equals(fileName)){
                return f.getSerializedFileName();
            }
        }
        return null;
    }

    public List<FileInfo> getFiles(){
        return files;
    }


    //the methods go here!!!
}
