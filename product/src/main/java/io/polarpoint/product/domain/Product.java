package io.polarpoint.product.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entities for product Catalogue microservice
 */
@ApiModel(description = "Entities for product Catalogue microservice")
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "additional_receipts", nullable = false)
    private String additionalReceipts;

    @NotNull
    @Column(name = "client", nullable = false)
    private Long client;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "type")
    private String type;

    @Column(name = "vat_code")
    private String vatCode;

    @OneToMany(mappedBy = "token")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Token> tokens = new HashSet<>();

    @OneToMany(mappedBy = "vatRate")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<VatRate> vatRates = new HashSet<>();

    @OneToMany(mappedBy = "page")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Page> pages = new HashSet<>();

    @OneToMany(mappedBy = "rulez")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rulez> rulezs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdditionalReceipts() {
        return additionalReceipts;
    }

    public Product additionalReceipts(String additionalReceipts) {
        this.additionalReceipts = additionalReceipts;
        return this;
    }

    public void setAdditionalReceipts(String additionalReceipts) {
        this.additionalReceipts = additionalReceipts;
    }

    public Long getClient() {
        return client;
    }

    public Product client(Long client) {
        this.client = client;
        return this;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public Product paymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getType() {
        return type;
    }

    public Product type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVatCode() {
        return vatCode;
    }

    public Product vatCode(String vatCode) {
        this.vatCode = vatCode;
        return this;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public Product tokens(Set<Token> tokens) {
        this.tokens = tokens;
        return this;
    }

    public Product addToken(Token token) {
        this.tokens.add(token);
        token.setToken(this);
        return this;
    }

    public Product removeToken(Token token) {
        this.tokens.remove(token);
        token.setToken(null);
        return this;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public Set<VatRate> getVatRates() {
        return vatRates;
    }

    public Product vatRates(Set<VatRate> vatRates) {
        this.vatRates = vatRates;
        return this;
    }

    public Product addVatRate(VatRate vatRate) {
        this.vatRates.add(vatRate);
        vatRate.setVatRate(this);
        return this;
    }

    public Product removeVatRate(VatRate vatRate) {
        this.vatRates.remove(vatRate);
        vatRate.setVatRate(null);
        return this;
    }

    public void setVatRates(Set<VatRate> vatRates) {
        this.vatRates = vatRates;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public Product pages(Set<Page> pages) {
        this.pages = pages;
        return this;
    }

    public Product addPage(Page page) {
        this.pages.add(page);
        page.setPage(this);
        return this;
    }

    public Product removePage(Page page) {
        this.pages.remove(page);
        page.setPage(null);
        return this;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    public Set<Rulez> getRulezs() {
        return rulezs;
    }

    public Product rulezs(Set<Rulez> rulezs) {
        this.rulezs = rulezs;
        return this;
    }

    public Product addRulez(Rulez rulez) {
        this.rulezs.add(rulez);
        rulez.setRulez(this);
        return this;
    }

    public Product removeRulez(Rulez rulez) {
        this.rulezs.remove(rulez);
        rulez.setRulez(null);
        return this;
    }

    public void setRulezs(Set<Rulez> rulezs) {
        this.rulezs = rulezs;
    }

    public Category getCategory() {
        return category;
    }

    public Product category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", additionalReceipts='" + getAdditionalReceipts() + "'" +
            ", client=" + getClient() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", type='" + getType() + "'" +
            ", vatCode='" + getVatCode() + "'" +
            "}";
    }
}
