package io.polarpoint.store.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Token.
 */
@Entity
@Table(name = "token")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "client_account_name", nullable = false)
    private String clientAccountName;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "iin")
    private Long iin;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "svc_start")
    private Long svcStart;

    @Column(name = "type")
    private String type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("tokens")
    private Product token;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientAccountName() {
        return clientAccountName;
    }

    public Token clientAccountName(String clientAccountName) {
        this.clientAccountName = clientAccountName;
        return this;
    }

    public void setClientAccountName(String clientAccountName) {
        this.clientAccountName = clientAccountName;
    }

    public Long getClientId() {
        return clientId;
    }

    public Token clientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getIin() {
        return iin;
    }

    public Token iin(Long iin) {
        this.iin = iin;
        return this;
    }

    public void setIin(Long iin) {
        this.iin = iin;
    }

    public Long getItemId() {
        return itemId;
    }

    public Token itemId(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public Token name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSvcStart() {
        return svcStart;
    }

    public Token svcStart(Long svcStart) {
        this.svcStart = svcStart;
        return this;
    }

    public void setSvcStart(Long svcStart) {
        this.svcStart = svcStart;
    }

    public String getType() {
        return type;
    }

    public Token type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getToken() {
        return token;
    }

    public Token token(Product product) {
        this.token = product;
        return this;
    }

    public void setToken(Product product) {
        this.token = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        return id != null && id.equals(((Token) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Token{" +
            "id=" + getId() +
            ", clientAccountName='" + getClientAccountName() + "'" +
            ", clientId=" + getClientId() +
            ", iin=" + getIin() +
            ", itemId=" + getItemId() +
            ", name='" + getName() + "'" +
            ", svcStart=" + getSvcStart() +
            ", type='" + getType() + "'" +
            "}";
    }
}
