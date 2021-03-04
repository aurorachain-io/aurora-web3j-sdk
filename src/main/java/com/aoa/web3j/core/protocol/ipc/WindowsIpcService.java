package com.aoa.web3j.core.protocol.ipc;

/**
 * Windows named pipe implementation of our services API.
 *
 * <p>This implementation is experimental.
 */
public class WindowsIpcService extends IpcService {

    public WindowsIpcService(String ipcSocketPath) {
        super(new WindowsNamedPipe(ipcSocketPath));
    }

    public WindowsIpcService(String ipcSocketPath, boolean includeRawResponse) {
        super(new WindowsNamedPipe(ipcSocketPath), includeRawResponse);
    }
}
