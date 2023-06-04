<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Редактирование автомобиля</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand navbar-dark bg-dark">
  <div class="container">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="/add">Добавить</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/read">Читать</a>
      </li>
    </ul>
  </div>
</nav>
<div class="container">
  <h1>Редактирование автомобиля</h1>
  <form action="/edit" method="POST">
    <div class="mb-3">
      <label for="name" class="form-label">Модель</label>
      <input type="text" class="form-control" id="name" name="name" readonly>
    </div>
    <div class="mb-3">
      <label for="brand" class="form-label">Марка</label>
      <input type="text" class="form-control" id="brand" name="brand" required>
    </div>
    <div class="mb-3">
      <label for="color" class="form-label">Цвет</label>
      <input type="text" class="form-control" id="color" name="color" required>
    </div>
    <div class="mb-3">
      <label for="year" class="form-label">Год выпуска</label>
      <input type="number" class="form-control" id="year" name="year" required>
    </div>
    <div class="mb-3">
      <label for="mileage" class="form-label">Пробег</label>
      <input type="number" class="form-control" id="mileage" name="mileage" required>
    </div>
    <button type="submit" class="btn btn-primary">Сохранить</button>
  </form>
</div>

<script>
  // Получение параметров URL для заполнения формы
  const urlParams = new URLSearchParams(window.location.search);
  const modelName = urlParams.get('name');
  document.getElementById("name").value = modelName;
</script>
</body>
</html>
