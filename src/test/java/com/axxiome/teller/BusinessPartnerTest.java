package com.axxiome.teller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.TransactionMode;
import org.jboss.arquillian.persistence.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by radoslaw on 20.12.17.
 */
@RunWith(Arquillian.class)
public class BusinessPartnerTest {
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addPackage(BusinessPartner.class.getPackage())
            .addAsManifestResource("test-persistence.xml", "persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        return jar;
    }

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    UserTransaction utx;

    @Test @InSequence(1)
    @Transactional(TransactionMode.ROLLBACK)
    public void businessPartner_Has_One_Account_Simply_Select() throws Exception {
        // given
        Account account = buildAccount();
        Bank bank = buildBank();
        BusinessPartner businessPartner = buildBusinessPartner(account, bank);
        save2db(account, businessPartner, bank);

        // when
        @SuppressWarnings("unchecked")
        List<BusinessPartner> businessPartners = entityManager
            .createQuery(selectAllInJPQL(BusinessPartner.class))
            .getResultList();

        // then
        assertThat(businessPartners).containsOnly(businessPartner);
        assertThat(businessPartner.getAccounts().size()).isEqualTo(1);
    }

    @Test @InSequence(1)
    @Transactional(TransactionMode.ROLLBACK)
    public void businessPartner_Has_One_Account_With_ManyToOne_Relation() throws Exception {
        // given
        Account account = buildAccount();
        Bank bank = buildBank();
        BusinessPartner businessPartner = buildBusinessPartner(account, bank);
        save2db(account, businessPartner, bank);

        // when
        final String MANYTOONE =
            "select bp from BusinessPartner bp where bp.bank.name like 'Corporation Axxiome bank'";
        @SuppressWarnings("unchecked")
        List<BusinessPartner> businessPartners = entityManager
            .createQuery(MANYTOONE)
            .getResultList();

        // then
        assertThat(businessPartners).containsOnly(businessPartner);
        assertThat(businessPartner.getAccounts().size()).isEqualTo(1);
    }

    @Test @InSequence(1)
    @Transactional(TransactionMode.ROLLBACK)
    public void businessPartner_Has_One_Account_With_OneToMany_Relation() throws Exception {
        // given
        Account account = buildAccount();
        Bank bank = buildBank();
        BusinessPartner businessPartner = buildBusinessPartner(account, bank);
        save2db(account, businessPartner, bank);

        // when
        final String ONETOMANY =
            "select bp from BusinessPartner bp left join bp.accounts as account where account.currency like '%PLN%'";
        @SuppressWarnings("unchecked")
        List<BusinessPartner> businessPartners = entityManager
            .createQuery(ONETOMANY)
            .getResultList();

        // then
        assertThat(businessPartners).containsOnly(businessPartner);
        assertThat(businessPartner.getAccounts().size()).isEqualTo(1);
    }

    private Bank buildBank() {
        Bank bank = new Bank();
        bank.setName("Corporation Axxiome bank");
        return bank;
    }

    private Account buildAccount() {
        Account account = new Account();
        account.setCurrency("PLN");
        account.setNumber(1L);
        return account;
    }
    private BusinessPartner buildBusinessPartner(Account account, Bank bank) {
        BusinessPartner businessPartner = new BusinessPartner();
        businessPartner.setBank(bank);
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        businessPartner.setAccounts(accounts);
        return businessPartner;
    }
    private void save2db(Account account, BusinessPartner businessPartner, Bank bank) {
        entityManager.persist(bank);
        entityManager.persist(account);
        entityManager.persist(businessPartner);
        entityManager.flush();
        entityManager.clear();
    }
    private String selectAllInJPQL(Class<?> clazz) {
        return "SELECT entity FROM " + clazz.getSimpleName() + " entity";
    }
}

