package cn.belier.jxls.autoconfigure.function;

import cn.belier.jxls.function.DateFunction;
import lombok.Data;

/**
 * {@link DateFunction} 的配置
 *
 * @author belier
 * @date 2018/12/7
 */
@Data
public class DateFunctionConfig {

    private boolean enabled = true;

    private String name = DateFunction.NAME;

    private String date = DateFunction.DATE;

    private String datetime = DateFunction.DATE_TIME;

    private String time = DateFunction.TIME;

}
