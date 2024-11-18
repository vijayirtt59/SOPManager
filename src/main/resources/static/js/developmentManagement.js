$(document).ready(function () {
    // Fetch and populate titles for create responsibility and map responsibility
    fetchTitles();

    // Create a new development
    $('#addDevelopmentForm').on('submit', function (e) {
        e.preventDefault();

        const number = $('#number').val();
        const numberSub = $('#numberSub').val();
        const activity = $('#activity').val();
        const responsible = $('#responsible').val();
        let description = $('#description').val();

        if (description) {
            description = description.split(','); // Split the description if there's data
        } else {
            description = []; // If no description, set it to an empty array
        }

        const formData = {
            number: number,
            activity: activity,
            responsible: responsible,
            description: description
        };

        $.ajax({
            url: '/api/developments',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                alert('Development created successfully!');
                $('#addDevelopmentForm')[0].reset();
            },
            error: function (error) {
                console.error('Error creating development:', error);
            }
        });
    });

    // Search for development by activity
    $("#searchDevelopmentForm").submit(function (e) {
        e.preventDefault();
        const activity = $("#searchActivity").val();

        $.get(`/api/developments/search/${activity}`, function (response) {
            const result = `
                <ul>
                    <li><strong>Activity:</strong> ${response.activity}</li>
                    <li><strong>Responsible:</strong> ${response.responsible}</li>
                    <li><strong>Description:</strong> ${response.description.join(', ') }</li>
                </ul>
                <button class="btn btn-warning" onclick="editDevelopment(${response.id})">Edit</button>
            `;
            $("#searchResult").html(result);
            $("#mapDevelopmentSection").show();
            populateDevelopmentDropdown(response);
        }).fail(function () {
            $("#searchResult").html("<p class='text-danger'>No development found.</p>");
            $("#mapDevelopmentSection").hide();
        });
    });

    window.editDevelopment = function(id) {
        $.get(`/api/developments/${id}`, function (response) {
            $('#editDevelopmentId').val(response.id);
            $('#editNumber').val(response.number);
            $('#editActivity').val(response.activity);
            $('#editResponsible').val(response.responsible);
            $('#editDescription').val(response.description.join(', '));

            $('#editDevelopmentSection').show();
        });
    };

    // Update development
    $('#editDevelopmentForm').on('submit', function (e) {
        e.preventDefault();

        const number = $('#editNumber').val();
        const activity = $('#editActivity').val();
        const responsible = $('#editResponsible').val();
        let description = $('#editDescription').val();

            // Only split description if it is not empty
        if (description) {
            description = description.split(','); // Split the description if there's data
        } else {
            description = []; // If no description, set it to an empty array
        }

            // Create form data object
        const formData = {
            number: number,
            activity: activity,
            responsible: responsible,
            description: description
        };

        const developmentId = $('#editDevelopmentId').val();

        $.ajax({
            url: `/api/developments/${developmentId}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                alert('Development updated successfully!');
                $('#editDevelopmentForm')[0].reset();
                $('#editDevelopmentSection').hide();
            },
            error: function (error) {
                console.error('Error updating development:', error);
            }
        });
    });

    // Map development to title
    $('#mapDevelopmentForm').on('submit', function (e) {
        e.preventDefault();

        const developmentId = $('#developmentId').val();
        const titleId = $('#titleId').val();

        $.ajax({
            url: '/api/developments/mapDevelopment',
            type: 'POST',
            data: { developmentId, titleId },
            success: function (response) {
                alert('Development mapped successfully!');
            },
            error: function (error) {
                console.error('Error mapping development:', error);
            }
        });
    });

    // Fetch titles for dropdowns
    function fetchTitles() {
        $.ajax({
            url: '/api/titles',
            type: 'GET',
            success: function (data) {
                let titleOptions = '';
                data.forEach(function (title) {
                    titleOptions += `<option value="${title.id}">${title.name}</option>`;
                });

                $('#titleId').html(titleOptions);
            },
            error: function (error) {
                console.error('Error fetching titles:', error);
            }
        });
    }

    // Populate development dropdown for mapping
    function populateDevelopmentDropdown(response) {
        let developmentOptions = '';
        developmentOptions += `<option value="${response.id}">${response.activity}</option>`;
        $('#developmentId').html(developmentOptions);
    }
});
