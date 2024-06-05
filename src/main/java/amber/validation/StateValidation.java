package amber.validation;

import amber.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//泛型当中第一个参数是要给哪个注解提供校验你规则，第二个参数是校验的数据类型
public class StateValidation implements ConstraintValidator<State,String> {
    //提供校验规则

    /**
     *
     * @param s
     * @param constraintValidatorContext
     * @return 如果返回false则校验不通过，如果返回true则校验通过
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if (s.equals("已发布")||s.equals("草稿")){
            return true;
        }
        return false;
    }
}
