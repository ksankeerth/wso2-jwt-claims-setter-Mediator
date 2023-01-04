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


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

public class JWTClaimsMediator extends AbstractMediator implements ManagedLifecycle {

    private static Log log = LogFactory.getLog(JWTClaimsMediator.class);




    @Override
    public void init(SynapseEnvironment synapseEnvironment) {
        if (log.isInfoEnabled()) {
            log.info("Initializing JWTDecoder Mediator");

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
         for(int i = 0; i < 10300; i++) {
             File tempFile = File.createTempFile("data" + Math.random() * i +"", ".txt");
             FileWriter fw = new FileWriter(tempFile);
             BufferedWriter bw = new BufferedWriter(fw);
             bw.write("This is the temporary data written to temp file");
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
