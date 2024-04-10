package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class ApplicationMain {
    
    public static void main(String[] args) {
        // - Создать товар [1]
        // - Изменить товар [2]
        // - Удалить товар [3]
        // Выберите действие: ___
    
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        
        
        while (true) {
            System.out.println("""
                
                Чтобы выполнить действия введите числа соответствующие командам:
                - Создать товар [1]
                - Изменить товар [2]
                - Удалить товар [3]
                - Создать категорию товаров [4]
                - Закрыть приложение [0]
                """);
            
            String command = scanner.nextLine();
            
            if (!command.isEmpty()) {
                try {
                    switch (Integer.parseInt(command)) {
                        case 1:
                            CatalogEditorDB.createProduct(scanner, factory);
                            break;
                        case 2:
                            CatalogEditorDB.updateProduct(scanner, factory);
                            break;
                        case 3:
                            CatalogEditorDB.deleteProduct(scanner, factory);
                            break;
                        case 4:
                            CatalogEditorDB.createCategory(scanner, factory);
                            break;
                        case 0:
                            scanner.close();
                            factory.close();
                            break;
                    }
                    
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат ввода числа! Попробуйте снова.");
                }
            }
        }
    }
}
