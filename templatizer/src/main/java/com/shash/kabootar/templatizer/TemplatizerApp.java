package com.shash.kabootar.templatizer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
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
    public static final String HIBERNATE_PKG = "com.shash.templatizer.domain";

    /*
    * Hibernate bundle
    */
    private final HibernateBundle<TemplatizerConfig> hibernate = new ScanningHibernateBundle<TemplatizerConfig>(HIBERNATE_PKG) {
        @Override
        public DataSourceFactory getDataSourceFactory(final TemplatizerConfig configuration) {
            return configuration.getDatabase();
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

        /*
         * Register managed, resources, exception mapper, filters
         */
        /*environment.lifecycle().manage(openTsdb);
        environment.jersey().register(componentResource);
        environment.jersey().register(RuntimeExceptionMapper.class);
        environment.jersey().register(UserFilter.class);*/
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

