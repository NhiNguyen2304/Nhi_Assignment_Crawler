(function(){
var regObj;
var xmlDOM = new ActiveXObject("Microsoft.XMLDOM");
var count = 0;
var cells = [];
var check = fasle;
function addRow(tableId, cells) {
    var tableElem = document.getElementById(tableId);
    var newRow = tableElem.insertRow(tableElem.rows.length);
    var newCell;
    for (var i = 0; i < cells.length; i++) {
        newCell = newRow.insertCell(newRow.cells.length);
        newCell.innerHTML = cells[i];

    }
    return newRow;

}
function deleteRow(tableId, rowNumber) {
    var tableElem = document.getElementById(tableId);
    if (rowNumber > 0 && rowNumber < tableElem.rows.length) {
        tableElem.deleteRow(rowNumber);
    } else {
        alert("Failed");
    }
}
function searchNode(node, strSearch, tableName) {
    if (node == null) {
        return;
    }

    var img = document.createElement('img');
    img.src = "images/down-arrow16.png";


    if (node.tagName === "currencyCode") {


        var tmp = node.firstChild.nodeValue;
        if (tmp.indexOf(strSearch, 0) > -1) {
            // count++;
            //cells[0] = count;
            cells[0] = node.firstChild.nodeValue; //currencyCode
            // cells[1] = node.lastChild.nodeValue; //currencyCode
            var siblingName = node.nextSibling;
            cells[1] = siblingName.firstChild.nodeValue;//name
            var siblingBuying = siblingName.nextSibling;
            cells[2] = siblingBuying.firstChild.nodeValue;//buying 

            var siblingTransfer = siblingBuying.nextSibling;
            cells[3] = siblingTransfer.firstChild.nodeValue;//transfer                      
            var siblingSell = siblingTransfer.nextSibling;
            cells[4] = siblingSell.firstChild.nodeValue;
            var siblingDate = siblingSell.nextSibling;
            cells[5] = siblingDate.firstChild.nodeValue;

            //cel[4] = siblingTransfer.lastChild.nodeValue;

            addRow(tableName, cells);
        }
    }
    var childs = node.childNodes;
    for (var i = 0; i < childs.length; i++) {
        searchNode(childs[i], strSearch, tableName);
    }

}
function searchProcess(tableName, checked) {
    if (!regObj) {
        alert("NULL");
        return false;

    }
    if (regObj) {
        xmlDOM.async = false;
        xmlDOM.loadXML(regObj);
        if (xmlDOM.parseError.errorCode != 0) {
            alert("Error: " + xmlDoc.parseError.reason);
        } else {
            var tableElem = document.getElementById(tableName);
            var i = 1;
            while (i < tableElem.rows.length) {
                deleteRow(tableName, i);
            }
            count = 0;
            searchNode(xmlDOM, myForm.txtCode.value, tableName);
        }
    }
    table_visibility(tableName);
    checked = true;
}
function printNode(node, n) {
    if (node == null) {
        return;
    }
    for (var i = 0; i < n; i++) {
        document.write("-");
    }
    document.write("name: " + node.nodeName);
}
function table_visibility(id) {
    var e = document.getElementById(id);
    if (check == false)
        e.style.visibility = 'hidden';
    else
        e.style.visibility = 'visible';
}
}());
//function addRowJudge(tableID, key) {
//    // Get a reference to the table
//    let tableRef = document.getElementById(tableID);
//
//    // Insert a row at the end of the table
//    let newRow = tableRef.insertRow(-1);
//
//    // Insert a cell in the row at index 0
//   
//    let newCell = newRow.insertCell(-1);
//
//    // Append a text node to the cell
//    var img = document.createElement("IMG");
//
//    if (key == 'up') {
//
//        img.setAttribute("src", "images/up-arrow16.png");
//
//    }
//    if (key == 'down') {
//
//        img.setAttribute("src", "images/down-arrow16.png");
//
//    }
//
//    //let newText = document.createTextNode('New bottom row');
//    newCell.appendChild(img);
//}
//
//// Call addRow() with the table's ID



    