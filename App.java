package ToDoApp;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager tm = new TaskManager();

        System.out.println("Welcome \"To\" Do App\n");

        int choice = 6;
        while (choice != 5){
            System.out.println("1. Add a task");
            System.out.println("2. List and filter tasks");
            System.out.println("3. Delete a task");
            System.out.println("4. Mark a task done");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
            }
            catch (Exception e) {
                System.out.println("Invalid choice");
            }
            switch (choice){
                case 1:
                    tm.addTask();
                    break;
                case 2:
                    System.out.println("1. List all tasks by separating whether the task is done or not done");
                    System.out.println("2. List all tasks due to deadline");
                    System.out.println("3. List all tasks by category\n");
                    System.out.print("Enter your choice: ");

                    int choice2 = sc.nextInt();

                    switch ( choice2 ){
                        case 1:
                            tm.listTasksFromDbByStatus();
                            break;
                        case 2:
                            tm.listTasksFromDbByDeadline();
                            break;
                        case 3:
                            System.out.print("Enter the category you want to filter: ");
                            String category = sc.next();
                            tm.listTasksFromDbByCategory(category);
                            break;
                    }
                    break;
                case 3:
                    tm.listTasksFromDb();
                    System.out.print("Enter the task's id: ");
                    int id = sc.nextInt();
                    tm.deleteTask(id);
                    System.out.println("The task has been deleted");
                    break;
                case 4:
                    tm.listTasksFromDb();
                    System.out.print("Enter the task's id: ");
                    int id2 = sc.nextInt();
                    tm.markDone(id2);
                    System.out.println("The task has been marked done");
                    break;
        }

    }
    }
}