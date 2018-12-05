package cn.belier.jxls.filename;

import org.jxls.common.Context;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * @author belier
 * @date 2018/12/4
 */
public class JxlsFilenameUtils {

    /**
     * 设置文件名时的key，设置到model中，不包含后缀
     */
    private static final String FILENAME_KEY = "JXLS_FILENAME_KEY";

    private JxlsFilenameUtils() {
    }

    /**
     * {@link cn.belier.jxls.view.JxlsViewResolver} 必须开启 exposeRequestAttributes 才有效果
     *
     * @param request  请求
     * @param filename 文件名称
     */
    public static void setFilename(HttpServletRequest request, String filename) {
        request.setAttribute(FILENAME_KEY, filename);
    }

    /**
     * 将文件名设置在 {@link Model}
     *
     * @param model    {@link Model}
     * @param filename 文件名称
     */
    public static void setFilename(Model model, String filename) {
        model.addAttribute(FILENAME_KEY, filename);
    }

    /**
     * 获取文件名称
     *
     * @param context {@link Context} jxls上下文
     * @return 文件名称
     */
    public static String getFilename(Context context) {
        return (String) context.getVar(FILENAME_KEY);
    }
}
