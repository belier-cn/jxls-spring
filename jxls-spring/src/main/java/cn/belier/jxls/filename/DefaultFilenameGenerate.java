package cn.belier.jxls.filename;

/**
 * 默认的文件名称生成器
 *
 * @author belier
 * @date 2018/12/4
 */
public class DefaultFilenameGenerate implements FilenameGenerate {

    @Override
    public String generate() {
        return String.valueOf(System.currentTimeMillis());
    }
}
