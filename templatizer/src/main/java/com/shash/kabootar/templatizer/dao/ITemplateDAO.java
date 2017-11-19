package com.shash.kabootar.templatizer.dao;

import com.shash.kabootar.templatizer.domain.Template;

import java.util.Set;

/**
 * @author shashankgautam
 */
public interface ITemplateDAO {

    /**
     * Save template.
     *
     * @param template ::
     * @return created template
     */
    Template save(final Template template);

    /**
     * Get Template.
     *
     * @param id ::
     * @return Template
     */
    Template get(final Long id);

    /**
     * Soft delete Template.
     *
     * @param Template ::
     */
    void delete(final Template Template);

    /**
     * Revive the Template.
     *
     * @param Template ::
     */
    void revive(final Template Template);

    /**
     * Search Template.
     *
     * @param name   to search
     * @param tenant to search
     * @return set of Template
     */
    Set<Template> search(final String name, final String tenant);

    /**
     * modify Template.
     *
     * @param Template ::
     */
    void modify(final Template Template);
}