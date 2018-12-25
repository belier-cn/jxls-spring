package cn.belier.jxls.function;

import lombok.Setter;

/**
 * @author belier
 * @date 2018/12/13
 */
public class AbstractJxlsFunction implements JxlsFunction {

    @Setter
    protected String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getFunction() {
        return this;
    }
}
