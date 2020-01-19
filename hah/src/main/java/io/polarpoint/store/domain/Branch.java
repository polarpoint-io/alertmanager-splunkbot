package io.polarpoint.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Entities for Gateway
 */
@ApiModel(description = "Entities for Gateway")
@Entity
@Table(name = "branch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "branch_address", nullable = false)
    private String branchAddress;

    @NotNull
    @Column(name = "fad", nullable = false)
    private String fad;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("subscriptionGroups")
    private SubscriptionGroup subscriptionGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public Branch branchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
        return this;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getFad() {
        return fad;
    }

    public Branch fad(String fad) {
        this.fad = fad;
        return this;
    }

    public void setFad(String fad) {
        this.fad = fad;
    }

    public String getName() {
        return name;
    }

    public Branch name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubscriptionGroup getSubscriptionGroup() {
        return subscriptionGroup;
    }

    public Branch subscriptionGroup(SubscriptionGroup subscriptionGroup) {
        this.subscriptionGroup = subscriptionGroup;
        return this;
    }

    public void setSubscriptionGroup(SubscriptionGroup subscriptionGroup) {
        this.subscriptionGroup = subscriptionGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Branch)) {
            return false;
        }
        return id != null && id.equals(((Branch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Branch{" +
            "id=" + getId() +
            ", branchAddress='" + getBranchAddress() + "'" +
            ", fad='" + getFad() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
