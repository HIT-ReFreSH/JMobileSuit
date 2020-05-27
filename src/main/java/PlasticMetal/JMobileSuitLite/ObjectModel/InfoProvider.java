package PlasticMetal.JMobileSuitLite.ObjectModel;

import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents an object which can provide information to Mobile Suit.
 */
public interface InfoProvider
{
    static String getInfo(SuitInfo suitInfo){
        return getInfo(suitInfo.resourceBundleName(),suitInfo.value());
    }

    static String getInfo(String bundleName,String key){
        if(bundleName.equals(""))return key;
        ResourceBundle bundle= ResourceBundle.getBundle(bundleName,  Locale.getDefault());
        return bundle.getString(key);
    }

    /// <summary>
    /// The information provided.
    /// </summary>
    String text();
}
