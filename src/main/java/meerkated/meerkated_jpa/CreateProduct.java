package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import meerkated.meerkated_jpa.entity.Category;
import meerkated.meerkated_jpa.entity.Product;
import meerkated.meerkated_jpa.entity.SpecCategory;
import meerkated.meerkated_jpa.entity.ValueSpec;

import java.util.List;
import java.util.Scanner;

public class CreateProduct {
    
    public static void main(String[] args) {
    
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        
        System.out.print("Введите название товара: ");
        String nameIn = scanner.nextLine();
        System.out.print("Введите цену товара: ");
        Integer priceIn = Integer.valueOf(scanner.nextLine());
        Integer categoryIDIn = 0;
        Category category = null;
        while (category == null) {
            System.out.print("Введите ID категории товара: ");
            categoryIDIn = Integer.valueOf(scanner.nextLine());
            category = manager.find(Category.class, categoryIDIn);
        }
        
        
        
        try {
            manager.getTransaction().begin();
            
            Product product = new Product();
            product.setName(nameIn);
            product.setPrice(priceIn);
            product.setCategory(category);
            
            manager.persist(product);
            
            List<SpecCategory> specsOfCategory = product.getCategory().getSpecCategories();
            
            for (SpecCategory specCategory : specsOfCategory) {
                System.out.print("Введите значение по характеристике - " +
                    specCategory.getName() + ": ");
                String value = scanner.nextLine();
                ValueSpec valueSpec = new ValueSpec();
                valueSpec.setProduct(product);
                valueSpec.setSpecCategory(specCategory);
                valueSpec.setValue(value);
                manager.persist(valueSpec);
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
