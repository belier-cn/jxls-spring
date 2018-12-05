package cn.belier.jxls.encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 下载文件名编码
 *
 * @author belier
 * @date 2018/12/4
 */
@FunctionalInterface
public interface DownloadFilenameHandler {


    /**
     * 编码文件，防止乱码
     *
     * @param request  请求
     * @param filename 文件名
     * @return Content-Disposition 的值
     * @throws UnsupportedEncodingException 编码异常
     */
    String getContentDisposition(HttpServletRequest request, String filename) throws UnsupportedEncodingException;

}
