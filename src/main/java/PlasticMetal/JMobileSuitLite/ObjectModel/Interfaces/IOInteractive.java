package PlasticMetal.JMobileSuitLite.ObjectModel.Interfaces;

import PlasticMetal.JMobileSuitLite.IO.IOServer;

/**
 * Represents that an object is interactive to SuitHost's IOServer
 */
public interface IOInteractive
{
    /**
     * Provides Interface for SuitHost to set ioServer
     * @param io SuitHost's IOServer.
     */
    void SetIO(IOServer io);
}
