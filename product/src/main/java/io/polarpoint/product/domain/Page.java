package io.polarpoint.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Page.
 */
@Entity
@Table(name = "page")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private Long number;

    @Column(name = "predicates")
    private String predicates;

    @Column(name = "protection_level")
    private String protectionLevel;

    @Column(name = "quote")
    private String quote;

    @Column(name = "title")
    private String title;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pages")
    private Product page;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("pages")
    private Field order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public Page number(Long number) {
        this.number = number;
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getPredicates() {
        return predicates;
    }

    public Page predicates(String predicates) {
        this.predicates = predicates;
        return this;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;
    }

    public String getProtectionLevel() {
        return protectionLevel;
    }

    public Page protectionLevel(String protectionLevel) {
        this.protectionLevel = protectionLevel;
        return this;
    }

    public void setProtectionLevel(String protectionLevel) {
        this.protectionLevel = protectionLevel;
    }

    public String getQuote() {
        return quote;
    }

    public Page quote(String quote) {
        this.quote = quote;
        return this;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getTitle() {
        return title;
    }

    public Page title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product getPage() {
        return page;
    }

    public Page page(Product product) {
        this.page = product;
        return this;
    }

    public void setPage(Product product) {
        this.page = product;
    }

    public Field getOrder() {
        return order;
    }

    public Page order(Field field) {
        this.order = field;
        return this;
    }

    public void setOrder(Field field) {
        this.order = field;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Page)) {
            return false;
        }
        return id != null && id.equals(((Page) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Page{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", predicates='" + getPredicates() + "'" +
            ", protectionLevel='" + getProtectionLevel() + "'" +
            ", quote='" + getQuote() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
