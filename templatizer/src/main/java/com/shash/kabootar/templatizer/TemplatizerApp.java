package com.shash.kabootar.templatizer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import com.shash.kabootar.commons.dropwizard.RuntimeExceptionMapper;
import com.shash.kabootar.commons.exception.ResourceNotFoundException;
import com.shash.kabootar.commons.exception.UnProcessableException;
import com.shash.kabootar.commons.filter.UserFilter;
import com.shash.kabootar.templatizer.dao.ITemplateDAO;
import com.shash.kabootar.templatizer.dao.impl.TemplateDAO;
import com.shash.kabootar.templatizer.domain.Template;
import com.shash.kabootar.templatizer.resource.TemplatizerResource;
import com.shash.kabootar.templatizer.service.impl.TemplatizerServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shashankgautam
 */
@Slf4j
public class TemplatizerApp extends Application<TemplatizerConfig> {

    public static final String APP_NAME = "kabootar-templatizer";

    /*
    * Hibernate bundle
    */
    private final HibernateBundle<TemplatizerConfig> hibernate = new HibernateBundle<TemplatizerConfig>(Template.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(final TemplatizerConfig templatizerConfig) {
            return templatizerConfig.getDatabase();
        }
    };

    @Override
    public void initialize(final Bootstrap<TemplatizerConfig> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final TemplatizerConfig config, final Environment environment) throws Exception {
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final ITemplateDAO dao = new TemplateDAO(hibernate.getSessionFactory());
        final MustacheFactory factory = new DefaultMustacheFactory();

        environment.jersey().register(new TemplatizerResource(new TemplatizerServiceImpl(factory, dao)));
        environment.jersey().register(RuntimeExceptionMapper.class);
        environment.jersey().register(UserFilter.class);
    }

    /**
     * @param args to start java process
     * @throws Exception ::
     */
    public static void main(final String[] args) throws Exception {
        final TemplatizerApp app = new TemplatizerApp();
        log.info("Starting app - {}", APP_NAME);
        app.run(args);
    }
}

