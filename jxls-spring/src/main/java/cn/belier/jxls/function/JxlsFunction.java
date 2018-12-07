package cn.belier.jxls.function;

import lombok.Builder;
import lombok.Data;

/**
 * @author belier
 * @date 2018/12/6
 */
@Data
@Builder
public class JxlsFunction {

    private String name;

    private Object fun;

}
