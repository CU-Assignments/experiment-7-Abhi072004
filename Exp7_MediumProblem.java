import java.sql.*;
import java.util.Scanner;

public class Exp7_MediumProblem {
    private static final String URL = "jdbc:mysql://localhost:3306/ProductDB";
    private static final String USER = "abhishek";
    private static final String PASSWORD = "abc"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

  
    public static void createProduct(String name, double price, int quantity) {
        String query = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            conn.setAutoCommit(false);

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);

            int rowsInserted = stmt.executeUpdate();
            conn.commit();
            System.out.println(rowsInserted > 0 ? "Product added successfully!" : "Insertion failed.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

   
    public static void readProducts() {
        String query = "SELECT * FROM Product";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            System.out.println("Product List:");
            System.out.println("-------------------------------------------------");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ProductID") +
                        ", Name: " + rs.getString("ProductName") +
                        ", Price: " + rs.getDouble("Price") +
                        ", Quantity: " + rs.getInt("Quantity"));
            }
            System.out.println("-------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
    public static void updateProduct(int id, String name, double price, int quantity) {
        String query = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false);

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setInt(4, id);

            int rowsUpdated = stmt.executeUpdate();
            conn.commit(); 
            System.out.println(rowsUpdated > 0 ? "Product updated successfully!" : "Product not found.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

  
    public static void deleteProduct(int id) {
        String query = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            conn.setAutoCommit(false); 

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            conn.commit(); 
            System.out.println(rowsDeleted > 0 ? "Product deleted successfully!" : "Product not found.");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

  
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Product Management System ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    createProduct(name, price, quantity);
                    break;
                case 2:
                    readProducts();
                    break;
                case 3:
                    System.out.print("Enter Product ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter New Product Name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter New Price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Enter New Quantity: ");
                    int newQuantity = scanner.nextInt();
                    updateProduct(updateId, newName, newPrice, newQuantity);
                    break;
                case 4:
                    System.out.print("Enter Product ID to delete: ");
                    int deleteId = scanner.nextInt();
                    deleteProduct(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1-5.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
