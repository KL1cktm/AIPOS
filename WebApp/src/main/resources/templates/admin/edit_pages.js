function openTab(evt, tabName) {
    // Скрыть все вкладки
    let tabcontent = document.getElementsByClassName("tabcontent");
    for (let i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Убрать активный класс у всех кнопок
    let tablinks = document.getElementsByClassName("tablinks");
    for (let i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Показать текущую вкладку и добавить к ней класс 'active'
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}
