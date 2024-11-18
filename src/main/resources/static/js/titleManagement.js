$(document).ready(function() {
    $('#searchForm').submit(function(event) {
        event.preventDefault();
        var name = $('#searchName').val();
        $.ajax({
            url: '/api/titles?name=' + name,
            method: 'GET',
            success: function(data) {
                var tableBody = $('#resultsTableBody');
                tableBody.empty();
                if (data.length > 0) {
                    data.forEach(function(title) {
                        var row = '<tr>' +
                            '<td>' + title.name + '</td>' +
                            '<td>' + title.procedureNumber + '</td>' +
                            '<td>' + title.applicationDate + '</td>' +
                            '<td>' + title.substitute + '</td>' +
                            '<td>' + title.revisionNo + '</td>' +
                            '<td>' + title.nextRevision + '</td>' +
                            '<td>' +
                            '<button onclick="editTitle(' + title.id + ')">Edit</button>' +
                            '<button onclick="deleteTitle(' + title.id + ')">Delete</button>' +
                            '</td>' +
                            '</tr>';
                        tableBody.append(row);
                    });
                    $('#searchResults').show();
                } else {
                    $('#searchResults').hide();
                }
            }
        });
    });

    $('#titleForm').submit(function(event) {
        event.preventDefault();
        var id = $('#titleId').val();
        var title = {
            name: $('#name').val(),
            procedureNumber: $('#procedureNumber').val(),
            applicationDate: $('#applicationDate').val(),
            substitute: $('#substitute').val(),
            revisionNo: $('#revisionNo').val(),
            nextRevision: $('#nextRevision').val()
        };
        var method = id ? 'PUT' : 'POST';
        var url = '/api/titles' + (id ? '/' + id : '');
        $.ajax({
            url: url,
            method: method,
            contentType: 'application/json',
            data: JSON.stringify(title),
            success: function() {
                $('#titleForm')[0].reset();
                $('#titleId').val('');
                alert('Title saved successfully!');
                $('#searchForm').submit();
            }
        });
    });
});

function editTitle(id) {
    $.ajax({
        url: '/api/titles/' + id,
        method: 'GET',
        success: function(data) {
            $('#titleId').val(data.id);
            $('#name').val(data.name);
            $('#procedureNumber').val(data.procedureNumber);
            $('#applicationDate').val(data.applicationDate);
            $('#substitute').val(data.substitute);
            $('#revisionNo').val(data.revisionNo);
            $('#nextRevision').val(data.nextRevision);
        }
    });
}

function deleteTitle(id) {
    if (confirm('Are you sure you want to delete this title?')) {
        $.ajax({
            url: '/api/titles/' + id,
            method: 'DELETE',
            success: function() {
                alert('Title deleted successfully!');
                $('#searchForm').submit();
            }
        });
    }
}
