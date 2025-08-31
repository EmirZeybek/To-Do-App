package ToDoApp;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;



public class TaskManager {

    Scanner in = new Scanner(System.in);

    Random rand = new Random();

    private int nextId;
    private DatabaseConnector db = new DatabaseConnector();

    public void addTask(){
        System.out.print("Enter a description: ");
        String description = in.next();
        nextId = rand.nextInt(0,100);
        System.out.print("Declare a category (Personal, Study...): ");
        String category = in.next();
        System.out.print("Choose a deadline YYYY-MM-DD: ");
        String dateInput = in.next();
        LocalDate deadline = LocalDate.parse(dateInput);

        addTaskToDb(nextId , description,category,deadline);
        System.out.println("Task successfully added: " + description + " | " + category + " | " + deadline);
    }
    public void addTaskToDb( int id, String description, String category, LocalDate deadline){
        String sql = "INSERT INTO tasks (id, status ,description, category, deadline ) VALUES (?,false ,?,?,?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, description);
            stmt.setString(3, category);
            stmt.setDate(4, java.sql.Date.valueOf(deadline));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listTasksFromDb(){
        String sql = "SELECT * FROM tasks";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listTasksFromDbByCategory(String category){
        String sql = "SELECT * FROM tasks WHERE category = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | "
                            + rs.getBoolean("status") + " | "
                            + rs.getString("description") + " | "
                            + rs.getString("category") + " | "
                            + rs.getDate("deadline"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listTasksFromDbByDeadline(){
        String sql = "SELECT * FROM tasks ORDER BY deadline DESC";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | "
                            + rs.getBoolean("status") + " | "
                            + rs.getString("description") + " | "
                            + rs.getString("category") + " | "
                            + rs.getDate("deadline"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listTasksFromDbByStatus(){
        System.out.println("Done tasks: ");

        String sql = "SELECT * FROM tasks WHERE status = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | "
                            + rs.getString("description") + " | "
                            + rs.getString("category") + " | "
                            + rs.getDate("deadline"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("\n-----------------------------\n");
        System.out.println("Pending tasks: ");
        String sql2 = "SELECT * FROM tasks WHERE status = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {
            stmt.setBoolean(1, false);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt("id") + " | "
                            + rs.getString("description") + " | "
                            + rs.getString("category") + " | "
                            + rs.getDate("deadline"));
                }
            }
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteTask( int id){
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void markDone( int id){
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
