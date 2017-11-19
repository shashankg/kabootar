package com.shash.kabootar.templatizer.dao.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.shash.kabootar.commons.dropwizard.BaseDAO;
import com.shash.kabootar.templatizer.dao.ITemplateDAO;
import com.shash.kabootar.templatizer.domain.Template;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Set;

/**
 * @author shashankgautam
 */
public class TemplateDAO extends BaseDAO<Template> implements ITemplateDAO {

    private final SessionFactory sessionFactory;

    public TemplateDAO(final SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Template save(final Template template) {
        template.setDeleted(false);
        return super.persist(template);
    }

    @Override
    public Template get(final Long id) {
        return super.get(id);
    }

    @Override
    public void delete(final Template template) {
        template.setDeleted(true);
        super.update(template);
    }

    @Override
    public void revive(final Template template) {
        template.setDeleted(false);
        super.update(template);
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
    public void modify(final Template template) {
        super.update(template);
        template.setVersion(template.getVersion() + 1);
    }

}
