$(document).ready(function() {
    // Form submission for creating/updating scopes
    $('#scopeForm').submit(function(event) {
        event.preventDefault();
        var formData = {
            titleName: $('#titleName').val(),
            paragraph: $('#paragraph').val()
        };

        var url = $('#scopeForm').attr('action');
        var method = url.includes('/api/scopes/') ? 'PUT' : 'POST';

        $.ajax({
            type: method,
            url: url,
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function() {
                alert('Scope saved successfully!');
                location.reload();
            },
            error: function() {
                alert('Error saving scope.');
            }
        });
    });

    // Form submission for searching scopes
    $('#searchForm').submit(function(event) {
        event.preventDefault();
        var name = $('#searchName').val();

        $.ajax({
            type: 'GET',
            url: '/api/scopes/search/' + name,
            success: function(data) {
                var scopesTable = $('#scopesTable tbody');
                scopesTable.empty();

                if (data) {
                    $('#scopesSection').show();
                    scopesTable.append(
                        '<tr>' +
                            '<td>' + data.id + '</td>' +
                            '<td>' + data.titleName + '</td>' +
                            '<td>' + data.paragraph + '</td>' +
                            '<td>' +
                                '<button class="btn btn-warning btn-sm" onclick="editScope(' + data.id + ')">Edit</button>' +
                                '<button class="btn btn-danger btn-sm" onclick="deleteScope(' + data.id + ')">Delete</button>' +
                            '</td>' +
                        '</tr>'
                    );
                } else {
                    alert('No scopes found.');
                    $('#scopesSection').hide();
                }
            },
            error: function() {
                alert('Error searching scope.');
            }
        });
    });
});

function editScope(id) {
    $.get('/api/scopes/' + id, function(data) {
        $('#titleName').val(data.titleName);
        $('#paragraph').val(data.paragraph);
        $('#scopeForm').attr('action', '/api/scopes/' + id);
    });
}

function deleteScope(id) {
    if (confirm('Are you sure you want to delete this scope?')) {
        $.ajax({
            type: 'DELETE',
            url: '/api/scopes/' + id,
            success: function() {
                alert('Scope deleted successfully!');
                location.reload();
            },
            error: function() {
                alert('Error deleting scope.');
            }
        });
    }
}
