document.addEventListener("DOMContentLoaded", function() {
    let btn = document.getElementById("createTask");
    let sectionAdd = document.getElementById("taskForm");
    let homeSection = document.getElementById("home");

    btn.addEventListener("click", function() {
        btn.classList.add = "hiddenItem";
        homeSection.style.display = "none";
        sectionAdd.style.display = "flex";
        sectionAdd.scrollIntoView({ behavior: "smooth" });
    });

    let btnSubmit = document.getElementById("addTask");
    let taskStr = localStorage.getItem('taskArray');
    let taskObj;
    if (taskStr) 
        taskObj = JSON.parse(taskStr);
    else
        taskObj = [];
    let id = 1;

    btnSubmit.addEventListener("click", function() {
        event.preventDefault();
        let task = createTaskObj();
        if (task) {
            taskObj.push(task);
            alert("Tarefa " + task.name + " foi criada");
            id++;
            clearInputs();
            localStorage.setItem('taskArray',JSON.stringify(taskArray));
        }
    })

    function isValidDate(dateString) {
        const dateTimePattern = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
        return dateTimePattern.test(dateString);
    }

    function checkDateInput(dateString) {
        let [date, time] = dateString.split(" ");
        let [day, month, year] = date.split("/");
        let [hours, minutes] = time.split(":");

        let diff = new Date(year, month - 1, day, hours, minutes).getTime() - Date.now();
        if (parseInt(month, 10) > 12 || parseInt(hours, 10) > 23 || parseInt(minutes, 10) > 59)
            return 0;
    
        if (parseInt(month, 10) === 2 && parseInt(day, 10) > 29)
            return 0;
        else if ([4, 6, 9, 11].includes(parseInt(month, 10)) && parseInt(day, 10) > 30) 
            return 0;
        else if (parseInt(day, 10) > 31)
            return 0;
        else if (diff < 0)
            return 0;
        return 1;
    }

    function createTaskObj() {
        let name = document.getElementById("name").value;
        let description = document.getElementById("description").value;
        let category = document.getElementById("category").value;
        let priority = document.getElementById("priority").value;
        let status = document.getElementById("status").value;
        let dateTime = document.getElementById("dateTime").value;
        
        if (!name || !description || !category || !priority || !status || !dateTime) {
            alert("Error: Todos os campos devem ser preenchidos.");
            return;
        }
    
        if (!["todo", "doing", "done"].includes(status)) {
            alert("Error: Status inválido. Permitidos: todo, doing ou done.");
            return;
        }
        let regex = /^[1-5]$/;
        if (isNaN(priority) || !regex.test(priority)) {
            alert("Error: Prioridade inválida. Use um número de 1 a 5.");
            return;
        }
    
        if (!isValidDate(dateTime) || !checkDateInput(dateTime)) {
            alert(`Error: data ${dateTime} inválida`);
            return;
        }


        return {
            id: id,
            name: name,
            description: description,
            category: category,
            priority: priority,
            status: status,
            dateTime: dateTime
        };
    }

    function clearInputs() {
        let inputs = document.querySelectorAll("input[type='text'], select");
        inputs.forEach(input => (input.value = ""));
    }

    let btnShow = document.getElementById("showTask");
    let table = document.getElementById("tasks");
    let btnAddTaskList = document.getElementById("addTaskList")
    btnShow.addEventListener("click", function() {
        table.scrollIntoView({ behavior: "smooth" });
        if (taskArray.length === 0) {
            alert("Lista vazia");
            return;
        } else {
            btnAddTaskList.style.display = "inline-block";
            btnShow.style.display = "none";
            sectionAdd.style.display = "none";
            table.style.display = "block";
            table.scrollIntoView({ behavior: "smooth" });
            generateTable(taskArray);
        }
    })

    function generateTable(tasks) {
        let thead= document.createElement("thead");
        let tbody = document.createElement("tbody");


        while (table.firstChild) {
            table.removeChild(table.firstChild);
        }

        let headerRow = document.createElement("tr");
        headerRow.innerHTML = `
            <th>Id</th><th>Nome</th><th>Descrição</th><th>Categoria</th><th>Prioridade
            </th><th>Status</th><th>Data e Hora</th><th>Ações</th>`;
        thead.appendChild(headerRow)

        for (let i = 0; i < taskArray.length; i++) {
            let task = taskArray[i];
            
            let row = document.createElement("tr");
    
            let c1 = document.createElement("td");
            let c2 = document.createElement("td");
            let c3 = document.createElement("td");
            let c4 = document.createElement("td");
            let c5 = document.createElement("td");
            let c6 = document.createElement("td");
            let c7 = document.createElement("td");
            let c8 = document.createElement("td");
    
            c1.innerText = task.id;
            c2.innerText = task.name;
            c3.innerText = task.description;
            c4.innerText = task.category; 
            c5.innerText = task.priority; 
            c6.innerText = task.status; 
            c7.innerText = task.dateTime;
            c8.innerHTML = `<i id="pencil" class=\"ph ph-pencil-line\"></i>
            <i id="trash" class=\"ph ph-trash\"></i>`;
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
                let trashIcon = row.querySelector("#trash");
                trashIcon.addEventListener("click", function() {
                    taskArray.splice(index, 1);
                    if (taskArray.length == 1)
                        taskArray.pop();
                    tbody.removeChild(row);
                });
            })(i);
            
            let pencilIcon = row.querySelector("#pencil");
            pencilIcon.addEventListener("click", function() {
                openEditModal(task, i);
        }); 

    }
    
}

    function toggleModal() {
        let editPage = document.getElementById("editPageTask");
        let modalStyle = editPage.style.display;

        if (modalStyle === "block") {
            btnAddTaskList.style.display = "block";
            table.classList.remove("modalBlur");
            editPage.style.display = "none";
        } else {
            editPage.style.display = "block";
            btnAddTaskList.style.display = "none";
            table.classList.add("modalBlur");
        }

    }

    function openEditModal(task, index) {
        toggleModal();
        let nameInput = document.getElementById("nameEdit");
        let descriptionInput = document.getElementById("descriptionEdit");
        let categoryInput = document.getElementById("categoryEdit");
        let priorityInput = document.getElementById("priorityEdit");
        let statusInput = document.getElementById("statusEdit");
        let dateTimeInput = document.getElementById("dateTimeEdit");

        nameInput.value = task.name;
        descriptionInput.value = task.description;
        categoryInput.value = task.category;
        priorityInput.value = task.priority;
        statusInput.value = task.status;
        dateTimeInput.value = task.dateTime;

        let btnEditTask = document.getElementById("btnEditTask");
        btnEditTask.addEventListener("click", editTaskHandler);

        function editTaskHandler() {
            let name = nameInput.value;
            let description = descriptionInput.value;
            let category = categoryInput.value;
            let priority = priorityInput.value;
            let status = statusInput.value;
            let dateTime = dateTimeInput.value; 

            if (!name || !description || !category || !priority || !status || !dateTime) {
                alert("Error: Todos os campos devem ser preenchidos.");
                return;
            }
        
            if (!["todo", "doing", "done"].includes(status)) {
                alert("Error: Status inválido. Permitidos: todo, doing ou done.");
                return;
            }
        
            if (isNaN(priority) || priority < 1 || priority > 5) {
                alert("Error: Prioridade inválida. Use um número de 1 a 5.");
                return;
            }
        
            if (!isValidDate(dateTime) || !checkDateInput(dateTime)) {
                alert(`Error: data ${dateTime} inválida`);
                return;
            }    

            taskArray[index].name = name;
            taskArray[index].description = descriptionInput.value;
            taskArray[index].category = categoryInput.value;
            taskArray[index].priority = priorityInput.value;
            taskArray[index].status = statusInput.value;
            taskArray[index].dateTime = dateTimeInput.value;

            generateTable(taskArray);
            toggleModal();

            btnEditTask.removeEventListener("click", editTaskHandler);
        }

    }


    let closeModal = document.getElementById("closeModal");
    closeModal.addEventListener("click", function() {
        let modal = document.getElementById("editPageTask");
        if (modal.style.display === "block") {
            toggleModal();
        }
    });

    btnAddTaskList.addEventListener("click", function() {
        homeSection.style.display = "none";
            btnAddTaskList.style.display = "none";
            table.style.display = "none";
            sectionAdd.style.display = "flex";
            sectionAdd.scrollIntoView({ behavior: "smooth" });
            btnShow.style.display = "block";
        
            btnShow.removeEventListener("click", showTaskClickHandler);
        
            function showTaskClickHandler() {
                btnShow.style.display = "none";
                sectionAdd.style.display = "none";
                btnAddTaskList.style.display = "inline-block";
                table.style.display = "block";
        
                table.scrollIntoView({ behavior: "smooth" });
                generateTable(taskArray);
        
                btnShow.removeEventListener("click", showTaskClickHandler);
            }
        
            btnShow.addEventListener("click", showTaskClickHandler);
    });
});