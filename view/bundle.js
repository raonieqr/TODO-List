(function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
class LocalStorageManager {
    static getLastId() {
      return parseInt(localStorage.getItem('lastId')) || 1;
    }

    static getStorage(taskArray) {
        return localStorage.getItem(taskArray);
    }
  
    static incrementLastId() {
      const lastId = this.getLastId();
      localStorage.setItem('lastId', String(lastId + 1));
    }
  
    static storeTask(task) {
      const lastId = this.getLastId();
      const taskArray = JSON.parse(localStorage.getItem('taskArray')) || [];
  
      task.id = lastId;
  
      taskArray.push(task);
      
      localStorage.setItem('taskArray', JSON.stringify(taskArray));
      
      this.incrementLastId();
    }

    static storeUpdate(taskArray, taskObj) {
      localStorage.removeItem(taskArray);
      localStorage.setItem(taskArray, JSON.stringify(taskObj));
    }

  }

  module.exports = LocalStorageManager
},{}],2:[function(require,module,exports){
const LocalStorageManager = require('./LocalStorageManager.cjs');
const check = require('./validations.cjs');

document.addEventListener('DOMContentLoaded', function () {

  let btn = document.getElementById('createTask');
  let sectionAdd = document.getElementById('taskForm');
  let homeSection = document.getElementById('home');
  const actionButton = document.getElementById('actions');
  let isAddTask = false;

  function handleButtonClick() {
    btn.classList.add('hiddenItem');
    homeSection.style.display = 'none';
    sectionAdd.style.display = 'flex';
    sectionAdd.scrollIntoView({ behavior: 'smooth' });
  }

  btn.addEventListener('click', handleButtonClick);

  let btnSubmit = document.getElementById('addTask');
  let taskStr = LocalStorageManager.getStorage("taskArray");
  let taskObj = taskStr ? JSON.parse(taskStr) : [];

  function handleTaskSubmission(event) {
    event.preventDefault();
  
    let task = createTaskObj();
  
    if (task) {
      alert(`Tarefa ${task.name} foi criada`);
      taskObj.push(task);
      isAddTask = true
      sendEmail(taskObj);
      clearInputs();
    }
  
  }

  function sendEmail(tasksData) {
    const requestData = { content: JSON.stringify(tasksData, null, 2) };

    fetch('http://localhost:3000/enviar-email', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestData)
    })
      .then(response => response.text())
      .then(data => {
        console.log(data);
      })
      .catch(error => {
        console.error('Erro ao agendar e-mail:', error);
      });
  }
  
    
  btnSubmit.addEventListener('click', handleTaskSubmission);
  if (isAddTask) {
    LocalStorageManager.storeTask(taskObj);
    isAddTask = false;
  }


  function createTaskObj() {
    const fields = ['name', 'description', 'category', 'priority', 'status', 'dateTime'];
    const inputValues = {};

    for (const field of fields) {
      inputValues[field] = document.getElementById(field).value;

      if (!check.isNotEmpty(inputValues[field])) {
        alert('Error: Todos os campos devem ser preenchidos.');

        return null;
      }
    }

    if (!check.isValidStatus(inputValues.status)) {
      alert('Error: Status inválido. Permitidos: todo, doing ou done.');

      return null;
    }

    if (!check.isValidPriority(inputValues.priority)) {
      alert('Error: Prioridade inválida. Use um número de 1 a 5.');

      return null;
    }

    if (!check.isValidDate(inputValues.dateTime) || !check.isDateInput(inputValues.dateTime)) {
      alert(`Error: data ${inputValues.dateTime} inválida`);

      return null;
    }

    LocalStorageManager.storeTask(inputValues);

    return inputValues;
  }

  function clearInputs() {
    let inputs = document.querySelectorAll("input[type='text'], select");
    inputs.forEach((input) => (input.value = ''));
  }

  let btnShow = document.getElementById('showTask');
  let table = document.getElementById('tasks');
  let btnAddTaskList = document.getElementById('addTaskList');
  
  function showTaskList() {
    if (taskObj.length <= 0)
        alert('Lista vazia');
    else {
      btnAddTaskList.style.display = 'inline-block';
      btnShow.style.display = 'none';
      sectionAdd.style.display = 'none';
      table.style.display = 'block';
      table.scrollIntoView({ behavior: 'smooth' });
      generateTable();
    }
  }

  btnShow.addEventListener('click', showTaskList);

  function createTableCell(text){
    let cell = document.createElement('td');
  
    cell.innerHTML = text;
  
    return cell;
  }
  
  function generateTableRow(cells) {
    let row = document.createElement('tr');
  
    cells.forEach((cell) => {
  
      row.appendChild(cell);
    });
  
    return row;
  }

  function createTableHeaderRow() {
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `
      <th></th>
      <th>Id</th>
      <th>Nome</th>
      <th>Descrição</th>
      <th>Categoria</th>
      <th>Prioridade</th>
      <th>Status</th>
      <th>Data e Hora</th>
      <th>Ações</th>
    `;
    return headerRow;
  }

  function handleDeleteTask(index, row) {
    if (taskObj.length === 1) {
      taskObj.pop();

      actionButton.style.display = 'none';
    } 
    else
      taskObj.splice(index, 1);
    
    LocalStorageManager.storeUpdate("taskArray", taskObj);
    
    row.parentNode.removeChild(row);
  
    let checkedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (checkedCheckboxes.length === 0) 
      actionButton.style.display = 'none';
  }

  function createTaskRow(task) {
    let c0 = createTableCell('<input type="checkbox" />');
    let c1 = createTableCell(task.id);
    let c2 = createTableCell(task.name);
    let c3 = createTableCell(task.description);
    let c4 = createTableCell(task.category);
    let c5 = createTableCell(task.priority);
    let c6 = createTableCell(task.status);
    let c7 = createTableCell(task.dateTime);
    let c8 = createTableCell('<i id="pencil" class="ph ph-pencil-line"></i>'
     + '<i id="trash" class="ph ph-trash"></i>');
  
    return generateTableRow([c0, c1, c2, c3, c4, c5, c6, c7, c8]);
  }

  function handleCheckboxChange(checkboxes) {
    checkboxes.forEach(function (checkbox) {
      checkbox.addEventListener('change', function () {
        let checked = Array.from(checkboxes).some((boxes) => boxes.checked);
        if (checked) actionButton.style.display = 'block';
        else actionButton.style.display = 'none';
      });
    });
  }

  var indexEdit = 0;

  function generateTable() {
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    while (table.firstChild)
      table.removeChild(table.firstChild);

    let headerRow = createTableHeaderRow();
      
    thead.appendChild(headerRow);

    for (let i = 0; i < taskObj.length; i++) {

      let task = taskObj[i];

      let row = createTaskRow(task);

      tbody.appendChild(row);
      table.appendChild(thead);
      table.appendChild(tbody);

      let trashIcon = row.querySelector('#trash');
      trashIcon.addEventListener('click', function () {
        handleDeleteTask(i, row);
      });

      let pencilIcon = row.querySelector('#pencil');
      pencilIcon.addEventListener('click', function () {
        indexEdit = i;
        openEditModal(task);
      });
    }

    let checkboxes = document.querySelectorAll('input[type="checkbox"]');

    handleCheckboxChange(checkboxes);

  }

  actionButton.addEventListener('click', function () {
    toggleModalStatus();
  });
 

  let btnSaveStatus = document.getElementById('btnStatusTask');

  function getSelectedRowIdsFromTable() {
    let rows = Array.from(document.querySelectorAll('#tasks tbody tr'));
    let checkedRows = rows.filter((row) => {
      return row.querySelector('input[type="checkbox"]').checked;
    });

    selectedRowsArray = checkedRows.map((row) => {
      return Number(row.querySelector('td:nth-child(2)').textContent);
    });

    return selectedRowsArray;
  }

  function updateStatusForSelectedRows() {
    let radios = document.querySelectorAll('input[type="radio"]:checked');
    let selectedRowsArray = [];

    selectedRowsArray = getSelectedRowIdsFromTable();

    if (radios.length > 0) {
      var valueRadio = radios[0].value;

      while (selectedRowsArray.length !== 0) {
        let index = selectedRowsArray.pop();

        taskObj.forEach((task) => {
        
          if (task.id === index)
            task.status = valueRadio;
        });
      }
    } 
    else {
      alert('Error: selecione uma opção');
      return;
    }

    LocalStorageManager.storeUpdate("taskArray", taskObj);

    generateTable();

    alert("Status modificado!");
    
    toggleModalStatus();
  }

  btnSaveStatus.addEventListener('click', updateStatusForSelectedRows);

  function toggleModal() {
    let editPage = document.getElementById('editPageTask');
    let modalStyle = editPage.style.display;

    if (modalStyle === 'block') {
      btnAddTaskList.style.display = 'block';
      table.classList.remove('modalBlur');
      editPage.style.display = 'none';
    } else {
      editPage.style.display = 'block';
      btnAddTaskList.style.display = 'none';
      table.classList.add('modalBlur');
    }
  }

  function openEditModal(task) {
    toggleModal();

    let nameInput = document.getElementById('nameEdit');
    let descriptionInput = document.getElementById('descriptionEdit');
    let categoryInput = document.getElementById('categoryEdit');
    let priorityInput = document.getElementById('priorityEdit');
    let statusInput = document.getElementById('statusEdit');
    let dateTimeInput = document.getElementById('dateTimeEdit');

    nameInput.value = task.name;
    descriptionInput.value = task.description;
    categoryInput.value = task.category;
    priorityInput.value = task.priority;
    statusInput.value = task.status;
    dateTimeInput.value = task.dateTime;

  }


  btnEditTask.addEventListener('click', () => saveEditTask());

  function saveEditTask() {
    editTaskHandler(indexEdit);
  }

  function editTaskHandler(index) {
    const taskInputs = getTaskInputs();
  
    if (!check.validateFields(taskInputs) ||
        !check.isValidStatus(taskInputs.status) ||
        !check.isValidPriority(taskInputs.priority) ||
        !check.isValidDateTime(taskInputs.dateTime)) {
      return;
    }
  
    if (updateTask(index, taskInputs)) {
      generateTable();
      alert(`A tarefa ${taskObj[index].name} foi editada!`);
      toggleModal();
    }
  }
  
  function getTaskInputs() {
    const nameInput = document.getElementById('nameEdit');
    const descriptionInput = document.getElementById('descriptionEdit');
    const categoryInput = document.getElementById('categoryEdit');
    const priorityInput = document.getElementById('priorityEdit');
    const statusInput = document.getElementById('statusEdit');
    const dateTimeInput = document.getElementById('dateTimeEdit');
  
    return {
      name: nameInput.value,
      description: descriptionInput.value,
      category: categoryInput.value,
      priority: priorityInput.value,
      status: statusInput.value,
      dateTime: dateTimeInput.value
    };
  }
  
  function updateTask(index, taskInputs) {
    if (typeof taskObj[index] !== "undefined") {
      taskObj[index].name = taskInputs.name;
      taskObj[index].description = taskInputs.description;
      taskObj[index].category = taskInputs.category;
      taskObj[index].priority = taskInputs.priority;
      taskObj[index].status = taskInputs.status;
      taskObj[index].dateTime = taskInputs.dateTime;
  
      LocalStorageManager.storeUpdate("taskArray", taskObj);
  
      return true;
    }
    return false;
  }

  let closeModal = document.getElementById('closeModal');
  closeModal.addEventListener('click', function () {
      toggleModal();
  });


  function clearCheckboxes() {
    let checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach((checkbox) => (checkbox.checked = false));
  }

  function clearRadios() {
    let radios = document.querySelectorAll('input[type="radio"]');
    radios.forEach((radio) => (radio.checked = false));
  }

  function toggleModalStatus() {
    let editPage = document.getElementById('editStatusTask');
    let modalStyle = editPage.style.display;

    if (modalStyle === 'block') {
      table.classList.remove('modalBlur');

      editPage.style.display = 'none';
      btnAddTaskList.style.display = 'block';

      clearCheckboxes();
      clearRadios();

    } 
    else {
      editPage.style.display = 'block';

      table.classList.add('modalBlur');

      actionButton.style.display = 'none';
      btnAddTaskList.style.display = 'none';
    }
  }

  let closeModalStatus = document.getElementById('closeModalStatus');
  closeModalStatus.addEventListener('click', function () {
      toggleModalStatus();

      clearRadios();
  });

  function showTaskForm() {
    homeSection.style.display = 'none';
    btnAddTaskList.style.display = 'none';
    table.style.display = 'none';
    actionButton.style.display = 'none';
    sectionAdd.style.display = 'flex';
    sectionAdd.scrollIntoView({ behavior: 'smooth' });
    btnShow.style.display = 'block';

    btnShow.removeEventListener('click', showTaskClickHandler);
  }

  btnAddTaskList.addEventListener('click', showTaskForm);

  function showTaskClickHandler() {
    btnShow.style.display = 'none';
    sectionAdd.style.display = 'none';
    btnAddTaskList.style.display = 'inline-block';
    table.style.display = 'block';
  
    table.scrollIntoView({ behavior: 'smooth' });
    generateTable();
  
    btnShow.removeEventListener('click', showTaskClickHandler);
  }
  
});

},{"./LocalStorageManager.cjs":1,"./validations.cjs":3}],3:[function(require,module,exports){

   function isValidDate(dateString) {
    const dateTimePattern = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
    return dateTimePattern.test(dateString);
  }

   function isDateInput(dateString) {
    const [date, time] = dateString.split(' ');
    const [day, month, year] = date.split('/');
    const [hours, minutes] = time.split(':');
    const parsedMonth = parseInt(month, 10);
    const parsedHours = parseInt(hours, 10);
    const parsedMinutes = parseInt(minutes, 10);
  
    const diff = new Date(year, parsedMonth - 1, day,
       parsedHours, parsedMinutes).getTime() - Date.now();
  
    if (
      isInvalidMonth(parsedMonth) ||
      isInvalidTime(parsedHours, parsedMinutes) ||
      isInvalidDay(parsedMonth, day) ||
      isNegativeDiff(diff)
    )
      return 0;

    return 1;
  }
  
   function isInvalidMonth(month) {
    return month > 12;
  }
  
   function isInvalidTime(hours, minutes) {
    return hours > 23 || minutes > 59;
  }
  
   function isInvalidDay(month, day) {
    return (month === 2 && day > 29) || ([4, 6, 9, 11].includes(month) && day > 30) || day > 31;
  }
  
   function isNegativeDiff(diff) {
    return diff < 0;
  }

   function isNotEmpty(value) {
    return value.trim() !== '';
  }

   function isValidStatus(status) {
    const validStatus = ['todo', 'doing', 'done'];
    return validStatus.includes(status);
  }

   function isValidPriority(priority) {
    const priorityRegex = /^[1-5]$/;
    return priorityRegex.test(priority);
  }

   function validateFields(taskInputs) {
    const { name, description, category, priority, status, dateTime } = taskInputs;
    if (!name || !description || !category || !priority || !status || !dateTime) {
      alert('Erro: Todos os campos devem ser preenchidos.');
      return false;
    }
    return true;
  }
  
   function isValidDateTime(dateTime) {
    if (!isValidDate(dateTime) || !isDateInput(dateTime)) {
      alert(`Erro: Data ${dateTime} inválida`);
      return false;
    }
    return true;
  }

  module.exports = {isValidDate, isDateInput, isInvalidMonth, isInvalidTime, isInvalidDay, isNegativeDiff, isNotEmpty, isValidStatus, isValidPriority, validateFields, isValidDateTime}
},{}]},{},[2]);