package cn.belier.jxls.autoconfigure;

import cn.belier.jxls.config.JxlsConfig;
import cn.belier.jxls.encoder.DefaultDownloadFilenameHandler;
import cn.belier.jxls.encoder.DownloadFilenameHandler;
import cn.belier.jxls.filename.DefaultFilenameGenerate;
import cn.belier.jxls.filename.FilenameGenerate;
import cn.belier.jxls.view.JxlsViewResolver;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.servlet.Servlet;

/**
 * @author belier
 * @date 2018/11/30
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, JxlsHelper.class})
@ConditionalOnProperty(name = "spring.jxls.enabled", matchIfMissing = true)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(JxlsProperties.class)
@Import(JxlsWebMvcConfigurer.class)
public class JxlsAutoConfiguration {

    @Autowired
    private JxlsProperties jxlsProperties;

    @Bean
    @ConditionalOnMissingBean(name = "jxlsViewResolver")
    public JxlsViewResolver jxlsViewResolver() {
        JxlsViewResolver jxlsViewResolver = new JxlsViewResolver();
        jxlsProperties.applyToMvcViewResolver(jxlsViewResolver);
        jxlsViewResolver.setContentType(jxlsProperties.getContentType().toString());
        return jxlsViewResolver;
    }

    @Bean
    public JxlsConfig jxlsConfig() {
        JxlsConfig config = new JxlsConfig();
        config.setSilent(jxlsProperties.isSilent());
        config.setTemplateLoaderPath(jxlsProperties.getTemplateLoaderPath());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean(DownloadFilenameHandler.class)
    public DownloadFilenameHandler downloadFilenameEncoder() {

        return new DefaultDownloadFilenameHandler();
    }

    @Bean
    @ConditionalOnMissingBean(FilenameGenerate.class)
    public FilenameGenerate filenameGenerate() {

        return new DefaultFilenameGenerate();
    }
}
