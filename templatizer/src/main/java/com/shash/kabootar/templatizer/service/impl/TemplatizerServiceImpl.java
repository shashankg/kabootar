package com.shash.kabootar.templatizer.service.impl;

import com.github.mustachejava.MustacheFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.shash.kabootar.commons.exception.ResourceNotFoundException;
import com.shash.kabootar.commons.exception.UnProcessableException;
import com.shash.kabootar.templatizer.dao.ITemplateDAO;
import com.shash.kabootar.templatizer.domain.Template;
import com.shash.kabootar.templatizer.service.ITemplatizerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        final Template existing = dao.get(template.getTenant(), template.getName());
        if (existing != null) {
            throw new RuntimeException(String.format("Template already exist with name %s under tenant %s", template.getName(), template.getTenant()));
        }
        validate(template);
        return dao.save(template);
    }

    private void validate(final Template template) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(template.getName()), "template name can't be null or empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(template.getTenant()), "template tenant can't be null or empty");
        InputStream stream = null;
        InputStreamReader reader = null;
        try {
            stream = new ByteArrayInputStream(template.getTemplateString().getBytes());
            reader = new InputStreamReader(stream);
            factory.compile(reader, template.getName());
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (final IOException e) {
                    log.error("unable to close stream", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    log.error("unable to close stream", e);
                }
            }
        }
    }

    @Override
    public Template get(final String tenant, final String name) {
        final Template template = dao.get(tenant, name);
        if (template == null || template.isDeleted()) {
            throw new ResourceNotFoundException(String.format("Not found: Template with name %s under tenant %s", name, tenant));
        }
        return template;
    }

    @Override
    public void publish(final String tenant, final String name) {
        final Template template = get(tenant, name);
        template.setStatus(Template.Status.PUBLISHED);
        dao.update(template);
    }

    @Override
    public void unpublish(final String tenant, final String name) {
        final Template template = get(tenant, name);
        if (!template.getStatus().equals(Template.Status.PUBLISHED)) {
            throw new UnProcessableException("Template is not published yet!");
        }
        template.setStatus(Template.Status.UNPUBLISHED);
        dao.update(template);
    }

    @Override
    public void update(final String tenant, final String name, final Template toUpdate) {
        final Template template = get(tenant, name);
        template.setTemplateString(toUpdate.getTemplateString());
        template.setVersion(template.getVersion() + 1);
        dao.update(template);
    }

    @Override
    public void delete(final String tenant, final String name) {
        final Template template = get(tenant, name);
        dao.delete(template);
    }
}
