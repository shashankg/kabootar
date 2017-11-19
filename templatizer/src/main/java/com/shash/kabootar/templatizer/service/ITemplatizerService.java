package com.shash.kabootar.templatizer.service;

import com.shash.kabootar.templatizer.domain.Template;

/**
 * @author shashankgautam
 */
public interface ITemplatizerService {
    Template create(final Template template);

    Template getById(long id);
}
