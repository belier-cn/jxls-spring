package cn.belier.jxls.filename;

/**
 * 文件名成生成器
 *
 * @author belier
 * @date 2018/12/4
 */
@FunctionalInterface
public interface FilenameGenerate {

    /**
     * 生成默认的文件名称，不包含后缀
     *
     * @return 文件名称
     */
    String generate();

}
