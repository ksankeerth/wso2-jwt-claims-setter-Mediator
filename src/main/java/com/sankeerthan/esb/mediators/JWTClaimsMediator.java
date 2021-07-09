package com.sankeerthan.esb.mediators;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
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


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

public class JWTClaimsMediator extends AbstractMediator implements ManagedLifecycle {

    private static Log log = LogFactory.getLog(JWTClaimsMediator.class);

    private String publicKey = "";

    private String jwtToken = "";


    public void setPublicKey(String key) {
        this.publicKey = key;
    }

    public void setJwtToken(String token) {
        this.jwtToken = token;
    }


    @Override
    public void init(SynapseEnvironment synapseEnvironment) {
        if (log.isInfoEnabled()) {
            log.info("Initializing JWTDecoder Mediator");
            log.info(publicKey);
            log.info(jwtToken);
        }
    }
    
    @Override
    public boolean mediate(MessageContext synapseContext) {
        SynapseLog synLog = getLog(synapseContext);
        log.info("Mediattion started");

        if (synLog.isTraceOrDebugEnabled()) {
            synLog.traceOrDebug("Start : JWTDecoder mediator");
            if (synLog.isTraceTraceEnabled()) {
                synLog.traceTrace("Message : " + synapseContext.getEnvelope());
            }
        }

        try {
            if (!JwtUtils.isValidJwt(jwtToken)) {
                throw new Exception("Malformed Json Web Token");
            }
            String encodedJwtBody = JwtUtils.getJwtBody(jwtToken);

            byte[] jwtBody = Base64.getDecoder().decode(encodedJwtBody);

            if (jwtBody == null || jwtBody.length == 0) {
                throw new Exception("Unknown error while setting claims from jwt");
            }
            String jsonBodyString = IOUtils.toString(jwtBody, String.valueOf(StandardCharsets.UTF_8));
            JsonObject jwtBodyJson = (JsonObject) JsonParser.parseString(jsonBodyString);

            Set<String> claimsKeys = jwtBodyJson.keySet();

            if (claimsKeys != null || claimsKeys.size() > 0) {
                for (String claimKey : claimsKeys) {
                    synapseContext.setProperty(claimKey, jwtBodyJson.get(claimKey).getAsString());
                    if(log.isDebugEnabled()){
                        log.debug("Getting claim :"+claimKey+" , " +jwtBodyJson.get(claimKey).getAsString() );
                    }
                }
            }

        }catch (Exception e){
            log.error("Error occurred when setting claims",e);
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
