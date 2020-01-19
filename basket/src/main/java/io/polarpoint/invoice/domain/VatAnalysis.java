package io.polarpoint.invoice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A VatAnalysis.
 */
@Entity
@Table(name = "vat_analysis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VatAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "vat_code")
    private String vatCode;

    @Column(name = "vat_element", precision = 21, scale = 2)
    private BigDecimal vatElement;

    @ManyToOne
    @JsonIgnoreProperties("vatAnalyses")
    private Basket basket;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVatCode() {
        return vatCode;
    }

    public VatAnalysis vatCode(String vatCode) {
        this.vatCode = vatCode;
        return this;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public BigDecimal getVatElement() {
        return vatElement;
    }

    public VatAnalysis vatElement(BigDecimal vatElement) {
        this.vatElement = vatElement;
        return this;
    }

    public void setVatElement(BigDecimal vatElement) {
        this.vatElement = vatElement;
    }

    public Basket getBasket() {
        return basket;
    }

    public VatAnalysis basket(Basket basket) {
        this.basket = basket;
        return this;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VatAnalysis)) {
            return false;
        }
        return id != null && id.equals(((VatAnalysis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VatAnalysis{" +
            "id=" + getId() +
            ", vatCode='" + getVatCode() + "'" +
            ", vatElement=" + getVatElement() +
            "}";
    }
}
