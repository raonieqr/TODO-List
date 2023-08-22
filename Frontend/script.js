document.addEventListener('DOMContentLoaded', function () {
  let btn = document.getElementById('createTask');
  let sectionAdd = document.getElementById('taskForm');
  let homeSection = document.getElementById('home');
  const actionButton = document.getElementById('actions');

  btn.addEventListener('click', function () {
    btn.classList.add = 'hiddenItem';
    homeSection.style.display = 'none';
    sectionAdd.style.display = 'flex';
    sectionAdd.scrollIntoView({ behavior: 'smooth' });
  });

  let btnSubmit = document.getElementById('addTask');
  let taskStr = localStorage.getItem('taskArray');

  let taskObj;
  if (taskStr) taskObj = JSON.parse(taskStr);
  else taskObj = [];
  let lastId = parseInt(localStorage.getItem('lastId')) || 1;

  btnSubmit.addEventListener('click', function () {
    event.preventDefault();
    let task = createTaskObj();
    if (task) {
      taskObj.push(task);
      alert('Tarefa ' + task.name + ' foi criada');
      lastId++;
      clearInputs();
      localStorage.setItem('taskArray', JSON.stringify(taskObj));
    }
  });

  function isValidDate(dateString) {
    const dateTimePattern = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
    return dateTimePattern.test(dateString);
  }

  function checkDateInput(dateString) {
    let [date, time] = dateString.split(' ');
    let [day, month, year] = date.split('/');
    let [hours, minutes] = time.split(':');

    let diff =
      new Date(year, month - 1, day, hours, minutes).getTime() - Date.now();
    if (
      parseInt(month, 10) > 12 ||
      parseInt(hours, 10) > 23 ||
      parseInt(minutes, 10) > 59
    )
      return 0;

    if (parseInt(month, 10) === 2 && parseInt(day, 10) > 29) return 0;
    else if (
      [4, 6, 9, 11].includes(parseInt(month, 10)) &&
      parseInt(day, 10) > 30
    )
      return 0;
    else if (parseInt(day, 10) > 31) return 0;
    else if (diff < 0) return 0;
    return 1;
  }

  function createTaskObj() {
    let name = document.getElementById('name').value;
    let description = document.getElementById('description').value;
    let category = document.getElementById('category').value;
    let priority = document.getElementById('priority').value;
    let status = document.getElementById('status').value;
    let dateTime = document.getElementById('dateTime').value;

    if (
      !name ||
      !description ||
      !category ||
      !priority ||
      !status ||
      !dateTime
    ) {
      alert('Error: Todos os campos devem ser preenchidos.');
      return;
    }

    if (!['todo', 'doing', 'done'].includes(status)) {
      alert('Error: Status inválido. Permitidos: todo, doing ou done.');
      return;
    }
    let regex = /^[1-5]$/;
    if (isNaN(priority) || !regex.test(priority)) {
      alert('Error: Prioridade inválida. Use um número de 1 a 5.');
      return;
    }

    if (!isValidDate(dateTime) || !checkDateInput(dateTime)) {
      alert(`Error: data ${dateTime} inválida`);
      return;
    }
    localStorage.setItem('lastId', String(lastId + 1));
    return {
      id: lastId,
      name: name,
      description: description,
      category: category,
      priority: priority,
      status: status,
      dateTime: dateTime,
    };
  }

  function clearInputs() {
    let inputs = document.querySelectorAll("input[type='text'], select");
    inputs.forEach((input) => (input.value = ''));
  }

  let btnShow = document.getElementById('showTask');
  let table = document.getElementById('tasks');
  let btnAddTaskList = document.getElementById('addTaskList');
  btnShow.addEventListener('click', function () {
    table.scrollIntoView({ behavior: 'smooth' });
    if (taskObj.length === 0) {
      alert('Lista vazia');
      return;
    } else {
      btnAddTaskList.style.display = 'inline-block';
      btnShow.style.display = 'none';
      sectionAdd.style.display = 'none';
      table.style.display = 'block';
      table.scrollIntoView({ behavior: 'smooth' });
      generateTable();
    }
  });

  function generateTable() {
    let thead = document.createElement('thead');
    let tbody = document.createElement('tbody');

    while (table.firstChild) {
      table.removeChild(table.firstChild);
    }

    let headerRow = document.createElement('tr');
    headerRow.innerHTML = `
          <th></th>
          <th>Id</th>
          <th>Nome</th>
          <th>Descrição</th>
          <th>Categoria</th>
          <th>Prioridade</th>
          <th>Status</th>
          <th>Data e Hora</th>
          <th>Ações</th>`;
    thead.appendChild(headerRow);

    for (let i = 0; i < taskObj.length; i++) {
      let task = taskObj[i];

      let row = document.createElement('tr');

      let c0 = document.createElement('td');
      let c1 = document.createElement('td');
      let c2 = document.createElement('td');
      let c3 = document.createElement('td');
      let c4 = document.createElement('td');
      let c5 = document.createElement('td');
      let c6 = document.createElement('td');
      let c7 = document.createElement('td');
      let c8 = document.createElement('td');

      c0.innerHTML = '<input type="checkbox" />';
      c1.innerText = task.id;
      c2.innerText = task.name;
      c3.innerText = task.description;
      c4.innerText = task.category;
      c5.innerText = task.priority;
      c6.innerText = task.status;
      c7.innerText = task.dateTime;
      c8.innerHTML =
        '<i id="pencil" class="ph ph-pencil-line"></i><i id="trash" class="ph ph-trash"></i>';
      row.appendChild(c0);
      row.appendChild(c1);
      row.appendChild(c2);
      row.appendChild(c3);
      row.appendChild(c4);
      row.appendChild(c5);
      row.appendChild(c6);
      row.appendChild(c7);
      row.appendChild(c8);
      tbody.appendChild(row);
      table.appendChild(thead);
      table.appendChild(tbody);

      ((index) => {
        let trashIcon = row.querySelector('#trash');
        trashIcon.addEventListener('click', function () {
          if (taskObj.length == 1) taskObj.pop();
          else taskObj.splice(index, 1);
          localStorage.removeItem('taskArray');
          localStorage.setItem('taskArray', JSON.stringify(taskObj));
          tbody.removeChild(row);
        });
      })(i);

      let pencilIcon = row.querySelector('#pencil');
      pencilIcon.addEventListener('click', function () {
        openEditModal(task, i);
      });
    }

    let checkboxes = document.querySelectorAll('input[type="checkbox"]');

    checkboxes.forEach(function (checkbox) {
      checkbox.addEventListener('change', function () {
        let checked = Array.from(checkboxes).some((boxes) => boxes.checked);
        if (checked) actionButton.style.display = 'block';
        else actionButton.style.display = 'none';
      });
    });
  }

  actionButton.addEventListener('click', function () {
    toggleModalStatus();
  });

  let btnSaveStatus = document.getElementById('btnStatusTask');

  btnSaveStatus.addEventListener('click', function () {
    let radios = document.querySelectorAll('input[type="radio"]:checked');
    let selectedRowsArray = [];

    var rows = Array.from(document.querySelectorAll('#tasks tbody tr'));
    var checkedRows = rows.filter((row) => {
      return row.querySelector('input[type="checkbox"]').checked;
    });

    selectedRowsArray = checkedRows.map((row) => {
      return Number(row.querySelector('td:nth-child(2)').textContent);
    });

    if (radios.length > 0) {
      var valueRadio = radios[0].value;
      while (selectedRowsArray.length !== 0) {
        let index = selectedRowsArray.pop();
        taskObj.forEach((task) => {
          if (task.id === index) {
            task.status = valueRadio;
          }
        });
      }
    } else alert('Error: seleciona uma opção');
    localStorage.setItem('taskArray', JSON.stringify(taskObj));
    generateTable();
    alert("Status modificado!");
    toggleModalStatus();
  });

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

  function openEditModal(task, index) {
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

    let btnEditTask = document.getElementById('btnEditTask');
    btnEditTask.addEventListener('click', editTaskHandler);

    function editTaskHandler() {
      let name = nameInput.value;
      let description = descriptionInput.value;
      let category = categoryInput.value;
      let priority = priorityInput.value;
      let status = statusInput.value;
      let dateTime = dateTimeInput.value;

      if (
        !name ||
        !description ||
        !category ||
        !priority ||
        !status ||
        !dateTime
      ) {
        alert('Error: Todos os campos devem ser preenchidos.');
        return;
      }

      if (!['todo', 'doing', 'done'].includes(status)) {
        alert('Error: Status inválido. Permitidos: todo, doing ou done.');
        return;
      }

      if (isNaN(priority) || priority < 1 || priority > 5) {
        alert('Error: Prioridade inválida. Use um número de 1 a 5.');
        return;
      }

      if (!isValidDate(dateTime) || !checkDateInput(dateTime)) {
        alert(`Error: data ${dateTime} inválida`);
        return;
      }

      taskObj[index].name = name;
      taskObj[index].description = descriptionInput.value;
      taskObj[index].category = categoryInput.value;
      taskObj[index].priority = priorityInput.value;
      taskObj[index].status = statusInput.value;
      taskObj[index].dateTime = dateTimeInput.value;

      localStorage.removeItem('taskArray');
      localStorage.setItem('taskArray', JSON.stringify(taskObj));
      generateTable();
      toggleModal();

      btnEditTask.removeEventListener('click', editTaskHandler);
    }
  }

  let closeModal = document.getElementById('closeModal');
  closeModal.addEventListener('click', function () {
    let modal = document.getElementById('editPageTask');
    if (modal.style.display === 'block') {
      toggleModal();
    }
  });

  function toggleModalStatus() {
    let editPage = document.getElementById('editStatusTask');
    let modalStyle = editPage.style.display;
    if (modalStyle === 'block') {
      table.classList.remove('modalBlur');
      editPage.style.display = 'none';
      btnAddTaskList.style.display = 'block';
    } else {
      editPage.style.display = 'block';
      table.classList.add('modalBlur');
      actionButton.style.display = 'none';
      btnAddTaskList.style.display = 'none';
    }
  }

  let closeModalStatus = document.getElementById('closeModalStatus');
  closeModalStatus.addEventListener('click', function () {
    let modal = document.getElementById('editStatusTask');
    if (modal.style.display === 'block') {
      toggleModalStatus();
    }
  });

  btnAddTaskList.addEventListener('click', function () {
    homeSection.style.display = 'none';
    btnAddTaskList.style.display = 'none';
    table.style.display = 'none';
    actionButton.style.display = 'none';
    sectionAdd.style.display = 'flex';
    sectionAdd.scrollIntoView({ behavior: 'smooth' });
    btnShow.style.display = 'block';

    btnShow.removeEventListener('click', showTaskClickHandler);

    function showTaskClickHandler() {
      btnShow.style.display = 'none';
      sectionAdd.style.display = 'none';
      btnAddTaskList.style.display = 'inline-block';
      table.style.display = 'block';

      table.scrollIntoView({ behavior: 'smooth' });
      generateTable();

      btnShow.removeEventListener('click', showTaskClickHandler);
    }

    btnShow.addEventListener('click', showTaskClickHandler);
  });
});
