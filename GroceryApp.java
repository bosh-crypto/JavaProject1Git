import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


class Product {
    private String name;
    private int quantity;

    public Product(String name, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.name = name.trim();
        this.quantity = quantity;
    }

    public String getName() { return this.name; }
    public int getQuantity() { return this.quantity; }
    public void setQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.name + " x" + this.quantity;
    }
}

// Abstract base class demonstrating constructors and protected state
abstract class BaseListManager {
    protected ArrayList<Product> list;
    protected String owner;

    public BaseListManager(String owner) {
        this.owner = (owner == null || owner.trim().isEmpty()) ? "Default" : owner.trim();
        this.list = new ArrayList<>();
    }

    public abstract void addProduct(Product p);
    public abstract void removeProduct(String name) throws ItemNotFoundException;
    public void display() {
        if (list.isEmpty()) {
            System.out.println("Your grocery list is empty.");
            return;
        }
        System.out.println("\n--- " + this.owner + "'s Grocery List ---");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
        System.out.println("-------------------------");
    }
}

// Subclass showing inheritance and super usage
class GroceryListManager extends BaseListManager {

    public GroceryListManager(String owner) {
        super(owner); // call parent constructor
    }

    @Override
    public void addProduct(Product p) {
        // If product exists, increase quantity
        for (Product prod : list) {
            if (prod.getName().equalsIgnoreCase(p.getName())) {
                prod.setQuantity(prod.getQuantity() + p.getQuantity());
                System.out.println("Updated " + prod);
                return;
            }
        }
        list.add(p);
        System.out.println(p + " added to the list.");
    }

    @Override
    public void removeProduct(String name) throws ItemNotFoundException {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equalsIgnoreCase(name.trim())) {
                Product removed = list.remove(i);
                System.out.println(removed + " removed from the list.");
                return;
            }
        }
        throw new ItemNotFoundException("Item '" + name + "' not found in the list.");
    }

    // Replace quantity or add new product
    public void addOrReplace(String name, int quantity) {
        for (Product prod : list) {
            if (prod.getName().equalsIgnoreCase(name.trim())) {
                prod.setQuantity(quantity);
                System.out.println("Replaced with " + prod);
                return;
            }
        }
        addProduct(new Product(name, quantity));
    }
}

// Custom checked exception for missing items
class ItemNotFoundException extends Exception {
    public ItemNotFoundException(String message) { super(message); }
}

// Custom unchecked exception for invalid input
class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) { super(message); }
}

// Main application with menu and exception handling
public class GroceryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GroceryListManager manager = new GroceryListManager("Home");
        int choice = -1;

        do {
            System.out.println("\n--- Grocery List Menu ---");
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. Display list");
            System.out.println("4. Add or replace item");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter item name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter quantity: ");
                        int q = sc.nextInt(); sc.nextLine();
                        manager.addProduct(new Product(name, q));
                    }
                    case 2 -> {
                        System.out.print("Enter item name to remove: ");
                        String name = sc.nextLine();
                        manager.removeProduct(name);
                    }
                    case 3 -> manager.display();
                    case 4 -> {
                        System.out.print("Enter item name to add or replace: ");
                        String name = sc.nextLine();
                        System.out.print("Enter quantity: ");
                        int q = sc.nextInt(); sc.nextLine();
                        manager.addOrReplace(name, q);
                    }
                    case 5 -> System.out.println("Exiting Grocery List Manager. Goodbye!");
                    default -> throw new InvalidInputException("Please choose a valid menu option");
                }

            } catch (InputMismatchException ime) {
                System.out.println("Invalid input type. Please enter numbers for menu and quantity.");
                sc.nextLine(); // clear bad token
            } catch (ItemNotFoundException infe) {
                System.out.println("Error: " + infe.getMessage());
            } catch (IllegalArgumentException iae) {
                System.out.println("Validation error: " + iae.getMessage());
            } catch (InvalidInputException iie) {
                System.out.println(iie.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

        } while (choice != 5);

        sc.close();
    }
}