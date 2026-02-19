import java.util.ArrayList;
import java.util.Scanner;

class Car {
    private String model;
    private String color;
    private int year;
    private double price;
    private String status; // "Available" or "Sold"

    public Car(String model, String color, int year, double price) {
        this.model = model;
        this.color = color;
        this.year = year;
        this.price = price;
        this.status = "Available";
    }

    // Getters
    public String getModel()  { return model; }
    public String getColor()  { return color; }
    public int    getYear()   { return year; }
    public double getPrice()  { return price; }
    public String getStatus() { return status; }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    public void displayInfo() {
        System.out.println("----------------------------------------");
        System.out.println("Model  : " + model);
        System.out.println("Color  : " + color);
        System.out.println("Year   : " + year);
        System.out.println("Price  : ₹" + String.format("%,.0f", price));
        System.out.println("Status : " + status);
        System.out.println("----------------------------------------");
    }
}

public class CarShowroomSystem {

    private static ArrayList<Car> inventory = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   WELCOME TO MINI CAR SHOWROOM     ");
        System.out.println("       Management System            ");
        System.out.println("=====================================");

        while (true) {
            printMenu();
            int choice = getValidInt("Enter your choice (1-6): ");

            switch (choice) {
                case 1 -> addNewCar();
                case 2 -> viewAllCars();
                case 3 -> searchCarByModel();
                case 4 -> sellCar();
                case 5 -> showAvailableCarsCount();
                case 6 -> {
                    System.out.println("\nThank you for using Mini Showroom System!");
                    System.out.println("Goodbye! 👋");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Please enter 1 to 6.");
            }

            System.out.println("\nPress Enter to continue...");
            sc.nextLine(); // safe now because we consume leftover newline properly
        }
    }

    private static void printMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Add New Car");
        System.out.println("2. View All Cars");
        System.out.println("3. Search Car by Model");
        System.out.println("4. Sell a Car");
        System.out.println("5. Show Available Cars Count");
        System.out.println("6. Exit");
    }

    private static void addNewCar() {
        System.out.println("\n--- Add New Car ---");

        System.out.print("Enter Car Model (e.g. Creta, Thar, BMW X1): ");
        String model = sc.nextLine().trim();

        System.out.print("Enter Color: ");
        String color = sc.nextLine().trim();

        int year = getValidInt("Enter Manufacturing Year (e.g. 2023): ");

        double price = getValidDouble("Enter Price (in rupees): ");

        Car newCar = new Car(model, color, year, price);
        inventory.add(newCar);

        System.out.println("\nCar added successfully!");
        newCar.displayInfo();
    }

    private static void viewAllCars() {
        if (inventory.isEmpty()) {
            System.out.println("\nNo cars in showroom yet.");
            return;
        }

        System.out.println("\n--- All Cars in Showroom ---");
        for (Car car : inventory) {
            car.displayInfo();
        }
    }

    private static void searchCarByModel() {
        if (inventory.isEmpty()) {
            System.out.println("\nShowroom is empty!");
            return;
        }

        System.out.print("\nEnter model to search: ");
        String searchModel = sc.nextLine().trim().toLowerCase();

        boolean found = false;
        for (Car car : inventory) {
            if (car.getModel().toLowerCase().contains(searchModel)) {
                car.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No car found with model containing: " + searchModel);
        }
    }

    private static void sellCar() {
        if (inventory.isEmpty()) {
            System.out.println("\nNo cars available to sell.");
            return;
        }

        viewAllCars();
        System.out.print("\nEnter model name of car to sell: ");
        String sellModel = sc.nextLine().trim().toLowerCase();

        for (Car car : inventory) {
            if (car.getModel().toLowerCase().contains(sellModel) 
                && car.getStatus().equals("Available")) {
                car.setStatus("Sold");
                System.out.println("\nCar SOLD successfully!");
                car.displayInfo();
                return;
            }
        }

        System.out.println("Car not found or already sold.");
    }

    private static void showAvailableCarsCount() {
        int count = 0;
        for (Car car : inventory) {
            if (car.getStatus().equals("Available")) {
                count++;
            }
        }
        System.out.println("\nAvailable cars in showroom: " + count);
    }

    // Helper: get valid integer
    private static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    // Helper: get valid double
    private static double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (e.g. 1250000)");
            }
        }
    }
}