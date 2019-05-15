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
                
                <table border="2">
                    <tr>
                        <th>Mã</th>
                        <th>Giá mua</th>
                        <th>Giá mua chuyển khoản</th>
                        <th>Bán ra</th>
                        <th>Ngày</th>
                    </tr>
                    <xsl:for-each select="currencies/currency">
                        <xsl:if test="date='10-03-2019'">
                            
                        <tr>
                           
                            <td>
                                
                                 <xsl:value-of select="currencyCode"/>
                                
                            </td>
                            <td> 
                                <xsl:choose>
                                    <xsl:when test="buying = 0">
                                          <p>-</p>
                                    </xsl:when>
                                    <xsl:otherwise>
                                         <xsl:value-of select="round(buying)"/>
                                    </xsl:otherwise>
                                </xsl:choose>                            
                                
                                
                            </td>
                            <td>
                                  <xsl:choose>
                                    <xsl:when test="purchaseByTransfer = 0">
                                          <p>-</p>
                                    </xsl:when>
                                    <xsl:otherwise>
                                          <xsl:value-of select="round(purchaseByTransfer)"/>
                                    </xsl:otherwise>
                                </xsl:choose> 
                               
                            </td>
                            <td>
                                 <xsl:choose>
                                    <xsl:when test="sale = 0">
                                          <p>-</p>
                                    </xsl:when>
                                    <xsl:otherwise>
                                          <xsl:value-of select="round(sale)"/>
                                    </xsl:otherwise>
                                </xsl:choose> 
                                
                            </td>
                           
<!--                            <td>
                                <xsl:if test="buyingRate &lt; 0">
                                    <p>Giảm</p>
                                </xsl:if>
                                <xsl:if test="buyingRate > 0">
                                    <p>Tăng</p>
                                </xsl:if>
                                <xsl:if test="buyingRate = 0">
                                    <p>Bình ổn</p>
                                </xsl:if>
                                
                                </td>-->
                                <td>
                               
                                    <xsl:value-of select="date"/>
                               
                                
                                </td>
                            </tr>
                        </xsl:if>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
