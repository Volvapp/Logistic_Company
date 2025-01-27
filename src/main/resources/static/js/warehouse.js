const computersSection = document.getElementById('computersId');
const smartphonesSection = document.getElementById('smartphonesId');

const allComputers = [];
const allSmartphones = [];

const displayComputers = (computers) => {
    computersSection.innerHTML += computers.map(
        (c) => {
            return asComputer(c);
        }
    ).join('');
};

const displaySmartphones = (smartphones) => {
    smartphonesSection.innerHTML += smartphones.map(
        (s) => {
            return asSmartphone(s);
        }
    ).join('');
};

const displayComputersEmptyElement = () => {
    computersSection.innerHTML +=
        `<h3 class="text-white text-center bg-blur bg-danger border-top border-left border-right border-bottom ">NO COMPUTERS AVAILABLE</h3>`;
}

const displaySmartphonesEmptyElement = () => {
    smartphonesSection.innerHTML +=
        `<h3 class="text-white text-center bg-blur bg-danger border-top border-left border-right border-bottom ">NO SMARTPHONES AVAILABLE</h3>`;
}

function asComputer(c) {
    let computerHtml = `
    <div>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Id: ${c.id}</h5>
                <h5 class="card-title">Name: ${c.name}</h5>
                <h5 class="card-title">Is Published: ${c.published}</h5>
                <p class="card-text">Description: ${c.description}</p>
            </div>
        </div>
    
        <span class="p-xl-5"></span>
    </div>`;

    return computerHtml;
}

function asSmartphone(s) {
    let smartphoneHtml = `
    <div>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Id: ${s.id}</h5>
                <h5 class="card-title">Model Name: ${s.modelName}</h5>
                <h5 class="card-title">Is Published: ${s.published}</h5>
                <p class="card-text">Description: ${s.description}</p>
            </div>
        </div>

        <span class="p-xl-5"></span>
    </div>`;

    return smartphoneHtml;
}

fetch('http://localhost:8080/api/warehouse/computers')
    .then(response => response.json())
    .then(data => {
        for (let computer of data) {
            allComputers.push(computer);
        }
        if (allComputers.length === 0) {
            displayComputersEmptyElement();
        } else {
            displayComputers(allComputers);
        }
    });

fetch('http://localhost:8080/api/warehouse/smartphones')
    .then(response => response.json())
    .then(data => {
        for (let smartphone of data) {
            allSmartphones.push(smartphone);
        }
        if (allSmartphones.length === 0) {
            displaySmartphonesEmptyElement();
        } else {
            displaySmartphones(allSmartphones);
        }
    });


//Smartphone Card
// <div>
//     <div class="card">
//         <div class="card-body">
//             <h5 class="card-title">Model Name</h5>
//             <p class="card-text">Description</p>
//         </div>
//     </div>
//
//     <span class="p-xl-5"></span>
// </div>

//Computer Card
// <div>
//     <div class="card">
//         <div class="card-body">
//             <h5 class="card-title">Id: 2</h5>
//             <h5 class="card-title">Name: Pesho</h5>
//             <p class="card-text">Description: Hello</p>
//         </div>
//     </div>
//
//     <span class="p-xl-5"></span>
// </div>