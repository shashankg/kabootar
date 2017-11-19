package com.shash.kabootar.templatizer.service.impl;

import com.github.mustachejava.MustacheFactory;
import com.shash.kabootar.commons.exception.ResourceNotFoundException;
import com.shash.kabootar.templatizer.dao.ITemplateDAO;
import com.shash.kabootar.templatizer.domain.Template;
import com.shash.kabootar.templatizer.service.ITemplatizerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shashankgautam
 */
@Slf4j
@AllArgsConstructor
public class TemplatizerServiceImpl implements ITemplatizerService {

    private MustacheFactory factory;
    private ITemplateDAO dao;


    @Override
    public Template create(final Template template) {
        return dao.save(template);
    }

    @Override
    public Template getById(final long id) {
        final Template template = dao.get(id);
        if (template == null) {
            throw new ResourceNotFoundException("Not found: Template with id " + id);
        }
        return template;
    }
}
