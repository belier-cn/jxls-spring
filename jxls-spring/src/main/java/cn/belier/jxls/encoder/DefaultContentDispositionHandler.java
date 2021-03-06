package cn.belier.jxls.encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 默认下载文件处理
 *
 * @author belier
 * @date 2018/12/4
 */
public class DefaultContentDispositionHandler implements ContentDispositionHandler {

    @Override
    public String getContentDisposition(HttpServletRequest request, String filename) throws UnsupportedEncodingException {

        String encodeFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.name())
                .replaceAll("\\+", "%20");

        return String.format("attachment; filename=\"%s\"; filename*=utf-8''%s", encodeFilename, encodeFilename);
    }

}
