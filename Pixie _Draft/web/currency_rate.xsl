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
                <p id="checkValueUnit">Đơn vị: đồng</p><br/>             
                <table border="2">
                    <tr>
                        <th>Mã</th>
                        <th>Tên</th>
                        <th>Mua</th>        
                        <th>Mua chuyển khoản</th>                     
                        <th>Bán ra</th>                      
                        <th>Ngày</th>
                    </tr>
                    <xsl:for-each select="currencyRates/currencyRate">
                        
                        <tr>
                            <td>
                                <xsl:value-of select="currencyCode"/>
                            </td>
                            <td>
                                <xsl:value-of select="name"/>
                            </td>
                            <td>                             
                                <xsl:value-of select="round(buyingRate)"/>
                                <pre></pre>
                                <xsl:if test="buyingRate &lt; 0">
                                    <img src="images/down-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="buyingRate > 0">
                                    <img src="images/up-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="buyingRate = 0">
                                    <p>-</p>
                                </xsl:if>
                            </td>
                           
                            <td>                             
                                <xsl:value-of select="round(transferRate)"/>
                                <pre></pre>
                                <pre></pre>
                                <xsl:if test="transferRate &lt; 0">
                                    <img src="images/down-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="transferRate > 0">
                                    <img src="images/up-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="transferRate = 0">
                                    <p>-</p>
                                </xsl:if>
                            </td>
                             
                            <td>                             
                                <xsl:value-of select="round(sellingRate)"/>
                                <pre></pre>
                                <xsl:if test="sellingRate &lt; 0">
                                    <img src="images/down-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="sellingRate > 0">
                                    <img src="images/up-arrow16.png"/>
                                </xsl:if>
                                <xsl:if test="sellingRate = 0">
                                    <p>-</p>
                                </xsl:if>
                            </td>
                             
                            <td>
                               
                                <xsl:value-of select="date"/>
                               
                                
                            </td>
                        </tr>
                      
                    </xsl:for-each>
                </table>
               
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
