/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhi.state;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author admin
 */
public class TextUtils {
    public static String refineHtml (String src) {
        src = getBody(src);
        src = removeMiscellaneousTags(src);
        
        XmlSyntaxChecker xmlSyntaxChecker = new XmlSyntaxChecker();
        src = xmlSyntaxChecker.check(src);
        
        //crop one more time
        src = getBody(src);
        return src;
    }
    public static String getBody (String src){
        String result = src;
        
        String expression = "<body.*?</body>";
        Pattern pattern = Pattern.compile(expression);
        
        Matcher matcher = pattern.matcher(result);
        
        if (matcher.find()){
            result = matcher.group(0);
        }
        return result;
    }
    public static String removeMiscellaneousTags (String src){
        String result = src;
        
        //Remove all script> tags
        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");
        
        //Remove all comments
        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");
        
        //Remove all whitespace
        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");
        
        return result;
    }
    
}
