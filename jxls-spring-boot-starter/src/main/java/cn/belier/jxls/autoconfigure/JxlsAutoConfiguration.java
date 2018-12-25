package cn.belier.jxls.autoconfigure;

import cn.belier.jxls.config.JxlsConfig;
import cn.belier.jxls.encoder.ContentDispositionHandler;
import cn.belier.jxls.encoder.DefaultContentDispositionHandler;
import cn.belier.jxls.filename.DefaultFilenameGenerate;
import cn.belier.jxls.filename.FilenameGenerate;
import cn.belier.jxls.function.DateFunction;
import cn.belier.jxls.function.JxlsFunction;
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
import java.util.List;

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

    @Autowired(required = false)
    private List<JxlsFunction> jxlsFunctions;

    @Bean
    @ConditionalOnMissingBean(name = "jxlsViewResolver")
    public JxlsViewResolver jxlsViewResolver() {
        JxlsViewResolver jxlsViewResolver = new JxlsViewResolver();
        this.jxlsProperties.applyToMvcViewResolver(jxlsViewResolver);
        jxlsViewResolver.setContentType(this.jxlsProperties.getContentType().toString());
        return jxlsViewResolver;
    }


    @Bean
    public JxlsConfig jxlsConfig() {

        JxlsConfig config = new JxlsConfig();

        config.setSilent(this.jxlsProperties.isSilent())
                .addFunctions(this.jxlsFunctions)
                .setTemplateLoaderPath(this.jxlsProperties.getTemplateLoaderPath())
                .setCache(this.jxlsProperties.isCache());

        return config;
    }


    @Bean
    public JxlsHelper jxlsHelper() {

        return JxlsHelper.getInstance()
                .setDeleteTemplateSheet(this.jxlsProperties.isDeleteTemplateSheet())
                .setHideTemplateSheet(this.jxlsProperties.isHideTemplateSheet())
                .setProcessFormulas(this.jxlsProperties.isProcessFormulas())
                .setUseFastFormulaProcessor(this.jxlsProperties.isUseFastFormulaProcessor());

    }

    /**
     * ContentDisposition处理
     */
    @Bean
    @ConditionalOnMissingBean(ContentDispositionHandler.class)
    public ContentDispositionHandler contentDispositionHandler() {

        return new DefaultContentDispositionHandler();
    }


    /**
     * 默认文件名生成器
     */
    @Bean
    @ConditionalOnMissingBean(FilenameGenerate.class)
    public FilenameGenerate filenameGenerate() {

        return new DefaultFilenameGenerate();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.jxls.function.date.enabled", matchIfMissing = true)
    public DateFunction dateFunction() {

        JxlsProperties.DateFunctionConfig dateConfig = this.jxlsProperties.getFunction().getDate();

        DateFunction dateFunction = DateFunction.of()
                .setDate(dateConfig.getDate())
                .setDatetime(dateConfig.getDatetime())
                .setTime(dateConfig.getTime());

        dateConfig.setName(dateConfig.getName());

        return dateFunction;
    }
}
