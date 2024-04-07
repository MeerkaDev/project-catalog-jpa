package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import meerkated.meerkated_jpa.entity.Category;
import meerkated.meerkated_jpa.entity.Product;

import java.util.List;
import java.util.Scanner;

public class PriceIncrease {
    
    public static void main(String[] args) {
        
        // Введите id категории: 2
        // Введите процент увеличения стоимости: 7
        
        // Увеличить стоимость на 7% товарам категория которых = 2.
        
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        
        Integer categoryIDIn = 0;
        Category category = null;
        while (category == null) {
            System.out.print("Введите ID категории товара: ");
            categoryIDIn = Integer.valueOf(scanner.nextLine());
            category = manager.find(Category.class, categoryIDIn);
        }
        
        System.out.print("Введите процент увеличения стоимости: ");
        int increasePercent = Integer.parseInt(scanner.nextLine());
        
        List<Product> products = category.getProducts();
        
        try {
            manager.getTransaction().begin();
            
            for (Product pr : products) {
                int priceOld = pr.getPrice();
                int priceNew = priceOld + (priceOld * increasePercent / 100);
                pr.setPrice(priceNew);
                manager.persist(pr);
            }
            
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        
        scanner.close();
        manager.close();
        factory.close();
    }
}
