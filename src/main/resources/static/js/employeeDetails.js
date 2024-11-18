document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const titleId = urlParams.get('id');

    if (titleId) {
        fetchTitleAndObjective(titleId);
    }
});

// Fetch Title, Objective, and Scope data
function fetchTitleAndObjective(id) {
    fetch(`/api/titles/${id}`)
        .then(response => response.json())
        .then(title => {
            displayTitleDetails(title);
            displayResponsibilities(title);
            displayDefinitions(title);
            displayDevelopments(title);
            fetchObjective(title.name);
        })
        .catch(error => console.error('Error fetching title:', error));
}

// Fetch Objective for the given title
function fetchObjective(titleName) {
    fetch(`/api/objectives/search/${titleName}`)
        .then(response => response.json())
        .then(objective => {
            displayObjectiveDetails(objective);
            fetchScope(titleName);
        })
        .catch(error => console.error('Error fetching objective:', error));
}

// Fetch Scope for the given title
function fetchScope(titleName) {
    fetch(`/api/scopes/search/${titleName}`)
        .then(response => response.json())
        .then(scope => {
            displayScopeDetails(scope);
        })
        .catch(error => console.error('Error fetching scope:', error));
}

// Display Title Details
function displayTitleDetails(title) {
    document.getElementById('titleName').textContent = title.name;
    document.getElementById('procedureNumber').textContent = title.procedureNumber;
    document.getElementById('applicationDate').textContent = title.applicationDate;
    document.getElementById('substitute').textContent = title.substitute;
    document.getElementById('revisionNo').textContent = title.revisionNo;
    document.getElementById('nextRevision').textContent = title.nextRevision;
}

// Display Objective Details
function displayObjectiveDetails(objective) {
    document.getElementById('objectiveTitleName').textContent = objective.title;
    document.getElementById('paragraph').textContent = objective.paragraph;

    const bulletPointsList = document.getElementById('bulletPoints');
    bulletPointsList.innerHTML = '';
    if (objective.bulletPoints && objective.bulletPoints.length > 0) {
        const bulletPoints = objective.bulletPoints[0].split('\n');
        bulletPoints.forEach(point => {
            const li = document.createElement('li');
            li.textContent = point.trim();
            bulletPointsList.appendChild(li);
        });
    }
}

// Display Scope Details
function displayScopeDetails(scope) {
    document.getElementById('scopeParagraph').textContent = scope.paragraph;
}

// Display Responsibilities
function displayResponsibilities(title) {
    const responsibilityList = document.getElementById('responsibilityList');
    responsibilityList.innerHTML = '';  // Clear the existing list

    title.responsibilities.forEach((responsibility, index) => {
        console.log(responsibility);
        const li = document.createElement('li');

        // Add the numbered title (3.1, 3.2, etc.)
        const header = document.createElement('strong');
        header.textContent = `${index + 1}. ${responsibility.position}`;
        li.appendChild(header);

        // Add sub-positions
        if (responsibility.subPositions) {
            const subPositionList = document.createElement('p');
            subPositionList.textContent = `(${responsibility.subPositions})`;
            li.appendChild(subPositionList);
        }

        // Add the note
        if (responsibility.note) {
            const noteParagraph = document.createElement('p');
            noteParagraph.innerHTML = `<strong>Nota:</strong> ${responsibility.note}`;
            noteParagraph.classList.add('note');
            li.appendChild(noteParagraph);
        }

        if (responsibility.bulletPoints && responsibility.bulletPoints.length > 0) {
                    console.log("I am here");
                    const bulletPointList = document.createElement('ul');

                    // Iterate over all bullet points
                    responsibility.bulletPoints.forEach(bulletPoint => {
                        const li = document.createElement('li');
                        const points = bulletPoint.split('\n'); // Split each bullet point by newline
                        points.forEach(point => {
                            const innerLi = document.createElement('li');
                            innerLi.textContent = point.trim(); // Trim any extra spaces
                            bulletPointList.appendChild(innerLi);
                        });
                    });
                    li.appendChild(bulletPointList);  // Add the list of bullet points to the li
                }
        responsibilityList.appendChild(li);
    });
}
function displayDefinitions(title) {

        const definitionList = document.getElementById('definitionList');
        definitionList.innerHTML = '';
        title.definitions.forEach((definition, index) => {
                console.log(definition);
                const li = document.createElement('li');

                // Add the numbered title (3.1, 3.2, etc.)
                const header = document.createElement('strong');
                header.textContent = `${index + 1}. ${definition.name}`;
                li.appendChild(header);

                // Add sub-positions
                if (definition.description) {
                    const definitionList = document.createElement('p');
                    definitionList.textContent = `${definition.description}`;
                    li.appendChild(definitionList);
                }
                definitionList.appendChild(li);
            });

}

function displayDevelopments(title) {
    const developmentTableBody = document.getElementById('developmentTableBody');
    developmentTableBody.innerHTML = '';  // Clear any existing rows

    title.developments.forEach(development => {
        const descriptions = development.description;  // Get the description array

        // We need to split the development description into multiple rows if there are multiple descriptions
        descriptions.forEach((desc, index) => {
            const row = document.createElement('tr');

            // Add Number and NumberSub
            const numCell1 = document.createElement('td');

            if (index === 0) {
                // For the first description, show the main number and numberSub together
                numCell1.innerHTML = `${development.number}`;
            } else {
                // For subsequent descriptions, number them as 1.1, 1.2, etc.
                numCell1.innerHTML = `${development.number}.${index + 1}`;
            }

            row.appendChild(numCell1);

            // Add Responsable (empty for all but the first description)
            const responsibleCell = document.createElement('td');
            responsibleCell.textContent = index === 0 ? development.responsible : '';
            row.appendChild(responsibleCell);

            // Add Actividad (empty for all but the first description)
            const activityCell = document.createElement('td');
            activityCell.textContent = index === 0 ? development.activity : '';
            row.appendChild(activityCell);

            // Add Descripción
            const descriptionCell = document.createElement('td');
            descriptionCell.textContent = desc;  // Add the current description
            row.appendChild(descriptionCell);

            // Append the row to the table
            developmentTableBody.appendChild(row);
        });
    });
}