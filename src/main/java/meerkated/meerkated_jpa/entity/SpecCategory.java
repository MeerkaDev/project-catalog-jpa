package meerkated.meerkated_jpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "specs_categories")
public class SpecCategory {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy = "specCategory")
    private List<ValueSpec> valueSpecs;
    
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
}
