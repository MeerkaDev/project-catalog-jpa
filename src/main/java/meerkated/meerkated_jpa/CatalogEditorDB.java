package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import meerkated.meerkated_jpa.entity.Category;
import meerkated.meerkated_jpa.entity.Product;
import meerkated.meerkated_jpa.entity.SpecCategory;
import meerkated.meerkated_jpa.entity.ValueSpec;

import java.util.List;
import java.util.Scanner;

public class CatalogEditorDB {
    
    // Создание товара
    public static void createProduct(Scanner scanner, EntityManagerFactory factory) {
        EntityManager manager = factory.createEntityManager();
        System.out.print("Введите название товара: ");
        String nameIn = scanner.nextLine();
        System.out.print("Введите цену товара: ");
        Integer priceIn = Integer.valueOf(scanner.nextLine());
        Long categoryIDIn = 0L;
        Category category = null;
        while (category == null) {
            System.out.print("Введите ID категории товара: ");
            categoryIDIn = Long.valueOf(scanner.nextLine());
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
        } finally {
            manager.close();
        }
    }
    
    // Редактирование товара
    public static void updateProduct(Scanner scanner, EntityManagerFactory factory) {
        EntityManager manager = factory.createEntityManager();
        System.out.print("Введите ID изменяемого товара: ");
        Long idIns = Long.valueOf(scanner.nextLine());
        
        try {
            manager.getTransaction().begin();
            
            Product product = manager.find(Product.class, idIns);
            
            List<SpecCategory> specsOfCategory = product.getCategory().getSpecCategories();
            
            System.out.println("Не вводите значение и нажмите Enter," +
                " если не хотите вносить изменение в текущей информации о товаре");
            
            System.out.print("Введите новое название для товара "
                + product.getName() + ": ");
            String NameNew = scanner.nextLine();
            if (!NameNew.isEmpty()) {
                product.setName(NameNew);
            }
            
            while (true) {
                System.out.print("Введите новую цену для товара "
                    + product.getName() + ": ");
                
                String priceNew = scanner.nextLine();
                if (!priceNew.isEmpty()) {
                    try {
                        product.setPrice(Integer.valueOf(priceNew));
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Неверный формат ввода числа! Попробуйте снова.");
                    }
                }
            }
            
            manager.persist(product);
            for (SpecCategory specCategory : specsOfCategory) {
                System.out.print("Введите значение по характеристике - " +
                    specCategory.getName() + ": ");
                String value = scanner.nextLine();
                if (!value.isEmpty()) {
                    List<ValueSpec> valueSpecs = specCategory.getValueSpecs();
                    for (ValueSpec vp : valueSpecs) {
                        if (vp.getProduct().equals(product) &&
                            vp.getSpecCategory().equals(specCategory)) {
                            vp.setValue(value);
                            manager.persist(vp);
                            break;
                        }
                    }
                }
            }
            
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }
    
    // Удаление товара
    public static void deleteProduct(Scanner scanner, EntityManagerFactory factory) {
        EntityManager manager = factory.createEntityManager();
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
        } finally {
            manager.close();
        }
    }
    
    // Создание категории
    public static void createCategory(Scanner scanner, EntityManagerFactory factory) {
        EntityManager manager = factory.createEntityManager();
        
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
        } finally {
            manager.close();
        }
    }
}
