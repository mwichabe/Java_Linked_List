import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) {
        // Read input_data.txt and initialize data structures
        SortedLinkedList<Subscriber> subscribers = new SortedLinkedList<>();
        SortedLinkedList<Meal> meals = new SortedLinkedList<>();

        try {
            Scanner fileScanner = new Scanner(new File("input_data.txt"));

            int numSubscribers = Integer.parseInt(fileScanner.nextLine().trim());

            // Read subscriber information
            for (int i = 0; i < numSubscribers; i++) {
                String[] subscriberInfo = fileScanner.nextLine().trim().split("\\s+");
                if (subscriberInfo.length == 2) {
                    String firstName = subscriberInfo[0];
                    String lastName = subscriberInfo[1];
                    subscribers.sortedInsert(new Subscriber(firstName, lastName));
                } else {
                    System.err.println("Error: Invalid subscriber information format.");
                    System.exit(1);
                }
            }
            // Read the number of meals
            int numMeals = Integer.parseInt(fileScanner.nextLine().trim());

            // Read meal information
            for (int i = 0; i < numMeals; i++) {
                String mealName = fileScanner.next();
                int availableQuantity = fileScanner.nextInt();
                meals.sortedInsert(new Meal(mealName, availableQuantity));
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: input_data.txt not found.");
            System.exit(1);
        }

        // Implement the menu and interactive functionality

        Scanner scanner = new Scanner(System.in);
        boolean programRunning = true;

        while (programRunning) {
            System.out.println("Choose an operation:");
            System.out.println("f - Finish running the program");
            System.out.println("m - Display information about all meals");
            System.out.println("s - Display information about all subscribers");
            System.out.println("a - Update data when a subscriber adds meals");
            System.out.println("r - Update data when a subscriber removes meals");

            char choice = scanner.next().charAt(0);

            switch (choice) {
                case 'f':
                    programRunning = false;
                    break;
                case 'm':
                    // Display information about all meals
                    displayAllMeals(meals);
                    break;
                case 's':
                    // Display information about all subscribers
                    displayAllSubscribers(subscribers);
                    break;
                case 'a':
                    // Update data when a subscriber adds meals
                    addMealsToSubscriber(subscribers, meals);
                    break;
                case 'r':
                    // Update data when a subscriber removes meals
                    removeMealsFromSubscriber(subscribers, meals);
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a valid operation.");
            }
        }

        // Close scanners or handle resources as needed
        scanner.close();
    }

    // Helper method to display all meals
    private static void displayAllMeals(SortedLinkedList<Meal> meals) {
        System.out.println("Meals:");
        for (Meal meal : meals) {
            System.out.println("Meal: " + meal.getName() + ", Available Quantity: " + meal.getAvailableQuantity());
        }
        System.out.println("--------");
    }

    // Helper method to display all subscribers
    private static void displayAllSubscribers(SortedLinkedList<Subscriber> subscribers) {
        System.out.println("Subscribers:");
        for (Subscriber subscriber : subscribers) {
            System.out.println("Subscriber: " + subscriber.getFirstName() + " " + subscriber.getLastName());
            System.out.println("Subscribed Meals:");
            displayAllMeals(subscriber.getSubscribedMeals());
        }
        System.out.println("--------");
    }

    // Helper method to add meals to a subscriber
    private static void addMealsToSubscriber(SortedLinkedList<Subscriber> subscribers, SortedLinkedList<Meal> meals) {
        // Display subscribers for user to choose
        System.out.println("Choose a subscriber:");
        displayAllSubscribers(subscribers);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the subscriber's last name: ");
            String lastName = scanner.nextLine().trim();

            // Find the subscriber by last name
            Subscriber subscriber = findSubscriberByLastName(subscribers, lastName);

            if (subscriber != null) {
                // Display available meals for user to choose
                System.out.println("Choose a meal to add:");
                displayAllMeals(meals);

                System.out.print("Enter the name of the meal: ");
                String mealName = scanner.nextLine().trim();

                // Find the meal by name
                Meal meal = findMealByName(meals, mealName);

                if (meal != null) {
                    // Prompt for the quantity
                    int availableQuantity = meal.getAvailableQuantity();
                    System.out.println("Available quantity for " + meal.getName() + ": " + availableQuantity);

                    System.out.print("Enter the quantity to add: ");
                    int quantity = scanner.nextInt();

                    // Check if the entered quantity is valid
                    if (quantity > 0 && quantity <= availableQuantity) {
                        // Update subscriber's subscription
                        subscriber.addMealToSubscription(meal, quantity);
                    
                        // Update available quantity of the meal
                        meal.decreaseAvailableQuantity(quantity);
                    
                        System.out.println("Meals added to the subscription successfully.");
                    } else {
                        String notEnoughMealsLetter = generateNotEnoughMealsLetter(subscriber, meal, quantity);
                        // Print the letter to the terminal
                        System.out.println(notEnoughMealsLetter);
                    
                        // Optionally, write the letter to letters.txt
                        writeLetterToFile("letters.txt", notEnoughMealsLetter);
                    }
                    
                } else {
                    System.out.println("Error: Meal not found.");
                }
            } else {
                System.out.println("Error: Subscriber not found.");
            }
        }
    }

    private static String generateNotEnoughMealsLetter(Subscriber subscriber, Meal meal, int requestedQuantity) {
        String letter = "Dear " + subscriber.getFirstName() + " " + subscriber.getLastName() + ",\n\n"
                + "We regret to inform you that we couldn't add the requested quantity of " + meal.getName()
                + " to your subscription. There are not enough meals available.\n\n"
                + "Sincerely,\n The FrozenFood Company";
        return letter;
    }
    private static Meal findMealByName(SortedLinkedList<Meal> meals, String mealName) {
        for (Meal meal : meals) {
            if (meal.getName().equalsIgnoreCase(mealName)) {
                return meal;
            }
        }
        System.out.println("Error: Meal not found.");
        return null;
    }

    private static Subscriber findSubscriberByLastName(SortedLinkedList<Subscriber> subscribers, String lastName) {
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getLastName().equalsIgnoreCase(lastName)) {
                return subscriber;
            }
        }
        System.out.println("Error: Subscriber not found.");
        return null;
    }

    private static void writeLetterToFile(String fileName, String letter) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(letter);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Helper method to remove meals from a subscriber
    private static void removeMealsFromSubscriber(SortedLinkedList<Subscriber> subscribers,
            SortedLinkedList<Meal> meals) {
        // Display subscribers for user to choose
        System.out.println("Choose a subscriber:");
        displayAllSubscribers(subscribers);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the subscriber's last name: ");
            String lastName = scanner.nextLine().trim();

            // Find the subscriber by last name
            Subscriber subscriber = findSubscriberByLastName(subscribers, lastName);

            if (subscriber != null) {
                // Display subscribed meals for the subscriber
                System.out.println(
                        "Subscribed meals for " + subscriber.getFirstName() + " " + subscriber.getLastName() + ":");
                displayAllMeals(subscriber.getSubscribedMeals());

                // Prompt for the meal name to remove
                System.out.print("Enter the name of the meal to remove: ");
                String mealName = scanner.nextLine().trim();

                // Find the meal by name
                Meal meal = findMealByName(meals, mealName);

                if (meal != null) {
                    // Prompt for the quantity to remove
                    System.out.print("Enter the quantity to remove: ");
                    int quantityToRemove = scanner.nextInt();

                    // Check if the entered quantity is valid
                    int totalSubscribedMeals = subscriber.getTotalSubscribedMeals();
                    if (quantityToRemove > 0 && quantityToRemove <= totalSubscribedMeals) {
                        // Update subscriber's subscription
                        subscriber.removeMealFromSubscription(meal, quantityToRemove);

                        // Update available quantity of the meal
                        meal.increaseAvailableQuantity(quantityToRemove);

                        System.out.println("Meals removed from the subscription successfully.");
                    } else {
                        System.out.println("Error: Invalid quantity to remove.");
                    }
                } else {
                    System.out.println("Error: Meal not found.");
                }
            } else {
                System.out.println("Error: Subscriber not found.");
            }
        }
    }

}
