package meerkated.meerkated_jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "values_specs")
public class ValueSpec {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "value")
    private String value;
    
    @ManyToOne
    @JoinColumn(name = "specs_categories_id")
    private SpecCategory specCategory;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public SpecCategory getSpecCategory() {
        return specCategory;
    }
    
    public void setSpecCategory(SpecCategory specCategory) {
        this.specCategory = specCategory;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
}
