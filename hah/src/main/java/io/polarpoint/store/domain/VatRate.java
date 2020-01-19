package io.polarpoint.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A VatRate.
 */
@Entity
@Table(name = "vat_rate")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VatRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "rate")
    private Long rate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("vatRates")
    private Product vatRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public VatRate code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getRate() {
        return rate;
    }

    public VatRate rate(Long rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Product getVatRate() {
        return vatRate;
    }

    public VatRate vatRate(Product product) {
        this.vatRate = product;
        return this;
    }

    public void setVatRate(Product product) {
        this.vatRate = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VatRate)) {
            return false;
        }
        return id != null && id.equals(((VatRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VatRate{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", rate=" + getRate() +
            "}";
    }
}
