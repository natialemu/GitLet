package Model;

import Model.FileModel.FileInfo;
import Repository.Serializer;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class Snapshot implements Serializable{
    private List<FileInfo> files;
    private final Date dateCreated;
    private int id;



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

    @Override
    public String toString() {
        return "Snapshot {" +
                "id=" + id +
                '}';
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
    public String getSerializedFile(String fileName){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Snapshot snapshot = (Snapshot) o;

        if (id != snapshot.id) return false;
        if (files != null ? !files.equals(snapshot.files) : snapshot.files != null) return false;
        if (dateCreated != null ? !dateCreated.equals(snapshot.dateCreated) : snapshot.dateCreated != null)
            return false;
        return commitMessage != null ? commitMessage.equals(snapshot.commitMessage) : snapshot.commitMessage == null;
    }

    @Override
    public int hashCode() {
        int result = files != null ? files.hashCode() : 0;
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + id;
        result = 31 * result + (commitMessage != null ? commitMessage.hashCode() : 0);
        return result;
    }

//the methods go here!!!
}
