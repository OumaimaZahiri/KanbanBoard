<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
    <link rel="stylesheet" href="./index.css">
    <title>Kanban Board</title>
</head>
<body>
    <div class="container">
        <div class="kanban-heading">
            <strong class="kanban-heading-text">Kanban Board</strong>
        </div>
        <div class="kanban-board">
            <div class="kanban-block" id="todo">
                <strong>To Do</strong>
                <div class="task-button-block">
        <button id="task-button">Create new task</button>
    </div>
    <div class="task" id="task1">
        <span>Task 1</span>
    </div>
            </div>
            <div class="kanban-block" id="inprogress">
                <strong>In Progress</strong>
            </div>
            <div class="kanban-block" id="done">
                <strong>Done</strong>
            </div>
        </div>
    </div>
</body>
</html>
