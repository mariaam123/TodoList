package org.epam.webinar.TodoList.rest.v1; //package we start froom -- root

import org.epam.webinar.TodoList.rest.v1.request.TodoRequest;
import org.epam.webinar.TodoList.rest.v1.response.TodoResponse;
import org.epam.webinar.TodoList.todo.TodoEntity;
import org.epam.webinar.TodoList.todo.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;  //any resource we want to

import java.util.ArrayList;
import java.util.List;
// use and when its in different package

@Controller  //annotation metadata we announce data to class variable or something else
//controller is a handler when some data is sent from the browser this data is called
//writing url in search browser will call it browser and backend communicates wiht http protocol
//it contains data which is request body data we sent ; type of the data jason structure how we int
//roduce data


@RequestMapping("/todo")  //annotation  whenewer someone calls todo sent the srecuset
public class TodoListController {
    private  TodoRepository todoRepository;

    public TodoListController(TodoRepository repository){
        todoRepository = repository;

    }




    @GetMapping  //created new function of class and introduced annotation which tells the browser whenever
    //retrieving data from backend system it uses database to get some data
    public String loadTodoList(ModelMap modelMap){

        List<TodoEntity> todoEntities = todoRepository.findAll();
        List<TodoResponse> todoResponseList = new ArrayList<>();

        for(TodoEntity entity:todoEntities){
            TodoResponse todoResponse = new TodoResponse();
            todoResponse.setDescription(entity.getDescription());

            todoResponseList.add(todoResponse);
        }

        
        modelMap.addAttribute("todos",todoResponseList);
        modelMap.addAttribute("newTodo", new TodoRequest());

        return "todo";


    }


    @PostMapping("/addTodo")
    public String addNewTodo(@ModelAttribute TodoRequest request){

        TodoEntity todo = new TodoEntity();
        todo.setDescription(request.getDescription());
        todo.setStatus("created");

        todoRepository.save(todo);

        return "redirect:/todo";


    }






}
