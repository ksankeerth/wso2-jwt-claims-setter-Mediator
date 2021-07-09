package org.wso2.esb.mediators;

import org.apache.synapse.ManagedLifecycle;
import org.apache.synapse.mediators.AbstractMediator;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseException;
import org.apache.synapse.SynapseLog;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseLog;
import org.apache.synapse.SynapseException;
import io.jsonwebtoken.*;

public class JWTClaimsMediator extends AbstractMediator implements ManagedLifecycle {

    private static Log log = LogFactory.getLog(JWTClaimsMediator.class);

    private String publicKey = "";

    private String jwtToken = "";


    @Override
    public void init(SynapseEnvironment synapseEnvironment) {
        if (log.isInfoEnabled()) {
            log.info("Initializing JWTDecoder Mediator");
        }
    }
    
    @Override
    public boolean mediate(MessageContext synapseContext) {
        SynapseLog synLog = getLog(synapseContext);

        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("Start : JWTDecoder mediator");
            if (synLog.isTraceTraceEnabled()) {
                synLog.traceTrace("Message : " + synapseContext.getEnvelope());
            }
        }
        
        Jws<Claims> jws;

        try {
            jws = Jwts.parserBuilder()  // (1)
            .setSigningKey(publicKey)         // (2)
            .build()                    // (3)
            .parseClaimsJws(jwtToken); // (4)
            
            // we can safely trust the JWT
            // synapseContext.setProperty(tempPropName, claimEntry.getValue());
            // if(log.isDebugEnabled()){
            //     log.debug("Getting claim :"+tempPropName+" , " +claimEntry.getValue() );
            // } 
        catch (JwtException ex) {       // (5)
            
            // we *cannot* use the JWT as intended by its creator
            log.error(e.getMessage(), e);
            throw new SynapseException(e.getMessage(), e);
        }
        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("End : JWTDecoder mediator");
        }
       
        return true;
    }

    @Override
    public void destroy() {
        if (log.isInfoEnabled()) {
            log.info("Destroying JWTDecoder Mediator");
        }
    }
}
