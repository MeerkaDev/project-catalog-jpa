package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import meerkated.meerkated_jpa.entity.Product;
import meerkated.meerkated_jpa.entity.ValueSpec;

import java.util.List;
import java.util.Scanner;

public class DeleteProduct {
    
    public static void main(String[] args) {
    
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
    
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Введите ID удаляемого товара: ");
        
        Long idIns = Long.valueOf(scanner.nextLine());
        
        try {
            manager.getTransaction().begin();
    
            Product product = manager.find(Product.class, idIns);
            List<ValueSpec> valueSpecs = product.getValueSpecs();
            for (ValueSpec vs : valueSpecs) {
                manager.remove(vs);
            }
            manager.remove(product);
            
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
