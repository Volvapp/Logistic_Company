
    var initialMoney;

    function showMoney(event) {
    let moneyOne = document.getElementById('totalMoneyOne');
    let moneyTwo = document.getElementById('totalMoneyTwo');

    if (event.target.value === '' && initialMoney !== undefined) {
    moneyOne.innerText = '$' + initialMoney;
    moneyTwo.innerText = '$' + initialMoney;
    return;
}

    if (event.target.value === '') {
    initialMoney = moneyOne.innerText.split('$')[1];
    return;
}
    if (moneyOne.innerHTML.split('$')[0] !== moneyTwo.innerHTML.split('$')[0]) {
    return;
}

    let moneyDiscount = initialMoney * event.target.value.split('%')[0] / 100;
    let money = initialMoney - moneyDiscount;
    money = money.toFixed(2);
    moneyOne.innerText = '$' + money;
    moneyTwo.innerText = '$' + money;

}
