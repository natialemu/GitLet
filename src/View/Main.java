package View;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class Main {
    public static Set<String> KEY_COMMANDS = new HashSet<>();

    public static void main(String[] args) {


        System.out.println("           ==== GitLet ====                  ");
        System.out.print(".\\workingDirectory\\>> ");


        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();


        GitLet gitLet = new GitLetFacade();


        while (!command.equals("") && !command.equals("quit")) {
            //System.out.print(".\\workingDirectory\\>> ");
            String[] arguments = command.split(" ");

            if (!arguments[0].equals("gitlet")) {
                System.out.println("Command must begin with gitlet keyword");
                System.out.print(".\\workingDirectory\\>> ");
                scanner = new Scanner(System.in);
                command = scanner.nextLine();
                continue;
            }
            if (arguments[1].equals("add") && arguments.length == 3) {
                //String secondArgumnet = command.substring(command.indexOf(" ")+1,command.length());
                gitLet.add(arguments[2]);

            } else if (arguments[1].equals("commit") && arguments.length == 3) {
                //TODO: concatenate all arguments after index 1
                //TODO: make sure the message is in quotes
                gitLet.commit(arguments[2]);

            } else if (arguments[1].equals("rm") && arguments.length == 3) {
                gitLet.rm(arguments[2]);

            } else if (arguments[1].equals("log") && arguments.length == 2) {
                gitLet.log();

            } else if (arguments[1].equals("global-l0g") && arguments.length == 2) {
                gitLet.globalLog();

            } else if (arguments[1].equals("find") && arguments.length == 3) {
                gitLet.find(arguments[2]);

            } else if (arguments[1].equals("status") && arguments.length == 2) {
                gitLet.status();

            } else if (arguments[1].equals("checkout")) {
                if (arguments.length == 4) {
                    gitLet.checkout(Integer.parseInt(arguments[2]), arguments[3]);
                } else if (arguments.length == 3 && gitLet.isBranchName(arguments[2])) {
                    gitLet.checkoutV2(arguments[2]);
                } else {
                    gitLet.checkout(arguments[2]);

                }


            } else if (arguments[1].equals("rm-branch") && arguments.length == 3) {
                gitLet.rmBranch(arguments[2]);

            } else if (arguments[1].equals("reset") && arguments.length ==3) {
                gitLet.reset(Integer.parseInt(arguments[2]));

            } else if (arguments[1].equals("merge") && arguments.length == 3) {
                gitLet.merge(arguments[2]);

            } else if (arguments[1].equals("rebase") && arguments.length == 3) {
                gitLet.rebase(arguments[2]);

            }else if(arguments[1].equals("interactive-rebase") && arguments.length == 2){
                gitLet.interactiveRebase(arguments[2]);

            }
            System.out.print(".\\workingDirectory\\>> ");
            scanner = new Scanner(System.in);
            command = scanner.nextLine();
            /*
            if command starts with any of KEY_COMMANDS:
                    get the remaining part out of the key commands
                    call the correspoinding command
             */

        }

    }
}
