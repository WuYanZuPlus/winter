package com.jianghu.winter.query.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

/**
 * @author daniel.hu
 * @date 2019/8/23 11:24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtils {

    /**
     * @param target Object that need to be validated
     * @return field errors
     */
    public static List<FieldError> checkBean(Object target) {
        try (LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean()) {
            localValidatorFactoryBean.afterPropertiesSet();
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(target, "target");
            localValidatorFactoryBean.validate(target, bindingResult);
            return bindingResult.getFieldErrors();
        }
    }
}
