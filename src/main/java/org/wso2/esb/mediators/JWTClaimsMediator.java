package org.wso2.esb.mediators;

import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.SynapseLog;
import org.apache.synapse.core.SynapseEnvironment;


public class JWTClaimsMediator extends AbstractMediator implements ManagedLifecycle {


    @Override
    public void init(SynapseEnvironment synapseEnvironment) {

    }
    
    @Override
    public boolean mediate(MessageContext messageContext) {

        return true;
    }

    public void destroy() {
        
    }
}
