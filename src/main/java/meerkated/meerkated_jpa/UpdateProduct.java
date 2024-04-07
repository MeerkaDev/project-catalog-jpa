package meerkated.meerkated_jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import meerkated.meerkated_jpa.entity.Product;
import meerkated.meerkated_jpa.entity.SpecCategory;
import meerkated.meerkated_jpa.entity.ValueSpec;

import java.util.List;
import java.util.Scanner;

public class UpdateProduct {
    
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Введите ID изменяемого товара: ");
        Long idIns = Long.valueOf(scanner.nextLine());
    
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        
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
                } else {
                    break;
                }
            }
    
            manager.persist(product);
    
            TypedQuery<ValueSpec> valueSpecTypedQuery = manager.createQuery(
                "select v from ValueSpec v where v.product = ?1 and" +
                    " v.specCategory = ?2", ValueSpec.class
            );
            for (SpecCategory specCategory : specsOfCategory) {
                System.out.print("Введите значение по характеристике - " +
                    specCategory.getName() + ": ");
                String value = scanner.nextLine();
                
                valueSpecTypedQuery.setParameter(1, product);
                valueSpecTypedQuery.setParameter(2, specCategory);
                
                ValueSpec valueSpec = valueSpecTypedQuery.getSingleResult();
    
                valueSpec.setValue(value);
                manager.persist(valueSpec);
                /*if (!value.isEmpty()) {
                    List<ValueSpec> valueSpecs = specCategory.getValueSpecs();
                    for (ValueSpec vp : valueSpecs) {
                        if(vp.getProduct().equals(product) &&
                            vp.getSpecCategory().equals(specCategory)) {
                            vp.setValue(value);
                            manager.persist(vp);
                            break;
                        }
                    }
                }*/
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
