package PlasticMetal.JMobileSuitLite.ObjectModel;

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
    void setIO(IOServer io);
}
