$(document).ready(function () {
    // Fetch and populate titles for creating definitions and mapping definitions
    fetchTitles();

    // Create a new definition
    $('#addDefinitionForm').on('submit', function (e) {
        e.preventDefault();

        const formData = {
            name: $('#name').val(),
            description: $('#description').val(),
            titles: $('#titles').val()
        };

        $.ajax({
            url: '/api/definitions',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                alert('Definition created successfully!');
                $('#addDefinitionForm')[0].reset();
            },
            error: function (error) {
                console.error('Error creating definition:', error);
            }
        });
    });

    // Search for definitions by name
    $("#searchDefinitionForm").submit(function (e) {
        e.preventDefault();
        const name = $("#searchName").val();

        $.get(`/api/definitions/search/${name}`, function (response) {
            const result = `
                <ul>
                    <li><strong>Name:</strong> ${response.name}</li>
                    <li><strong>Description:</strong> ${response.description}</li>
                </ul>
                <button class="btn btn-warning" onclick="editDefinition(${response.id})">Edit</button>
            `;
            $("#searchResult").html(result);
            // Show map definition section after search
            $("#mapDefinitionSection").show();
            // Populate definitionId dropdown for mapping
            populateDefinitionDropdown(response);
        }).fail(function () {
            $("#searchResult").html("<p class='text-danger'>No definition found.</p>");
            $("#mapDefinitionSection").hide();
        });
    });

    window.editDefinition = function(id) {
        $.get(`/api/definitions/${id}`, function (response) {
            $('#editDefinitionId').val(response.id);
            $('#editName').val(response.name);
            $('#editDescription').val(response.description);
            // Add titles to the select options
            populateTitlesDropdown(response.titles);

            $('#editDefinitionSection').show();
        });
    };

    // Update definition
    $('#editDefinitionForm').on('submit', function (e) {
        e.preventDefault();

        const formData = {
            name: $('#editName').val(),
            description: $('#editDescription').val(),
            titles: $('#editTitles').val()
        };

        const definitionId = $('#editDefinitionId').val();

        $.ajax({
            url: `/api/definitions/${definitionId}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                alert('Definition updated successfully!');
                $('#editDefinitionForm')[0].reset();
                $('#editDefinitionSection').hide();
            },
            error: function (error) {
                console.error('Error updating definition:', error);
            }
        });
    });

    // Map definition to title
    $('#mapDefinitionForm').on('submit', function (e) {
        e.preventDefault();

        const definitionId = $('#definitionId').val();
        const titleId = $('#titleId').val();

        $.ajax({
            url: '/api/definitions/mapDefinition',
            type: 'POST',
            data: { definitionId, titleId },
            success: function (response) {
                alert('Definition mapped successfully!');
            },
            error: function (error) {
                console.error('Error mapping definition:', error);
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

    // Function to populate titles dropdown for editing and mapping
    function populateTitlesDropdown(titles) {
        let titleOptions = '';
        titles.forEach(function (title) {
            titleOptions += `<option value="${title.id}" selected>${title.name}</option>`;
        });
        $('#editTitles').html(titleOptions);
    }

    // Function to populate the dropdown for mapping definitions
    function populateDefinitionDropdown(response) {
        let definitionOptions = '';
        definitionOptions += `<option value="${response.id}">${response.name}</option>`;
        $('#definitionId').html(definitionOptions);
    }
});
