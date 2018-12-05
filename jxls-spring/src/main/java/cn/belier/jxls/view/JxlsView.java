package cn.belier.jxls.view;

import cn.belier.jxls.config.JxlsConfig;
import cn.belier.jxls.encoder.DownloadFilenameHandler;
import cn.belier.jxls.filename.FilenameGenerate;
import cn.belier.jxls.filename.JxlsFilenameUtils;
import lombok.Cleanup;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;
import org.jxls.util.TransformerFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Jxls 视图
 *
 * @author belier
 * @date 2018/11/30
 */

public class JxlsView extends AbstractTemplateView {

    private PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

    private DownloadFilenameHandler downloadFilenameHandler;

    private JxlsConfig config;

    private FilenameGenerate filenameGenerate;

    @Override
    protected void initServletContext(ServletContext servletContext) {

        this.config = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), JxlsConfig.class, true, false);

        this.downloadFilenameHandler = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), DownloadFilenameHandler.class, true, false);

        this.filenameGenerate = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), FilenameGenerate.class, true, false);

    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model
            , HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setStatus(HttpStatus.CREATED.value());

        // 创建Jxls的上下文
        Context context = new Context(model);

        String contentDisposition = this.downloadFilenameHandler.getContentDisposition(request, getFilename(context));

        response.setHeader("Content-Disposition", contentDisposition);

        // 获取对应的模板
        @Cleanup
        InputStream template = getTemplate();

        Transformer transformer = TransformerFactory.createTransformer(template, response.getOutputStream());

        ((JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator()).getJexlEngine()
                // 静默模式配置
                .setSilent(this.config.isSilent());

        // 输出渲染后的数据
        JxlsHelper.getInstance().processTemplate(context, transformer);

    }

    private String getFilename(Context context) {

        // 获取设置的文件名称
        String filename = JxlsFilenameUtils.getFilename(context);

        // 获取扩展名称
        String extension = this.getUrl().substring(this.getUrl().lastIndexOf("."));

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
     * @throws IOException IO异常
     */
    private InputStream getTemplate() throws IOException {

        for (String path : this.config.getTemplateLoaderPath()) {

            // 模板全路径
            String templatePath = path + getUrl();

            // 加载模板
            InputStream inputStream = templatePath.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)
                    ? this.patternResolver.getResource(templatePath).getInputStream()
                    : new FileInputStream(templatePath);

            if (inputStream != null) {
                return inputStream;
            }
        }

        return null;
    }

}
