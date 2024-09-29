import com.example.dao.UserDAO;
import com.example.dao.UserDAOImpl;
import com.example.model.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserDAO userDAO = new UserDAOImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    readUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    listAllUsers();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- CRUD Menu ---");
        System.out.println("1. Create user");
        System.out.println("2. Read user");
        System.out.println("3. Update user");
        System.out.println("4. Delete user");
        System.out.println("5. List all users");
        System.out.println("6. Exit");
    }

    private static void createUser() {
        System.out.println("\n--- Create User ---");
        String name = getStringInput("Enter user name: ");
        String email = getStringInput("Enter user email: ");
        User newUser = new User(name, email);
        userDAO.insert(newUser);
        System.out.println("User created successfully: " + newUser);
    }

    private static void readUser() {
        System.out.println("\n--- Read User ---");
        int id = getIntInput("Enter user ID: ");
        User user = userDAO.getById(id);
        if (user != null) {
            System.out.println("User found: " + user);
        } else {
            System.out.println("User not found with ID: " + id);
        }
    }

    private static void updateUser() {
        System.out.println("\n--- Update User ---");
        int id = getIntInput("Enter user ID to update: ");
        User user = userDAO.getById(id);
        if (user != null) {
            System.out.println("Current user details: " + user);
            String name = getStringInput("Enter new name (press Enter to keep current): ");
            String email = getStringInput("Enter new email (press Enter to keep current): ");
            
            if (!name.isEmpty()) {
                user.setName(name);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            
            userDAO.update(user);
            System.out.println("User updated successfully: " + user);
        } else {
            System.out.println("User not found with ID: " + id);
        }
    }

    private static void deleteUser() {
        System.out.println("\n--- Delete User ---");
        int id = getIntInput("Enter user ID to delete: ");
        User user = userDAO.getById(id);
        if (user != null) {
            userDAO.delete(id);
            System.out.println("User deleted successfully: " + user);
        } else {
            System.out.println("User not found with ID: " + id);
        }
    }

    private static void listAllUsers() {
        System.out.println("\n--- All Users ---");
        List<User> users = userDAO.getAll();
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}