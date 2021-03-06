package com.cjf.util.singleton;


import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

/**
 * <p>Title: ISingletonManager </p>
 * <p>Description:  </p>
 * <p>Company: www.mapuni.com </p>
 *
 * @author : 蔡俊峰
 * @version : 1.0
 * @date : 2020/1/2 15:55
 */
public interface ISingletonManager {

    /**
     * 单例，弱引用
     */
    @NonNull
    <P extends ISingletonWrapper> P getInstance(@NonNull final Class<P> key,
            @NonNull final Function<Class<P>, P> function);

}
