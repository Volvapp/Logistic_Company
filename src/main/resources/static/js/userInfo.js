const balanceListItem = document.getElementById('balanceId');

const userInfo = [];

const displayUserInfo = (user) => {
    balanceListItem.innerHTML += user.map(
        (u) => {
            return asUser(u);
        }
    ).join('');
};

function asUser(u){
    let userInfoHtml = `<div class="m-2 font-weight-bold">Balance: ${u.balance}$</div>`;

    return userInfoHtml;
}

fetch('http://localhost:8080/api/user/info')
    .then(response => response.json())
    .then(data => {
       userInfo.push(data);

       displayUserInfo(userInfo);
    });