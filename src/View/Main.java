package View;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Nathnael on 8/17/2017.
 */
public class Main {
    public static Set<String> KEY_COMMANDS = new HashSet<>();
    public static void main(String[] args){


        System.out.println("           ==== GitLet ====                  ");

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();


        GitLet gitLet = new GitLetFacade();




        while(!command.equals("") && !command.equals("quit")){
            System.out.print(".\\workingDirectory\\>> ");

            if(command.startsWith("add ")){
                String secondArgumnet = command.substring(command.indexOf(" ")+1,command.length());
                gitLet.add(secondArgumnet);

            }else if(){

            }
            /*
            if command starts with any of KEY_COMMANDS:
                    get the remaining part out of the key commands
                    call the correspoinding command
             */

        }

    }
}
