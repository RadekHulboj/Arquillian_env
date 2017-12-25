package com.axxiome.teller;

import com.google.common.base.Objects;
import com.sun.istack.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by radoslaw on 20.12.17.
 */
@Entity
public class BusinessPartner implements Serializable {

    private String name;
    private Long id;
    private Set<Account> accounts = new HashSet<>();
    private Bank bank;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    @Size(min = 3, max = 30)
    String getName() {
        return name;
    }

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    Set<Account> getAccounts() {
        return accounts;
    }

    void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessPartner that = (BusinessPartner) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id);
    }
}
