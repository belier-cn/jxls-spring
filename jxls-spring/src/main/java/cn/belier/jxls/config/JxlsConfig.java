package cn.belier.jxls.config;

import cn.belier.jxls.function.JxlsFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author belier
 * @date 2018/12/3
 */
@Getter
@Setter
@Accessors(chain = true)
public class JxlsConfig {


    /**
     * 模板所在路径
     */
    private String[] templateLoaderPath;

    /**
     * 是否开启静默模式
     */
    private boolean silent = false;


    /**
     * 是否缓存
     */
    private boolean cache = false;

    /**
     * 扩展方法
     */
    @Setter(AccessLevel.NONE)
    private Map<String, Object> functions;

    public JxlsConfig() {
        this.functions = new HashMap<>();
    }

    /**
     * 添加扩展方法
     *
     * @param name 使用前缀
     * @param fun  提供方法的对象
     * @return {@link JxlsConfig} 链式操作
     */
    public JxlsConfig addFunction(String name, Object fun) {
        this.functions.put(name, fun);
        return this;
    }

    /**
     * 添加扩展方法
     *
     * @param jxlsFunction {@link JxlsFunction}
     * @return {@link JxlsConfig} 链式操作
     */
    public JxlsConfig addFunction(JxlsFunction jxlsFunction) {
        this.functions.put(jxlsFunction.getName(), jxlsFunction.getFun());
        return this;
    }

    /**
     * 添加扩展方法列表
     *
     * @param functions 扩展方法列表
     * @return {@link JxlsConfig} 链式操作
     */
    public JxlsConfig addFunctions(Map<String, Object> functions) {
        this.functions.putAll(functions);
        return this;
    }

    /**
     * 添加扩展方法列表
     *
     * @param jxlsFunctions {@link JxlsFunction}
     * @return {@link JxlsConfig} 链式操作
     */
    public JxlsConfig addFunctions(List<JxlsFunction> jxlsFunctions) {
        if (CollectionUtils.isNotEmpty(jxlsFunctions)) {
            jxlsFunctions.forEach(this::addFunction);
        }
        return this;
    }
}
