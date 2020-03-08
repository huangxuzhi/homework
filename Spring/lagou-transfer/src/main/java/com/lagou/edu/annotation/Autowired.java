package com.lagou.edu.annotation;

import java.lang.annotation.*;

/**
 * @author huangxz
 * @date ${date}
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD,ElementType.FIELD})
public @interface Autowired {
}
