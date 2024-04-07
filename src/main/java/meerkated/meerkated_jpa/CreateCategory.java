package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import meerkated.meerkated_jpa.entity.Category;
import meerkated.meerkated_jpa.entity.SpecCategory;
import meerkated.meerkated_jpa.entity.ValueSpec;

import java.util.List;
import java.util.Scanner;

public class CreateCategory {
    
    public static void main(String[] args) {
        // Введите название категории: Мебель
        // Введите характеристики категории: Ширина, Высота, Материал изготовления
        
        // Создать в таблицах категории и характеристики записи которые бы
        // соответствовали введенной информации
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);
        
        TypedQuery<Category> categoryTypedQuery = manager.createQuery(
            "select c from Category c where c.name = ?1", Category.class
        );
    
        String nameIn = "";
        while (true) {
            System.out.print("Введите название категории: ");
            nameIn = scanner.nextLine();
            
            categoryTypedQuery.setParameter(1, nameIn);
            
            List<Category> categories = categoryTypedQuery.getResultList();
            
            if (categories.isEmpty()) {
                break;
            } else {
                System.out.println("Категория с таким название уже существует, введите другую!");
            }
        }
        
        System.out.print("Введите характеристики категории: ");
        String[] specsIn = scanner.nextLine().split(",");
        
        
        try {
            manager.getTransaction().begin();
            
            Category category = new Category();
            category.setName(nameIn);
            manager.persist(category);
            
            for (String spec : specsIn) {
                SpecCategory specCategory = new SpecCategory();
                specCategory.setName(spec);
                specCategory.setCategory(category);
                manager.persist(specCategory);
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
