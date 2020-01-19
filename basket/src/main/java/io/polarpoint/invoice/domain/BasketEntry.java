package io.polarpoint.invoice.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A BasketEntry.
 */
@Entity
@Table(name = "basket_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BasketEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "basket_id")
    private String basketId;

    @Column(name = "can_edit")
    private Boolean canEdit;

    @Column(name = "prodict_id")
    private Long prodictId;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "refundable")
    private Boolean refundable;

    @Column(name = "removeable")
    private Boolean removeable;

    @NotNull
    @Column(name = "total_price", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "unit_price", precision = 21, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "vat_code")
    private String vatCode;

    @Column(name = "vat_element")
    private String vatElement;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("basketEntries")
    private Basket basket;

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

    public BasketEntry basketId(String basketId) {
        this.basketId = basketId;
        return this;
    }

    public void setBasketId(String basketId) {
        this.basketId = basketId;
    }

    public Boolean isCanEdit() {
        return canEdit;
    }

    public BasketEntry canEdit(Boolean canEdit) {
        this.canEdit = canEdit;
        return this;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Long getProdictId() {
        return prodictId;
    }

    public BasketEntry prodictId(Long prodictId) {
        this.prodictId = prodictId;
        return this;
    }

    public void setProdictId(Long prodictId) {
        this.prodictId = prodictId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BasketEntry quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Boolean isRefundable() {
        return refundable;
    }

    public BasketEntry refundable(Boolean refundable) {
        this.refundable = refundable;
        return this;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public Boolean isRemoveable() {
        return removeable;
    }

    public BasketEntry removeable(Boolean removeable) {
        this.removeable = removeable;
        return this;
    }

    public void setRemoveable(Boolean removeable) {
        this.removeable = removeable;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BasketEntry totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BasketEntry transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BasketEntry unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getVatCode() {
        return vatCode;
    }

    public BasketEntry vatCode(String vatCode) {
        this.vatCode = vatCode;
        return this;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getVatElement() {
        return vatElement;
    }

    public BasketEntry vatElement(String vatElement) {
        this.vatElement = vatElement;
        return this;
    }

    public void setVatElement(String vatElement) {
        this.vatElement = vatElement;
    }

    public Basket getBasket() {
        return basket;
    }

    public BasketEntry basket(Basket basket) {
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
        if (!(o instanceof BasketEntry)) {
            return false;
        }
        return id != null && id.equals(((BasketEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BasketEntry{" +
            "id=" + getId() +
            ", basketId='" + getBasketId() + "'" +
            ", canEdit='" + isCanEdit() + "'" +
            ", prodictId=" + getProdictId() +
            ", quantity=" + getQuantity() +
            ", refundable='" + isRefundable() + "'" +
            ", removeable='" + isRemoveable() + "'" +
            ", totalPrice=" + getTotalPrice() +
            ", transactionId='" + getTransactionId() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", vatCode='" + getVatCode() + "'" +
            ", vatElement='" + getVatElement() + "'" +
            "}";
    }
}
