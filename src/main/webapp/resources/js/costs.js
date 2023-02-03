function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/costs/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: "ajax/profile/costs/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "cost"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: updateFilteredTable
    });
});