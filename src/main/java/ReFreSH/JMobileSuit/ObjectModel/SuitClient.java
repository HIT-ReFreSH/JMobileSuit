package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.ObjectModel.Annotions.SuitIgnore;

/// <summary>
/// An Standard mobile suit client driver-class.
/// </summary>
@SuppressWarnings("unused")
public class SuitClient implements InfoProvider, IOInteractive {

    protected String _text = "";
    private IOServer _io;

    /**
     * The IOServer for current SuitHost.
     */
    public IOServer IO() {
        return _io;
    }

    /**
     * set The information provided.
     *
     * @param value The information provided.
     */
    public void setText(String value) {
        _text = value;
    }


    /**
     * The information provided.
     *
     * @return The information provided.
     */
    @SuitIgnore
    public String text() {
        return _text;
    }

    /**
     * Provides Interface for SuitHost to set ioServer
     *
     * @param io SuitHost's IOServer.
     */
    @SuitIgnore
    public void setIO(IOServer io) {
        _io = io;
    }
}
