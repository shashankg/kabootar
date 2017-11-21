package com.shash.kabootar.templatizer.service;

import com.shash.kabootar.templatizer.domain.Template;

/**
 * @author shashankgautam
 */
public interface ITemplatizerService {

    Template create(final Template template);

    void update(final String tenant, final String name, final Template toUpdate);

    void delete(final String tenant, final String name);

    Template get(final String tenant, final String name);

    void publish(final String tenant, final String name);

    void unpublish(final String tenant, final String name);
}
