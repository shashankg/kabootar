package com.shash.kabootar.templatizer.dao.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.shash.kabootar.commons.dropwizard.BaseDAO;
import com.shash.kabootar.templatizer.dao.ITemplateDAO;
import com.shash.kabootar.templatizer.domain.Template;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Set;

/**
 * @author shashankgautam
 */
public class TemplateDAO extends AbstractDAO<Template> implements ITemplateDAO {

    private final SessionFactory sessionFactory;

    /**
     * Constructor
     *
     * @param sessionFactory ::
     */
    public TemplateDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Template save(final Template template) {
        template.setDeleted(false);
        template.prePersist();
        return super.persist(template);
    }

    @Override
    public Template get(final String tenant, final String name) {
        return super.get(Template.createId(tenant, name));
    }

    @Override
    public void delete(final Template template) {
        template.setDeleted(true);
        template.preUpdate();
        super.persist(template);
    }

    @Override
    public void revive(final Template template) {
        template.setDeleted(false);
        template.preUpdate();
        super.persist(template);
    }

    @Override
    public Set<Template> search(final String name, final String tenant) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria criteria = session.createCriteria(Template.class);

        if (!Strings.isNullOrEmpty(name)) {
            criteria.add(Restrictions.eq("name", name));
        }
        if (!Strings.isNullOrEmpty(tenant)) {
            criteria.add(Restrictions.eq("tenant", tenant));
        }

        return Sets.newHashSet(super.list(criteria));
    }

    @Override
    public void update(final Template template) {
        template.preUpdate();
        super.persist(template);
        template.setVersion(template.getVersion() + 1);
    }

}
