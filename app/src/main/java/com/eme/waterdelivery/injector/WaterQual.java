package com.eme.waterdelivery.injector;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by dijiaoliang on 17/3/2.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface WaterQual {
}
