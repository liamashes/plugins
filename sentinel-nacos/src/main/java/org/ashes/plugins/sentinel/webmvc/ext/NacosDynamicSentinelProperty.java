package org.ashes.plugins.sentinel.webmvc.ext;

import com.alibaba.csp.sentinel.property.DynamicSentinelProperty;

/**
 * @author: ashes
 * @create: 2023-07-24 16:11
 * @description: Add the ability to synchronize
 */
public class NacosDynamicSentinelProperty<T> extends DynamicSentinelProperty<T> {

    private final PropertiesSyncer<T> syncer;

    public NacosDynamicSentinelProperty(PropertiesSyncer<T> syncer) {
        this.syncer = syncer;
    }

    @Override
    public boolean updateValue(T newValue) {
        boolean r = super.updateValue(newValue);
        if (r) {
            syncer.sync(newValue);
        }
        return r;
    }
}
