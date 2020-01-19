package io.polarpoint.store.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubscriptionGroup.
 */
@Entity
@Table(name = "subscription_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubscriptionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "subscriptionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Category> subscriptionGroups = new HashSet<>();

    @OneToMany(mappedBy = "subscriptionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Branch> subscriptionGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SubscriptionGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getSubscriptionGroups() {
        return subscriptionGroups;
    }

    public SubscriptionGroup subscriptionGroups(Set<Category> categories) {
        this.subscriptionGroups = categories;
        return this;
    }

    public SubscriptionGroup addSubscriptionGroup(Category category) {
        this.subscriptionGroups.add(category);
        category.setSubscriptionGroup(this);
        return this;
    }

    public SubscriptionGroup removeSubscriptionGroup(Category category) {
        this.subscriptionGroups.remove(category);
        category.setSubscriptionGroup(null);
        return this;
    }

    public void setSubscriptionGroups(Set<Category> categories) {
        this.subscriptionGroups = categories;
    }

    public Set<Branch> getSubscriptionGroups() {
        return subscriptionGroups;
    }

    public SubscriptionGroup subscriptionGroups(Set<Branch> branches) {
        this.subscriptionGroups = branches;
        return this;
    }

    public SubscriptionGroup addSubscriptionGroup(Branch branch) {
        this.subscriptionGroups.add(branch);
        branch.setSubscriptionGroup(this);
        return this;
    }

    public SubscriptionGroup removeSubscriptionGroup(Branch branch) {
        this.subscriptionGroups.remove(branch);
        branch.setSubscriptionGroup(null);
        return this;
    }

    public void setSubscriptionGroups(Set<Branch> branches) {
        this.subscriptionGroups = branches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionGroup)) {
            return false;
        }
        return id != null && id.equals(((SubscriptionGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubscriptionGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
