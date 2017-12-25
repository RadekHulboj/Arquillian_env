//https://www.mkyong.com/hibernate/hibernate-one-to-many-relationship-example-annotation/

package com.axxiome.teller;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * Created by radoslaw on 22.12.17.
 */
@Entity
public class Account implements Serializable {
    private Long id;
    private String currency;
    private Long number;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account accounter = (Account) o;
        return Objects.equal(id, accounter.id) &&
                Objects.equal(currency, accounter.currency) &&
                Objects.equal(number, accounter.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, currency, number);
    }
}
