package com.shash.kabootar.commons.dropwizard;

import com.shash.kabootar.commons.hibernate.BaseDomain;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 * @author shashankgautam
 */
public class BaseDAO<E extends BaseDomain> extends AbstractDAO<E> {

    public BaseDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected E persist(final E entity) throws HibernateException {
        entity.prePersist();
        return super.persist(entity);
    }

    protected E update(final E entity) throws HibernateException {
        entity.preUpdate();
        return super.persist(entity);
    }
}
