package cat.exemple.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productes")
public class Producte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "preu", precision = 10, scale = 2)
    private BigDecimal preu;

    @Column(name = "stock")
    private Integer stock;

    public Producte() {}

    public Producte(String nom, BigDecimal preu, Integer stock) {
        this.nom = nom;
        this.preu = preu;
        this.stock = stock;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public BigDecimal getPreu() { return preu; }
    public void setPreu(BigDecimal preu) { this.preu = preu; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    @Override
    public String toString() {
        return "Producte [id=" + id + ", nom=" + nom + ", preu=" + preu + ", stock=" + stock + "]";
    }
}