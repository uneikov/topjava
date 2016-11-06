var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function rowColor(row) {
    return row.exceed ? 'exceeded' : 'normal';
}

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        paging: false,
        info: true,
        columns: [
            {
                data: "dateTime",
                render: function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) +
                            '">'+data.substring(0,10)+ "  " + data.substring(11,16)+'</span>';
                    }
                    return data;
                }
            },
            {
                data: "description",
                render:  function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) + '">'+data+'</span>';
                    }
                    return data;
                }
            },
            {
                data: "calories",
                render:  function (data, type, row) {
                    if (type == 'display') {
                        return '<span class="' + rowColor(row) + '">'+data+'</span>';
                    }
                    return data;
                }
            },
            {
                orderable: false, defaultContent: "", render: renderEditBtn
            },
            {
                orderable: false, defaultContent: "", render: renderDeleteBtn
            }
        ],
        order: [
            [
                0,
                "desc"
            ]
        ],
        initComplete: makeEditable
    });
});