package cn.belier.jxls.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belier
 * @date 2018/12/3
 */
@Getter
@Setter
public class JxlsConfig {


    /**
     * 模板所在路径
     */
    private String[] templateLoaderPath;

    /**
     * 是否开启静默模式
     */
    private boolean silent;

}
