$(document).ready(function () {
    // Fetch and populate titles for create responsibility and map responsibility
    fetchTitles();

    // Create a new responsibility
    $('#addResponsibilityForm').on('submit', function (e) {
        e.preventDefault();

        const formData = {
            position: $('#position').val(),
            note: $('#note').val(),
            bulletPoints: $('#bulletPoints').val().split(','),
            subPositions: $('#subPositions').val(),
            titles: $('#titles').val()
        };

        $.ajax({
            url: '/api/responsibilities',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                alert('Responsibility created successfully!');
                $('#addResponsibilityForm')[0].reset();
            },
            error: function (error) {
                console.error('Error creating responsibility:', error);
            }
        });
    });

    // Search for responsibilities by position
    $("#searchResponsibilityForm").submit(function (e) {
        e.preventDefault();
        const position = $("#searchPosition").val();

        $.get(`/api/responsibilities/search/${position}`, function (response) {
            const result = `
                <ul>
                    <li><strong>Position:</strong> ${response.position}</li>
                    <li><strong>Sub Positions:</strong> ${response.subPositions}</li>
                    <li><strong>Note:</strong> ${response.note}</li>
                    <li><strong>Responsibilities:</strong> ${response.bulletPoints.join(', ') }</li>
                </ul>
                <button class="btn btn-warning" onclick="editResponsibility(${response.id})">Edit</button>
            `;
            $("#searchResult").html(result);
            // Show map responsibility section after search
            $("#mapResponsibilitySection").show();
            // Populate responsibilityId dropdown for mapping
            populateResponsibilityDropdown(response);
        }).fail(function () {
            $("#searchResult").html("<p class='text-danger'>No responsibility found.</p>");
            $("#mapResponsibilitySection").hide(); // Hide map section if no result found
        });
    });

    window.editResponsibility = function(id) {
            $.get(`/api/responsibilities/${id}`, function (response) {
                // Show edit form with the current data
                $('#editResponsibilityId').val(response.id);
                $('#editPosition').val(response.position);
                $('#editSubPositions').val(response.subPositions);
                $('#editNote').val(response.note);
                $('#editResponsibilities').val(response.bulletPoints.join(', '));

                // Show the edit responsibility section
                $('#editResponsibilitySection').show();
            });
        };

        // Update responsibility
        $('#editResponsibilityForm').on('submit', function (e) {
            e.preventDefault();

            const formData = {
                position: $('#editPosition').val(),
                note: $('#editNote').val(),
                bulletPoints: $('#editResponsibilities').val().split(','),
                subPositions: $('#editSubPositions').val()
            };

            const responsibilityId = $('#editResponsibilityId').val();

            $.ajax({
                url: `/api/responsibilities/${responsibilityId}`,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                headers: {
                        "Accept": "application/json"
                },
                success: function (response) {
                    alert('Responsibility updated successfully!');
                    $('#editResponsibilityForm')[0].reset();
                    $('#editResponsibilitySection').hide();
                },
                error: function (error) {
                    console.error('Error updating responsibility:', error);
                }
            });
        });

        // Map responsibility to title
        $('#mapResponsibilityForm').on('submit', function (e) {
            e.preventDefault();

            const responsibilityId = $('#responsibilityId').val();
            const titleId = $('#titleId').val();

            $.ajax({
                url: '/api/responsibilities/mapResponsibility',
                type: 'POST',
                data: { responsibilityId, titleId },
                success: function (response) {
                    alert('Responsibility mapped successfully!');
                },
                error: function (error) {
                    console.error('Error mapping responsibility:', error);
                }
            });
        });

        // Function to fetch titles
        function fetchTitles() {
            $.ajax({
                url: '/api/titles',
                type: 'GET',
                success: function (data) {
                    let titleOptions = '';
                    data.forEach(function (title) {
                        titleOptions += `<option value="${title.id}">${title.name}</option>`;
                    });

                    $('#titles').html(titleOptions);
                    $('#titleId').html(titleOptions);
                },
                error: function (error) {
                    console.error('Error fetching titles:', error);
                }
            });
        }

        // Function to populate the dropdown for mapping responsibility
        function populateResponsibilityDropdown(response) {
            let responsibilityOptions = '';
            // Assuming response has an ID to be used as the responsibility id
            responsibilityOptions += `<option value="${response.id}">${response.position}</option>`;
            $('#responsibilityId').html(responsibilityOptions);
        }
});
