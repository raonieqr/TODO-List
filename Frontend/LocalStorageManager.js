export class LocalStorageManager {
  static getLastId() { return parseInt(localStorage.getItem('lastId')) || 1; }

  static getStorage(taskArray) { return localStorage.getItem(taskArray); }

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