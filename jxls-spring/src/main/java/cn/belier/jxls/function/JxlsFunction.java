package cn.belier.jxls.function;

/**
 * jxls扩展方法封装
 *
 * @author belier
 * @date 2018/12/6
 */
public interface JxlsFunction {

    /**
     * 使用名称，最好简短
     *
     * @return 名称
     */
    String getName();

    /**
     * 扩展方法对象
     *
     * @return 扩展方法
     */
    Object getFunction();


}
