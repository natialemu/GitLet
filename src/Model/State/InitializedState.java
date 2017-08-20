package Model.State;

import Model.CommitTree;
import Model.FileModel.FileInfo;
import Model.FileModel.FileInfoConcrete;
import Model.Snapshot;
import Model.Tools.IDGenerator;

import java.io.*;
import java.util.*;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class InitializedState implements GitLetStateMachine{
    public CommitTree getTree() {
        return tree;
    }

    private CommitTree tree;

    public List<String> getWorkingDirectory() {
        return workingDirectory;
    }

    private List<String> workingDirectory;
    private GitVCS gitLet;


    //static int commit_id = IDGenerator.getCommitId();
    public InitializedState(GitVCS gitLet){
        this.gitLet = gitLet;
        tree = new CommitTree();
        workingDirectory = new ArrayList<>();
    }
    @Override
    public Void init() {
        return null;
    }

    @Override
    public Void add(String filename) {

        File toBeAdded = new File(GitVCS.RESOURCES_DIRECTORY+filename);
        if(!toBeAdded.exists()){
            System.out.println("File does not exist.");
        }else{
            if(workingDirectory.contains(filename)){
                System.out.println("File is already inside working directory");
            }else{

                if(tree.fileIsInLastCommit(filename) && tree.fileNotModifiedSinceLastCommit(filename)){
                    System.out.println("File has not been modified since last commit");
                }else if(tree.getLastCommit().isFileMarked(filename)){
                    tree.getLastCommit().unMarkFile(filename);
                }else{
                    workingDirectory.add(filename);
                }
            }

        }

        //first check if the filename exists in the last commit in the commit tree
        //then deserialize the one in the last commit and compare it to the file to be added
        //if they are the same, then display the meesage "File has not been modified since the last commit"



        return null;
    }

    public void writeToFile(File originalFile, String filename){
        try {
            File outputFile = new File(GitVCS.RESOURCES_DIRECTORY+filename);
            Scanner scanner = new Scanner(originalFile);
            PrintWriter printer = new PrintWriter(GitVCS.RESOURCES_DIRECTORY+outputFile);
            while(scanner.hasNext()){
                String text = scanner.nextLine();
                printer.write(text);
            }
        }catch (IOException io){
            io.printStackTrace();
        }


    }



    @Override
    public Void commit(String message) {
        //create a list to hold the files to be commmited!
        List<String> commitFiles = new ArrayList<>();
        // if the content of the working directory is empty, or nothing is flagged in the
        //old commit, then it will abort and print the message "No changes added to commit"
        Snapshot lastCommit = tree.getLastCommit();
        if(workingDirectory.isEmpty() && !lastCommit.flagsExist()){
            System.out.println("No changes added to commit.");
            return null;

        }else if(message.equals("")){
            System.out.println("Provide a commit message and try again");
            return null;
        }
        for(int i = 0; i < workingDirectory.size(); i++){

            commitFiles.add(workingDirectory.get(i));

        }

        for(int i = 0; i  < lastCommit.getFiles().size(); i++){
            if(commitFiles.contains(lastCommit.getFiles().get(i).getFilename()) || !lastCommit.getFiles().get(i).tagged()){
                continue;

            }
            else{
                commitFiles.add(lastCommit.getFiles().get(i).getFilename());
            }
        }

        int commit_id = IDGenerator.getCommitId();
        Snapshot currentCommit = new Snapshot(commit_id,message);
        for(int i = 0; i < commitFiles.size(); i++){

            String serializedFileName = gitLet.getSerializer().serializeTextFile(commitFiles.get(i),commit_id);
            FileInfo info = new FileInfoConcrete(commitFiles.get(i),serializedFileName);
            currentCommit.addFiles(info);
        }





        // then add the snapshot into the commit tree and adjust where the current pointer points to in the hashmap
        //als omap the hashmap of ID to snapshot.
        tree.addSnapshotToTree(currentCommit);


        //make sure to serialize the files in the commit and put them in the .gitlet directory
        //change the serializeable name to the filenmae + the id of the commit it is in.

        workingDirectory.removeAll(workingDirectory);

        gitLet.getSerializer().serializeCommitTree(tree);

        return null;
    }

    @Override
    public Void rm(String filename) {
        //go through the working directory and check if the filename exists, if so remove it
        //go throught the files in the previous commit and search for filename
        //i fyou find it, then mark it for removal
        Snapshot lastCommit = tree.getLastCommit();
        if(!workingDirectory.contains(filename) && !lastCommit.exists(filename)){
            System.out.println("No reason to remove the file.");
            return null;

        }

        if(workingDirectory.contains(filename)){
            workingDirectory.remove(filename);
        }else {

            if (lastCommit.exists(filename) && lastCommit.flagsExist()) {
                for (int i = 0; i < lastCommit.getFiles().size(); i++) {
                    if (lastCommit.getFiles().get(i).equals(filename)) {
                        lastCommit.getFiles().get(i).tagForRemoval();

                    }

                }
            }
        }


        gitLet.getSerializer().serializeCommitTree(tree);


        //if file is not in working directory or in previous commit print "No reason to remove the file".

        return null;
    }

    @Override
    public Void log() {
        //List<Integer> commitTree = tree.getCommitTree();
        //get the id that corresponds to the current pointer
        //determine the index of the id in the list
        // go in reverse order starting from that index
        tree.displayLog();

        return null;
    }

    @Override
    public Void globalLog() {
        tree.displayGlobalLog();
        return null;
    }
    @Override
    public Void find(String message) {
        List<Snapshot> commits = tree.getCommitWithMessage(message);
        System.out.println("Commits with the given message: \n");
        for(Snapshot s: commits){
            System.out.println("Commit id: " +  s.getId());
            //print out the id of the snapshot
        }
        return null;
    }


    @Override
    public Void status() {
        List<String> filesMarkedForRemoval = new ArrayList<>();
        Snapshot lastCommit = tree.getLastCommit();
        for(FileInfo f: lastCommit.getFiles()){
            if(f.tagged()){
                filesMarkedForRemoval.add(f.getFilename());
            }
        }
        Set<String> branches = tree.getBranches();
        String currentBranch = tree.getCurrentBranch();
        Iterator<String> setIterator = branches.iterator();
        System.out.println("=== Branches ===");
        System.out.println("*"+currentBranch);
        while (setIterator.hasNext()){
            String branch = setIterator.next();
            if(branch != currentBranch){
                System.out.println(branch);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files");
        for(String s: workingDirectory){
            System.out.println(s);
        }
        System.out.println();

        System.out.println("=== Files Marked for Removal ===");
        for(String s: filesMarkedForRemoval){
            System.out.println(s);
        }

        return null;
    }

    @Override
    public Void checkout(String filename) {

        File inputFile = new File(GitVCS.RESOURCES_DIRECTORY+filename);
        if(!inputFile.exists()){
            System.out.println("Requested file does not exist. Please provide a correct file");
            return null;
        }
        if(!tree.fileIsInLastCommit(filename)){
            System.out.println("File does not exist in the most recent commit, or no such branch exists.");
            return null;
        }
        Snapshot lastCommit = tree.getLastCommit();
        String serializedName = lastCommit.getSerializedFile(filename);
        File fileFromLastCommit = gitLet.getDeserializer().deserializeTextFile(serializedName);

        copyFile(fileFromLastCommit,inputFile);

        //first make sure file exists in current working directory
        //second make sure file exists in last commit
        // get file from last commit
        //get deserialized file
        //replace the current file with the deserialized file
        //
        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
    }

    public void copyFile(File source, File desination){
        try{
            FileInputStream inputStream= new FileInputStream(source);
            FileOutputStream outputStream = new FileOutputStream(desination);

            byte[] buffer = new byte[1024];

            int length;

            while((length = inputStream.read(buffer)) > 0){
                outputStream.write(buffer,0,length);
            }
            inputStream.close();
            outputStream.close();

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public Void checkout(int commitId, String filename) {
        File inputFile = new File(GitVCS.RESOURCES_DIRECTORY+filename);
        if(!inputFile.exists()){
            System.out.println("Requested file does not exist. Please provide a correct file");
            return null;
        }

        Snapshot commit = tree.getCommit(commitId);
        if(commit == null){
            System.out.println("No commit with that id exists.");
            return null;
        }
        if(!commit.exists(filename)){
            System.out.println("File does not exist in that  commit.");
            return null;
        }
        String serializedName = commit.getSerializedFile(filename);
        File fileFromLastCommit = gitLet.getDeserializer().deserializeTextFile(serializedName);

        copyFile(fileFromLastCommit,inputFile);
        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
    }

    @Override
    public Void checkoutv2(String brachname) {
        //get the commit id of the branch
        tree.setCurrentBranchName(brachname);
        for(String filename: workingDirectory){
            checkout(filename);
        }
        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
    }

    @Override
    public Void branch(String branchName) {
        //creates a new branch
        //points to the same commit as the current branch
        //does not change the active branch
        tree.addBranch(branchName);

        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
    }

    @Override
    public Void rmBranch(String branchName) {
        //check if branch exists
        if(tree.getCurrentBranch().equals(branchName)){
            System.out.println("Cannot remove the current branch.");
        } else if(!tree.removeBranch(branchName)){
            System.out.println("A branch with that name does not exist");
        }
        gitLet.getSerializer().serializeCommitTree(tree);

        return null;
    }

    @Override
    public Void reset(int commitId) {
        //get the commit
        //for all files in the commit:
        //    if file exists in the current directory:
        //           get serializeable of the file in commit
        //             copy files

        Snapshot commit = tree.getCommit(commitId);

        for(FileInfo fi: commit.getFiles()){
            String fileInCommit = fi.getFilename();
            File output = new File(GitVCS.RESOURCES_DIRECTORY+fileInCommit);
            if(output.exists()){
                String serializedFile = fi.getSerializedFileName();
                File serializedFileOutput = gitLet.getDeserializer().deserializeTextFile(serializedFile);
                copyFile(serializedFileOutput,output);
            }

        }

        return null;
    }

    @Override
    public Void merge(String brachName) {

        //find the earliest common ancestor of the given branch and current branch
        //identify a modified file within an interval in a branch
        //if there are any files in both branches, the one in given replaces the one in current
        Snapshot splitPoint = tree.getSplitPoint(brachName);

        Snapshot lastCommitOfGivenBranch = tree.getLastCommit(brachName);
        for(FileInfo fileInfo: lastCommitOfGivenBranch.getFiles()){
            if(tree.fileHasBeenModified(fileInfo.getFilename(),brachName,splitPoint) && tree.fileHasBeenModified(fileInfo.getFilename(),tree.getCurrentBranch(),splitPoint)){
                createNewFile(fileInfo.getFilename(),fileInfo.getSerializedFileName());
                /*String newFileName = fileInfo.getFilename() + ".conflicted";
                add(newFileName);
                commit("added conflicting file to current commit ");
                */
            }else if(tree.fileHasBeenModified(fileInfo.getFilename(),brachName,splitPoint) && !tree.fileHasBeenModified(fileInfo.getFilename(),tree.getCurrentBranch(),splitPoint)){

                //Do Nothing


            }else if(!tree.fileHasBeenModified(fileInfo.getFilename(),brachName,splitPoint) && tree.fileHasBeenModified(fileInfo.getFilename(),tree.getCurrentBranch(),splitPoint)){

                File source = gitLet.getDeserializer().deserializeTextFile(fileInfo.getSerializedFileName());
                ;
                File desitnation = gitLet.getDeserializer().deserializeTextFile(tree.getLastCommit().getSerializeable(fileInfo.getSerializedFileName()));
                copyFile(source,desitnation);
            }
        }
        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
        //for each file in the given branch:
        //            if that file is modified in the given branch AND the current branch:
        //                            do something
                                      //create a new file with the name filename.conflicted
        //                            add the conflict in the current branch without replacing
        //            if that file is modified ONLY in the given branch:
        //                             do something
        //                            leave them
        //            if that file has been modified ONLY in the current branch:
        //                              do something
        //                                   copy file in current branch with given branch
        //


        //return null;

    }

    private void createNewFile(String filename, String serializedFileName) {
        try {
            PrintWriter writer = new PrintWriter(GitVCS.RESOURCES_DIRECTORY+filename+".conflicted","UTF-8");

            writer.close();
            File newFile = new File(GitVCS.RESOURCES_DIRECTORY+filename+".conflicted");
            File source  = gitLet.getDeserializer().deserializeTextFile(serializedFileName);
            copyFile(source,newFile);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    public boolean isInWorkingDirector(String filename){
        return workingDirectory.contains(filename);
    }

    @Override
    public Void rebase(String branchName) {

        //get the split point
        Snapshot splitPoint = tree.getSplitPoint(branchName);
        tree.rebase(branchName,splitPoint);

        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
    }

    @Override
    public Void interactiveRebase(String branchName) {

        Snapshot splitPoint = tree.getSplitPoint(branchName);
        tree.interactiveRebase(branchName,splitPoint);

        gitLet.getSerializer().serializeCommitTree(tree);
        return null;
    }

    @Override
    public void setCommitTree(CommitTree commitTree) {

        tree = commitTree;
    }
}
