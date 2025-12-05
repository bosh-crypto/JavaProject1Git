import java.util.Scanner;
import java.util.ArrayList;

public class GroceryListManager {
        ArrayList<String> groceryList;
        Scanner sc = new Scanner(System.in);

        public GroceryListManager() {
            groceryList = new ArrayList<>();
             sc = new Scanner(System.in);
        }

        
        public void addItem(String item) {
            groceryList.add(item);
            System.out.println(item + " added to the list.");
        }


        public void removeItem(String item) {
            if (groceryList.remove(item)) {
                System.out.println(item + " removed from the list.");
            } else {
                System.out.println(item + " not found in the list.");
            }
        }


        public void displayList() {
            if (groceryList.isEmpty()) {
                System.out.println("Your grocery list is empty.");
            } else {
                System.out.println("\n--- Your Grocery List ---");
                for (int i = 0; i < groceryList.size(); i++) {
                    System.out.println((i + 1) + ". " + groceryList.get(i));
                }
                System.out.println("-------------------------");
            }
        }

         static void main(String[] args) {
            GroceryListManager java = new GroceryListManager();
            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n--- Grocery List Menu ---");
                System.out.println("1. Add ");
                System.out.println("2. Remove ");
                System.out.println("3. Display List");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter your required item ");
                        String itemToAdd = sc.nextLine();
                        java.addItem(itemToAdd);
                        break;
                    case 2:
                        System.out.print("Enter item that you want to remove: ");
                        String itemToRemove = sc.nextLine();
                        java.removeItem(itemToRemove);
                        break;
                    case 3:
                        java.displayList();
                        break;
                    case 4:
                        System.out.println("Exiting Grocery List Manager. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 4);


        }
    }