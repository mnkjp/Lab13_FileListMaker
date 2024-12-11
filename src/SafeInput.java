import java.util.Scanner;
public class SafeInput {
    /**
     * @param pipe
     * @param prompt
     * @return
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retVal = "";
        do {
            System.out.print(prompt + ": ");
            retVal = pipe.nextLine();
        } while (retVal.isEmpty());
        System.out.println("You must enter at least 1 character");
        return retVal;
    }
    public static int getInt(Scanner pipe, String prompt) {
        int retVal = 0;
        boolean done = false;
        String trash = "";
        do {
            System.out.print(prompt + " ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("Please enter an valid number, not " + trash);
            }
        } while (!done);
        return retVal;
    }
    public static double getDouble(Scanner in, String prompt) {
        double retVal = 0;
        String trash = "";
        System.out.print(prompt);
        boolean done = false;
        do {
            System.out.println(prompt);
            if (in.hasNextInt()) {
                retVal = in.nextDouble();
                in.nextLine(); // clears buffer
                done = true;
            } else {
                trash = in.nextLine();
                System.out.println("Please enter an valid double, not" + trash);
            }
        } while (!done);
        return retVal;
    }
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String input ="";
        boolean retVal = false;
        boolean done = false;
        do{
            System.out.print(prompt + "[Y/N]");
            input = pipe.nextLine();
            if (input.isEmpty()) {
                System.out.println("you must enter y or n");
            }
            else if (input.equalsIgnoreCase("Y")) {
                retVal = true;
                done = true;
            }
            else if (input.equalsIgnoreCase("N")) {
                retVal = false;
                done = true;
            }
            else
            {
                System.out.println("Please enter a valid input, not " + input);
            }
        }while (!done);
        return retVal;
    }
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double retVal = 0;
        boolean done = false;
        String trash = "";
        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                retVal = pipe.nextDouble();
                pipe.nextLine();
                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("Please enter a value between " + low + " and " + high + ".");
                }
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid double, not" + trash);
            }
        } while (!done);
        return retVal;
    }
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retVal = 0;
        boolean done = false;
        String trash = "";
        do {
            System.out.print(prompt + " [" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                retVal = pipe.nextInt();
                pipe.nextLine();
                if (retVal >= low && retVal <= high) {
                    done = true;
                } else {
                    System.out.println("Please enter a value between " + low + " and " + high + ".");
                }
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number, not " + trash);
            }
        } while (!done);
        return retVal;
    }
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String retVal = "";
        boolean done = false;
        do {
            System.out.print(prompt + regEx + ": ");
            retVal = pipe.nextLine();
            if (retVal.matches(regEx)) {
                done = true;
            } else {
                System.out.println("Please enter a value that matches the pattern, not " + retVal);
            }
        } while (!done);
        return retVal;
    }
    public static String prettyHeader (String msg){
        final int totalWidth = 60;
        final int padding = 3;
        int msgLength = msg.length();
        int availableWidth = totalWidth - 2 * padding;
        String formattedMsg;
        if(msgLength > availableWidth) {
            formattedMsg = msg.substring(0, availableWidth - 3) + "...";
        }
        else {
            int spacesBefore = (availableWidth - msgLength) / 2;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < spacesBefore; i++) {
                sb.append(" ");
            }
            formattedMsg = sb.toString() + msg;
        }
        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.print("***");
        System.out.print(formattedMsg);
        System.out.println("                 ***");
        for(int i = 0; i < totalWidth; i++){
            System.out.print("*");
        }
        System.out.println();
        return formattedMsg;
    }
    public static double CtoF (double celsius){
        return (celsius * 9 / 5) + 32;
    }
    public static String getString(String prompt) {
        System.out.print(prompt);
        return prompt;
    }
}