
function isValidDate(dateString) {
    const dateTimePattern = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
    return dateTimePattern.test(dateString);
  }

  function checkDateInput(dateString) {
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
  
  function isValidStatus(status) {
    if (!['todo', 'doing', 'done'].includes(status)) {
      alert('Erro: Status inválido. Permitidos: todo, doing ou done.');
      return false;
    }
    return true;
  }
  
  function isValidPriority(priority) {
    const numericPriority = Number(priority);
    if (isNaN(numericPriority) || numericPriority < 1 || numericPriority > 5) {
      alert('Erro: Prioridade inválida. Use um número de 1 a 5.');
      return false;
    }
    return true;
  }
  
  function isValidDateTime(dateTime) {
    if (!isValidDate(dateTime) || !checkDateInput(dateTime)) {
      alert(`Erro: Data ${dateTime} inválida`);
      return false;
    }
    return true;
  }