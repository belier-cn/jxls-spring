package cn.belier.jxls.view;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * Jxls视图解析器
 *
 * @author belier
 * @date 2018/11/30
 */
public class JxlsViewResolver extends AbstractTemplateViewResolver {

    public static final String EXCEL_XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public JxlsViewResolver() {
        setViewClass(requiredViewClass());
        setContentType(EXCEL_XLSX_CONTENT_TYPE);
    }

    public JxlsViewResolver(String prefix, String suffix) {
        this();
        setPrefix(prefix);
        setSuffix(suffix);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return JxlsView.class;
    }


}
