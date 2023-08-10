document.addEventListener("DOMContentLoaded", function() {
    let btn = document.getElementById("createTask");
    let sectionAdd = document.getElementById("taskForm");
    let homeSection = document.getElementById("home")
    btn.addEventListener("click", function() {
        btn.classList.add = "hiddenItem";
        homeSection.style.display = "none";
        sectionAdd.style.display = "flex";
        sectionAdd.scrollIntoView({ behavior: "smooth" });
        
    });

    let btnSubmit = document.getElementById("addTask");
    let task = {};
    let taskArray = [];
    let id = 1;
    btnSubmit.addEventListener("click", function() {
        event.preventDefault();
        let name = document.getElementById("name")
        let description = document.getElementById("description")
        let category = document.getElementById("category")
        let priority = document.getElementById("priority")
        let status = document.getElementById("status")
        let dateTime = document.getElementById("dateTime")
        
        task = {
            id: id,
            name: name.value,
            description: description.value,
            category: category.value,
            priority: priority.value,
            status: status.value,
            dateTime: dateTime.value
        }
        
        taskArray.push(task);
        alert("Tarefa " + task.name + " foi criada");
        id++;
        name.value = "";
        description.value = "";
        category.value = "";
        priority.value = "";
        status.value = "";
        dateTime.value = "";
        console.log(taskArray);

    })

    let btnShow = document.getElementById("showTask");
    let table = document.getElementById("tasks");
    let btnAddTaskList = document.getElementById("addTaskList")
    btnShow.addEventListener("click", function() {
        btnShow.style.display = "none";
        sectionAdd.style.display = "none";
        btnAddTaskList.style.display = "inline-block";
        table.style.display = "block";

        table.scrollIntoView({ behavior: "smooth" });
    
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
            let c9 = document.createElement("td");

    
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
    })
})