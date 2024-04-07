package meerkated.meerkated_jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne // Много товаров для одной категории
    @JoinColumn(name = "category_id") // Присоединение внешнего ключа
    private Category category;
    
    @OneToMany(mappedBy = "product")
    private List<ValueSpec> valueSpecs;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price")
    private Integer price;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public List<ValueSpec> getValueSpecs() {
        return valueSpecs;
    }
    
    public void setValueSpecs(List<ValueSpec> valueSpecs) {
        this.valueSpecs = valueSpecs;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
}
