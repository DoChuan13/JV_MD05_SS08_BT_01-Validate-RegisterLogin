package backend.config;

import backend.formatter.SampleFormatter;
import backend.services.sample.ISampleService;
import backend.services.sample.SampleServiceIMPL;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * <h3>Setup Annotations for Config Class</h3>
 * <p><b>Configuration</b>, <b>EnableWebMvc</b></p>
 * <p><b>EnableTransactionManagement</b>, <b>EnableSpringDataWebSupport</b></p>
 * <p><b>PropertySource</b> for Upload File, <b>ComponentScan</b> for Controller</p>
 * <p><b>EnableJpaRepositories</b> for Repositories</p>
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableSpringDataWebSupport
@PropertySource("classpath:file-upload.properties")
@ComponentScan("backend.controller")
@EnableJpaRepositories("backend.repository")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    /**
     * <h3>00. Application Context Configuration</h3>
     */
    private ApplicationContext applicationContext;
    @Value("${file-upload}")
    private String fileUpload;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new SampleFormatter(applicationContext.getBean(SampleServiceIMPL.class)));
    }

    /**
     * <h3>00. Import Files Configuration</h3>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + fileUpload);
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(52428800);
        return resolver;
    }

    /**
     * <h3>01. Thymeleaf Resolver Configuration</h3>
     * <p>Step 1: Create Spring Resource <b>Template Resolver</b> Configuration</p>
     * <p>Step 2: Create Spring <b>Template Engine</b> Configuration</p>
     * <p>Step 3: Create Thymeleaf <b>View Resolver</b> Configuration</p>
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    /**
     * <h3>02. JPA Configuration</h3>
     * <p>Step 1: Create <b>Data Source</b> with Database Configuration</p>
     * <p>Step 2: Addition More <b>Properties</b> for Database Configuration</p>
     * <p>Step 3: Create <b>Entity Manager Factory Bean</b> for Models and <b>JPA Hibernate</b> Configuration</p>
     * <p>Step 4: Create <b>Entity Manager Factory</b> Configuration</p>
     * <p>Step 5: Create <b>Transaction Manager</b> Configuration</p>
     */
    @Bean
    public DataSource dataSource() {
        String DATABASE = System.getenv("DATABASE") + "cms";
        String USER = System.getenv("USER");
        String PASSWORD = System.getenv("PASSWORD");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(DATABASE);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }

    @Bean
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("backend.model");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * <h3>03. Injection Services</h3>
     * <p>All Collection of Service Beans</p>
     */
    @Bean
    public ISampleService sampleService() {
        return new SampleServiceIMPL();
    }
}
