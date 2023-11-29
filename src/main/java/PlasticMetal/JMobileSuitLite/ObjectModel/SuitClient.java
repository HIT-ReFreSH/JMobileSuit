package PlasticMetal.JMobileSuitLite.ObjectModel;

import PlasticMetal.JMobileSuitLite.IO.IOServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitIgnore;

/// <summary>
/// An Standard mobile suit client driver-class.
/// </summary>
@SuppressWarnings("unused")
public abstract class SuitClient implements InfoProvider, IOInteractive
{

    private IOServer _io;
    protected String _text="";

    /**
     * The IOServer for current SuitHost.
     */
    protected IOServer IO()
    {
        return _io;
    }

    /**
     * set The information provided.
     * @param value The information provided.
     */
    protected void setText(String value){
        _text=value;
    }


    /**
     * The information provided.
     *
     * @return The information provided.
     */
    @SuitIgnore
    public String text()
    {
        return _text;
    }

    /**
     * Provides Interface for SuitHost to set ioServer
     * @param io SuitHost's IOServer.
     */
     @SuitIgnore
    public void setIO(IOServer io)
    {
        _io = io;
    }
}
