function deleteTableRow(data) {
    const trTag = data.parentNode.parentNode;
    const route = data.getAttribute('data-route-param');
    const id = data.getAttribute('data-row-id');
    const url = "/" + route + "/" + id + "/delete";

    let xmlHttpRequest = new XMLHttpRequest();

    xmlHttpRequest.onreadystatechange = function () {
        if (xmlHttpRequest.readyState == xmlHttpRequest.DONE && xmlHttpRequest.status == 200) {
            const tbodyTag = trTag.parentNode;

            tbodyTag.removeChild(trTag);
        }
    };

    xmlHttpRequest.open("POST", url);
    xmlHttpRequest.send();
}