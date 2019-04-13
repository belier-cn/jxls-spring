package cn.belier.jxls.view;

import cn.belier.jxls.cahce.TemplateCache;
import cn.belier.jxls.config.JxlsConfig;
import cn.belier.jxls.encoder.ContentDispositionHandler;
import cn.belier.jxls.filename.FilenameGenerate;
import cn.belier.jxls.filename.JxlsFilenameUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

/**
 * Jxls 视图
 *
 * @author belier
 * @date 2018/11/30
 */

public class JxlsView extends AbstractTemplateView {

    private ContentDispositionHandler contentDispositionHandler;

    private JxlsConfig config;

    private FilenameGenerate filenameGenerate;

    private JxlsHelper jxlsHelper;

    private TemplateCache templateCache;

    @Override
    protected void initServletContext(ServletContext servletContext) {

        this.config = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), JxlsConfig.class, true, false);

        this.jxlsHelper = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), JxlsHelper.class, true, false);

        this.contentDispositionHandler = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), ContentDispositionHandler.class, true, false);

        this.filenameGenerate = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), FilenameGenerate.class, true, false);

        this.templateCache = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), TemplateCache.class, true, false);

    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setStatus(HttpStatus.CREATED.value());

        // 创建Jxls的上下文
        Context context = new Context(model);

        String contentDisposition = this.contentDispositionHandler.getContentDisposition(request, getFilename(context));

        response.setHeader("Content-Disposition", contentDisposition);

        // 获取对应的模板
        InputStream template = getTemplate();

        if (template == null) {
            return;
        }

        Transformer transformer = TransformerFactory.createTransformer(template, response.getOutputStream());

        JexlExpressionEvaluator expressionEvaluator = (JexlExpressionEvaluator) transformer
                .getTransformationConfig().getExpressionEvaluator();

        JexlEngine customJexlEngine = new JexlBuilder()
                // 自定义方法
                .namespaces(this.config.getFunctions())
                // 静默模式配置
                .silent(this.config.isSilent())
                .create();

        expressionEvaluator.setJexlEngine(customJexlEngine);


        // 输出渲染后的数据
        this.jxlsHelper.processTemplate(context, transformer);

    }

    /**
     * 获取文件名称
     *
     * @param context jxls上下文
     * @return 文件名称
     */
    private String getFilename(Context context) {

        // 获取设置的文件名称
        String filename = JxlsFilenameUtils.getFilename(context);

        // 获取扩展名称
        String extension = this.getUrl().substring(Objects.requireNonNull(this.getUrl()).lastIndexOf("."));

        // 没有设置就使用默认的
        if (StringUtils.isEmpty(filename)) {
            filename = this.filenameGenerate.generate();
        }

        return filename + extension;
    }


    /**
     * 获取模板
     *
     * @return {@link InputStream}
     */
    private InputStream getTemplate() {

        for (String path : this.config.getTemplateLoaderPath()) {

            byte[] template = templateCache.getTemplate(path + getUrl());

            if (template != null) {
                return new ByteArrayInputStream(template);
            }

        }

        return null;
    }

}
