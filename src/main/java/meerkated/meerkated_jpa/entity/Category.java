package meerkated.meerkated_jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @OneToMany(mappedBy = "category")
    // Названия поля в классе продакт, которое ссылается на категорию
    private List<Product> products;
    
    @OneToMany(mappedBy = "category")
    private List<SpecCategory> specCategories;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public List<SpecCategory> getSpecCategories() {
        return specCategories;
    }
    
    public void setSpecCategories(List<SpecCategory> specCategories) {
        this.specCategories = specCategories;
    }
}
