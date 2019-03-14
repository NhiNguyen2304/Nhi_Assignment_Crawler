<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : chart.xsl
    Created on : March 9, 2019, 4:44 PM
    Author     : admin
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/">
        <html>
            <head>
                <title>chart.xsl</title>
            </head>
            <body>
                <script language="javascript">
                   
                    window.onload = function () {
                    
                    var chart = new CanvasJS.Chart("chartContainer", {
                    animationEnabled: true,
                    theme: "light2",
                    title: {
                    text: "Tỉ giá"
                    },
                    axisX: {
                    crosshair: {
                    enabled: true,
                    snapToDataPoint: true
                    }
                    },
                    axisY: {
                    title: "Tỉ lệ",
                    crosshair: {
                    enabled: true
                    }
                    },
                    toolTip: {
                    shared: true
                    },
                    legend: {
                    cursor: "pointer",
                    verticalAlign: "bottom",
                    horizontalAlign: "left",
                    dockInsidePlotArea: true,
                    itemclick: toogleDataSeries
                    },
                    data: [{
                    type: "line",
                    showInLegend: true,
                    name: "USD",
                    markerType: "square",
                    color: "#F08080",
                    dataPoints: [
                    <xsl:for-each select="currencyRates/currencyRate">
                       <xsl:if test="currencyCode='USD'">
                           
                            {
                            x: <xsl:value-of select="date"/>, y: <xsl:value-of select="round(buyingRate)"/>
                            },
                       </xsl:if> 
                        
                    </xsl:for-each>
                    ]
                    },
                    {
                    type: "line",
                    showInLegend: true,
                    name: "EUR",
                    lineDashType: "dash",
                    dataPoints: [
                    <xsl:for-each select="currencyRates/currencyRate">
                      
                         <xsl:if test="currencyCode='EUR'">  
                            {
                            x: <xsl:value-of select="date"/>, y: <xsl:value-of select="round(buyingRate)"/>
                            },
                         </xsl:if>
                       
                    </xsl:for-each>
                    ]
                    },
                    {
                    type: "line",
                    showInLegend: true,
                    name: "JPY",
                    lineDashType: "dash",
                    dataPoints: [
                    <xsl:for-each select="currencyRates/currencyRate">
                        <xsl:if test="currencyCode='JPY'">
                          
                            {
                            x: <xsl:value-of select="date"/>, y: <xsl:value-of select="round(buyingRate)"/>
                            },
                           
                        </xsl:if>
                    </xsl:for-each>
                    ]
                    }
                    ]
                    });
                    chart.render();

                    function toogleDataSeries(e) {
                    if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                    e.dataSeries.visible = false;
                    } else {
                    e.dataSeries.visible = true;
                    }
                    chart.render();
                    }

                    }
                  
                </script>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
