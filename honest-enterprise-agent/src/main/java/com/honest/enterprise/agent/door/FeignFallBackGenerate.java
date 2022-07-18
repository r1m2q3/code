package com.honest.enterprise.agent.door;


import com.honest.enterprise.feign.core.auto.IAutoAssembleFallBackClass;

import java.util.ServiceLoader;

/**
 *
 * @author fanjie
 */
public class FeignFallBackGenerate {
    public void exec(){
        ServiceLoader<IAutoAssembleFallBackClass> internetServices = ServiceLoader.load(IAutoAssembleFallBackClass.class);
        for (IAutoAssembleFallBackClass internetService : internetServices) {
            internetService.registerByteCode();
        }
    }
}
