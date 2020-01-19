package io.polarpoint.product.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Field.
 */
@Entity
@Table(name = "field")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "defaultz")
    private String defaultz;

    @Column(name = "editable")
    private Boolean editable;

    @Column(name = "label")
    private String label;

    @Column(name = "mandatory")
    private Boolean mandatory;

    @Column(name = "maxs")
    private String maxs;

    @Column(name = "mins")
    private String mins;

    @Column(name = "multiple")
    private Long multiple;

    @Column(name = "name")
    private String name;

    @Column(name = "patternz")
    private String patternz;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Page> pages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDefaultz() {
        return defaultz;
    }

    public Field defaultz(String defaultz) {
        this.defaultz = defaultz;
        return this;
    }

    public void setDefaultz(String defaultz) {
        this.defaultz = defaultz;
    }

    public Boolean isEditable() {
        return editable;
    }

    public Field editable(Boolean editable) {
        this.editable = editable;
        return this;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getLabel() {
        return label;
    }

    public Field label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public Field mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getMaxs() {
        return maxs;
    }

    public Field maxs(String maxs) {
        this.maxs = maxs;
        return this;
    }

    public void setMaxs(String maxs) {
        this.maxs = maxs;
    }

    public String getMins() {
        return mins;
    }

    public Field mins(String mins) {
        this.mins = mins;
        return this;
    }

    public void setMins(String mins) {
        this.mins = mins;
    }

    public Long getMultiple() {
        return multiple;
    }

    public Field multiple(Long multiple) {
        this.multiple = multiple;
        return this;
    }

    public void setMultiple(Long multiple) {
        this.multiple = multiple;
    }

    public String getName() {
        return name;
    }

    public Field name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatternz() {
        return patternz;
    }

    public Field patternz(String patternz) {
        this.patternz = patternz;
        return this;
    }

    public void setPatternz(String patternz) {
        this.patternz = patternz;
    }

    public String getType() {
        return type;
    }

    public Field type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public Field pages(Set<Page> pages) {
        this.pages = pages;
        return this;
    }

    public Field addPage(Page page) {
        this.pages.add(page);
        page.setOrder(this);
        return this;
    }

    public Field removePage(Page page) {
        this.pages.remove(page);
        page.setOrder(null);
        return this;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Field)) {
            return false;
        }
        return id != null && id.equals(((Field) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Field{" +
            "id=" + getId() +
            ", defaultz='" + getDefaultz() + "'" +
            ", editable='" + isEditable() + "'" +
            ", label='" + getLabel() + "'" +
            ", mandatory='" + isMandatory() + "'" +
            ", maxs='" + getMaxs() + "'" +
            ", mins='" + getMins() + "'" +
            ", multiple=" + getMultiple() +
            ", name='" + getName() + "'" +
            ", patternz='" + getPatternz() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
