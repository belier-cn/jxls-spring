package cn.belier.jxls.function;

import lombok.Builder;
import lombok.Data;

/**
 * jxls扩展方法封装
 *
 * @author belier
 * @date 2018/12/6
 */
@Data
@Builder
public class JxlsFunction {

    /**
     * 使用名称，最好简短
     */
    private String name;

    /**
     * 扩展方法对象
     */
    private Object fun;

}
