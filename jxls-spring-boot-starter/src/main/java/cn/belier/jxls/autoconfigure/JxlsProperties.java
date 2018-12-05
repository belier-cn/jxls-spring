package cn.belier.jxls.autoconfigure;

import cn.belier.jxls.view.JxlsViewResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MimeType;

/**
 * @author belier
 * @date 2018/11/30
 */
@Getter
@Setter
@ConfigurationProperties("spring.jxls")
public class JxlsProperties extends AbstractTemplateViewResolverProperties {

    /**
     * 默认模板加载路径
     */
    private static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";

    /**
     * 默认前缀
     */
    private static final String DEFAULT_PREFIX = "";

    /**
     * 默认后缀
     */
    protected static final String DEFAULT_SUFFIX = ".xlsx";

    /**
     * 模板加载路径列表
     */
    private String[] templateLoaderPath = new String[]{DEFAULT_TEMPLATE_LOADER_PATH};

    /**
     * 静默模式，默认开启
     */
    private boolean silent = true;

    public JxlsProperties() {
        // 设置默认的前缀，后缀
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
        // 设置默认的 contentType
        setContentType(MimeType.valueOf(JxlsViewResolver.EXCEL_XLSX_CONTENT_TYPE));
    }

}