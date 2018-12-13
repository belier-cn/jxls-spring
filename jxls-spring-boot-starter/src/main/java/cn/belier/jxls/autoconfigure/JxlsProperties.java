package cn.belier.jxls.autoconfigure;

import cn.belier.jxls.function.DateFunction;
import cn.belier.jxls.view.JxlsViewResolver;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.MimeType;

/**
 * jxls自动化配置参数
 *
 * @author belier
 * @date 2018/11/30
 */
@Getter
@Setter
@ConfigurationProperties("spring.jxls")
public class JxlsProperties extends AbstractTemplateViewResolverProperties {

    /**
     * 默认后缀
     */
    protected static final String DEFAULT_SUFFIX = ".xlsx";
    /**
     * 默认模板加载路径
     */
    private static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";
    /**
     * 默认前缀
     */
    private static final String DEFAULT_PREFIX = "";
    /**
     * 模板加载路径列表
     */
    private String[] templateLoaderPath = new String[]{DEFAULT_TEMPLATE_LOADER_PATH};

    /**
     * 使用快速公式处理
     */
    private boolean useFastFormulaProcessor = false;

    /**
     * 处理公式
     */
    private boolean processFormulas = true;

    /**
     * 隐藏模板sheet
     */
    private boolean hideTemplateSheet = false;

    /**
     * 删除模板sheet
     */
    private boolean deleteTemplateSheet = true;

    /**
     * 扩展方法配置
     */
    private FunctionConfig function = new FunctionConfig();

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

    /**
     * 扩展方法配置
     */
    @Data
    public static class FunctionConfig {

        private DateFunctionConfig date = new DateFunctionConfig();

    }

    /**
     * 日期扩展方法配置
     */
    @Data
    public static class DateFunctionConfig {

        private boolean enabled = true;

        private String name = DateFunction.DEFAULT_NAME;

        private String date = DateFunction.DEFAULT_DATE;

        private String datetime = DateFunction.DEFAULT_DATE_TIME;

        private String time = DateFunction.DEFAULT_TIME;

    }

}