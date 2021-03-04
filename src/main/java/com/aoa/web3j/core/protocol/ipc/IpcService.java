package com.aoa.web3j.core.protocol.ipc;

import com.aoa.web3j.core.protocol.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Ipc service implementation.
 */
public class IpcService extends Service {

    private static final Logger log = LoggerFactory.getLogger(IpcService.class);

    private final IOFacade ioFacade;

    public IpcService(IOFacade ioFacade, boolean includeRawResponses) {
        super(includeRawResponses);
        this.ioFacade = ioFacade;
    }

    public IpcService(IOFacade ioFacade) {
        this(ioFacade, false);
    }

    @Override
    protected InputStream performIO(String payload) throws IOException {
        ioFacade.write(payload);
        log.debug(">> " + payload);

        String result = ioFacade.read();
        log.debug("<< " + result);

        // It's not ideal converting back into an inputStream, but we want
        // to be consistent with the HTTPService API.
        return new ByteArrayInputStream(result.getBytes());
    }
    
    public void close() throws IOException {
        ioFacade.close();
    }
}
