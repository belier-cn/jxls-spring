package cn.belier.jxls.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author belier
 * @date 2018/12/5
 */
@Configuration
public class JxlsWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private JxlsProperties jxlsProperties;

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        String contentType = jxlsProperties.getContentType().toString();

        // 添加内容协商媒体类型
        configurer.mediaType(jxlsProperties.getSuffix().replaceFirst(".", "")
                , MediaType.parseMediaType(contentType));
    }

}
