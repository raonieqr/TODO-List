document.addEventListener("DOMContentLoaded", function() {
    var btn = document.getElementById("createTask");
    var formContainer = document.getElementById("taskForm");

    btn.addEventListener("click", function() {
        btn.style.display = "none";
        formContainer.style.display = "block";
        formContainer.scrollIntoView({ behavior: "smooth" });
        
    });

    var btnSubmit = document.getElementById("addTask");
    let task = {};
    let taskArray = [];
    btnSubmit.addEventListener("click", function() {
        event.preventDefault();
        
        var name = document.getElementById("name")
        var description = document.getElementById("description")
        var category = document.getElementById("category")
        var priority = document.getElementById("priority")
        var status = document.getElementById("status")
        var dateTime = document.getElementById("dateTime")
        
        task = {
            name: name.value,
            description: description.value,
            category: category.value,
            priority: priority.value,
            status: status.value,
            dateTime: dateTime.value
        }

        taskArray.push(task);
        name.value = "";
        description.value = "";
        category.value = "";
        priority.value = "";
        status.value = "";
        dateTime.value = "";
        console.log(taskArray);

    })
})