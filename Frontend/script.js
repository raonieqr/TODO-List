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
    let taskArray = [];
    let id = 1;

    btnSubmit.addEventListener("click", function() {
        event.preventDefault();
        let task = createTaskObj();
        console.log(task.dateTime);
        taskArray.push(task);
        alert("Tarefa " + task.name + " foi criada");
        id++;
        clearInputs();
    })

    function isValidDate(dateString) {
        const dateTimePattern = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
        let [date, time] = dateString.split(" ");
        let [day, month, year] = date.split("/");
        let [hours, minutes] = time.split(":");
        if (parseInt(day, 10) > 30 || parseInt(month, 10) > 12 || parseInt(hours, 10) > 23 || parseInt(minutes, 10) > 59) {
            alert("Error: datetime inválido");
            return 0;
        }
        return dateTimePattern.test(dateString);
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
    
        if (isNaN(priority) || priority < 1 || priority > 5) {
            alert("Error: Prioridade inválida. Use um número de 1 a 5.");
            return;
        }
    
        if (!isValidDate(dateTime)) {
            try {
                let [date, time] = dateTime.split(" ");
                let [day, month, year] = date.split("/");s
                let [hours, minutes] = time.split(":");

                let monthTask = parseInt(month, 10) - 1;
                dateTime = new Date(year, monthTask, day, hours, minutes);
            } catch (error) {
                alert("Error: Formato de data inválido. Use o formato DD-MM-YYYY HH:mm.");
            }
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
        btnShow.style.display = "none";
        sectionAdd.style.display = "none";
        btnAddTaskList.style.display = "inline-block";
        table.style.display = "block";

        table.scrollIntoView({ behavior: "smooth" });
        generateTable(taskArray);
    })

    function generateTable(tasks) {
        let tbody = document.createElement("tbody");
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
            table.appendChild(tbody);
            
            ((index) => {
                let trashIcon = row.querySelector("#trash");
                trashIcon.addEventListener("click", function() {
                    taskArray.splice(index, 1);
                    tbody.removeChild(row);
                    console.log(taskArray.length)
                });
            })(i);

        }
    }
})