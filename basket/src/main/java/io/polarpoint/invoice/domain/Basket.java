package io.polarpoint.invoice.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entities for Basket microservice
 */
@ApiModel(description = "Entities for Basket microservice")
@Entity
@Table(name = "basket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "basket_id", nullable = false)
    private String basketId;

    @NotNull
    @Column(name = "total_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "txns")
    private String txns;

    @Column(name = "vat_analysis")
    private String vatAnalysis;

    @OneToMany(mappedBy = "basket")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BasketEntry> basketEntries = new HashSet<>();

    @OneToMany(mappedBy = "basket")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VatAnalysis> vatAnalyses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBasketId() {
        return basketId;
    }

    public Basket basketId(String basketId) {
        this.basketId = basketId;
        return this;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Basket totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTxns() {
        return txns;
    }

    public Basket txns(String txns) {
        this.txns = txns;
        return this;
    }

    public void setTxns(String txns) {
        this.txns = txns;
    }

    public String getVatAnalysis() {
        return vatAnalysis;
    }

    public Basket vatAnalysis(String vatAnalysis) {
        this.vatAnalysis = vatAnalysis;
        return this;
    }

    public void setVatAnalysis(String vatAnalysis) {
        this.vatAnalysis = vatAnalysis;
    }

    public Set<BasketEntry> getBasketEntries() {
        return basketEntries;
    }

    public Basket basketEntries(Set<BasketEntry> basketEntries) {
        this.basketEntries = basketEntries;
        return this;
    }

    public Basket addBasketEntry(BasketEntry basketEntry) {
        this.basketEntries.add(basketEntry);
        basketEntry.setBasket(this);
        return this;
    }

    public Basket removeBasketEntry(BasketEntry basketEntry) {
        this.basketEntries.remove(basketEntry);
        basketEntry.setBasket(null);
        return this;
    }

    public void setBasketEntries(Set<BasketEntry> basketEntries) {
        this.basketEntries = basketEntries;
    }

    public Set<VatAnalysis> getVatAnalyses() {
        return vatAnalyses;
    }

    public Basket vatAnalyses(Set<VatAnalysis> vatAnalyses) {
        this.vatAnalyses = vatAnalyses;
        return this;
    }

    public Basket addVatAnalysis(VatAnalysis vatAnalysis) {
        this.vatAnalyses.add(vatAnalysis);
        vatAnalysis.setBasket(this);
        return this;
    }

    public Basket removeVatAnalysis(VatAnalysis vatAnalysis) {
        this.vatAnalyses.remove(vatAnalysis);
        vatAnalysis.setBasket(null);
        return this;
    }

    public void setVatAnalyses(Set<VatAnalysis> vatAnalyses) {
        this.vatAnalyses = vatAnalyses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Basket)) {
            return false;
        }
        return id != null && id.equals(((Basket) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Basket{" +
            "id=" + getId() +
            ", basketId='" + getBasketId() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", txns='" + getTxns() + "'" +
            ", vatAnalysis='" + getVatAnalysis() + "'" +
            "}";
    }
}
