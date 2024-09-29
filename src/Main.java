import com.example.dao.UserDAO;
import com.example.dao.UserDAOImpl;
import com.example.model.User;
import com.example.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static UserDAO userDAO = new UserDAOImpl();
    private static UserService userService = new UserService(userDAO);
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
                    filterUsersByName();
                    break;
                case 7:
                    countUsersByEmailDomain();
                    break;
                case 8:
                    getAverageNameLength();
                    break;
                case 9:
                    getUsersInIdRange();
                    break;
                case 10:
                    getUserWithLongestName();
                    break;
                case 11:
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
        System.out.println("\n--- CRUD and Advanced Operations Menu ---");
        System.out.println("1. Create user");
        System.out.println("2. Read user");
        System.out.println("3. Update user");
        System.out.println("4. Delete user");
        System.out.println("5. List all users");
        System.out.println("6. Filter users by name");
        System.out.println("7. Count users by email domain");
        System.out.println("8. Get average name length");
        System.out.println("9. Get users in ID range");
        System.out.println("10. Get user with longest name");
        System.out.println("11. Exit");
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

    private static void filterUsersByName() {
        String namePart = getStringInput("Enter part of the name to filter: ");
        List<User> filteredUsers = userService.filterUsersByName(namePart);
        System.out.println("Filtered users:");
        filteredUsers.forEach(System.out::println);
    }

    private static void countUsersByEmailDomain() {
        Map<String, Long> countByDomain = userService.countUsersByEmailDomain();
        System.out.println("User count by email domain:");
        countByDomain.forEach((domain, count) -> System.out.println(domain + ": " + count));
    }

    private static void getAverageNameLength() {
        double avgLength = userService.getAverageNameLength();
        System.out.printf("Average name length: %.2f\n", avgLength);
    }

    private static void getUsersInIdRange() {
        int minId = getIntInput("Enter minimum ID: ");
        int maxId = getIntInput("Enter maximum ID: ");
        List<User> usersInRange = userService.getUsersInIdRange(minId, maxId);
        System.out.println("Users in ID range " + minId + " to " + maxId + ":");
        usersInRange.forEach(System.out::println);
    }

    private static void getUserWithLongestName() {
        User user = userService.getUserWithLongestName();
        if (user != null) {
            System.out.println("User with longest name: " + user);
        } else {
            System.out.println("No users found.");
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