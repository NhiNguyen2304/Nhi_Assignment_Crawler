/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.properties;

import java.util.Properties;

/**
 *
 * @author admin
 */
public class NhiSaveProperties {

    public void saveProInXml(String fileAndPathProp, String fileAndPathXml) {
      final PropertiesExamples me = new PropertiesExamples();
        Properties inputProperties = me.loadTraditionalProperties(fileAndPathProp);
        me.storeXMLPropertiesFile(inputProperties,fileAndPathXml);
    }
}
