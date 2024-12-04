package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.ObjectModel.SuitConfigurator;

public interface IConfiguration {
    /**
     * return a SuitConfiguration from a Json File for the creation of SuitHost
     * @param JsonFilePath the path of the Json File,must be vaild
     * @return A SuitConfiguration Object,and if the file has wrong format,it will be null
     */
    public SuitConfiguration parse(String JsonFilePath);
}
