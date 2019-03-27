<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : currency.xsl
    Created on : March 5, 2019, 4:59 PM
    Author     : admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
   
    <xsl:template match="/">
        
        <html>
            <body>
                <p id="checkValue">Đơn vị: đồng</p><br/>           
                <table border="2">
                    <tr>
                        <th>Loại</th>
                        <th>Bán ra</th>
                        <th>Mua vào</th>   
                        <th>Khu vực</th>                       
                        <th>Ngày</th>
                    </tr>
                    <xsl:for-each select="golds/gold">
                         <xsl:sort select="district"/>
                        <tr>
                            <td>
                                <xsl:value-of select="typeOfGold"/>
                            </td>
                            
                             <td>                             
                                <xsl:value-of select="buy"/>
                                <pre></pre>
                                <xsl:if test="buy &lt; 0">
                                   <img src="images/down-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="buy > 0">
                                    <img src="images/up-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="buy = 0">
                                    <p>-</p>
                                </xsl:if>
                            </td>
                                 
                             
                            <td>                             
                                <xsl:value-of select="sale"/>
                                <pre></pre>
                                 <xsl:if test="sale &lt; 0">
                                    <img src="images/down-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="sale > 0">
                                     <img src="images/up-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="sale = 0">
                                    <p>-</p>
                                </xsl:if>
                            </td>
                             
                            <td>
                               
                                    <xsl:value-of select="date"/>
                               
                                
                            </td>
                             <td>
                               
                                    <xsl:value-of select="district"/>
                               
                                
                            </td>
                        </tr>
                      
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
