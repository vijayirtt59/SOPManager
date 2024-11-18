$(document).ready(function() {
    // Form submission for creating/updating objectives
    $('#objectiveForm').submit(function(event) {
        event.preventDefault();
        var formData = {
            titleName: $('#titleName').val(),
            paragraph: $('#paragraph').val(),
            bulletPoints: $('#bulletPoints').val().split(',')
        };

        var url = $('#objectiveForm').attr('action');
        var method = url.includes('/api/objectives/') ? 'PUT' : 'POST';

        $.ajax({
            type: method,
            url: url,
            data: JSON.stringify(formData),
            contentType: 'application/json',
            success: function() {
                alert('Objective saved successfully!');
                location.reload();
            },
            error: function() {
                alert('Error saving objective.');
            }
        });
    });

    // Form submission for searching objectives
    $('#searchForm').submit(function(event) {
        event.preventDefault();
        var name = $('#searchName').val();

        $.ajax({
            type: 'GET',
            url: '/api/objectives/search/' + name,
            success: function(data) {
                var objectivesTable = $('#objectivesTable tbody');
                objectivesTable.empty();

                if (data) {
                    $('#objectivesSection').show();
                    objectivesTable.append(
                        '<tr>' +
                            '<td>' + data.id + '</td>' +
                            '<td>' + data.titleName + '</td>' +
                            '<td>' + data.paragraph + '</td>' +
                            '<td>' + data.bulletPoints.join(', ') + '</td>' +
                            '<td>' +
                                '<button class="btn btn-warning btn-sm" onclick="editObjective(' + data.id + ')">Edit</button>' +
                                '<button class="btn btn-danger btn-sm" onclick="deleteObjective(' + data.id + ')">Delete</button>' +
                            '</td>' +
                        '</tr>'
                    );
                } else {
                    alert('No objectives found.');
                    $('#objectivesSection').hide();
                }
            },
            error: function() {
                alert('Error searching objective.');
            }
        });
    });
});

function editObjective(id) {
    $.get('/api/objectives/' + id, function(data) {
        $('#titleName').val(data.titleName);
        $('#paragraph').val(data.paragraph);
        $('#bulletPoints').val(data.bulletPoints.join(','));
        $('#objectiveForm').attr('action', '/api/objectives/' + id);
    });
}

function deleteObjective(id) {
    if (confirm('Are you sure you want to delete this objective?')) {
        $.ajax({
            type: 'DELETE',
            url: '/api/objectives/' + id,
            success: function() {
                alert('Objective deleted successfully!');
                location.reload();
            },
            error: function() {
                alert('Error deleting objective.');
            }
        });
    }
}

function adjustTextAreaHeight(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}

document.addEventListener('DOMContentLoaded', () => {
    const bulletPointsTextArea = document.getElementById('bulletPoints');
    bulletPointsTextArea.addEventListener('input', () => adjustTextAreaHeight(bulletPointsTextArea));
});
