package utils;

public class DesignUtils {
    public static void clearScreen(int delay) {
        try {
            Thread.sleep(delay);
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }
    

    public static void printMainMenuHeader() {
        clearScreen(1000);
        System.out.println("===========================================================================");
        System.out.println("|\t                  ╦ ╦╔═╗╔═╗╦═╗╔╦╗╦═╗╔═╗╔═╗                        |");
        System.out.println("|\t     <────────────╠═╣║╣ ╠═╣╠╦╝ ║║╠╦╝║ ║╠═╝────────────>           |");
        System.out.println("|\t                  ╩ ╩╚═╝╩ ╩╩╚══╩╝╩╚═╚═╝╩                          |");
        System.out.println("===========================================================================");
        System.out.println("|         D R O P P I N G  H E L P  W H E R E  I T ' S  H E A R D         |");
        System.out.println("---------------------------------------------------------------------------");
        
    }

    public static void printHeader(String role, String username) {
        clearScreen(1000);
        System.out.println("========================================================================");
        System.out.println("    H E A R D R O P : D R O P P I N G  W H E R E  I T ' S  H E A R D    ");
        System.out.println("========================================================================");
        System.out.println("\t\t\tWELCOME, " + role.toUpperCase() + " " + username.toUpperCase() + "!      ");
        System.out.println("------------------------------------------------------------------------");
    }

    public static void printStart(){
        System.out.println("==============================================================================================================================================");
        System.out.println("|\tWelcome to HearDrop, where your needs are heard. This program is for donors and those in need to get in touch with each other,       | \n|\tthereby making the sharing of resources easier and that extends support to more communities. This is in accordance with with \t     | \n|\tSDG 2: Zero Hunger, by redistributing items with a potential to fulfill basic needs; at the same time, it caters toward SDG \t     | \n|\t12: Responsible Consumption and Production through the sustainable use and sharing of resources. With that being said, \t             |\n|\tthank you for using our services!\t\t\t\t\t\t\t\t\t\t\t\t     |");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------\n");
        System.out.println("Program is loading, Please Wait...");
        clearScreen(5000);
    }

    public static boolean isExitInput(String input) {
        if (input.equalsIgnoreCase("0") || input.equalsIgnoreCase("exit")) {
            System.out.println("Action canceled.");
            return true;
        }
        return false;
    }
}

