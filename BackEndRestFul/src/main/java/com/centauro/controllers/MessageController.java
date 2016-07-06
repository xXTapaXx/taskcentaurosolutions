package com.centauro.controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.portable.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.centauro.exception.ShopNotFound;
import com.centauro.model.ListModel;
import com.centauro.model.SharedModel;
import com.centauro.model.TaskModel;
import com.centauro.service.ListService;
import com.centauro.service.SharedService;
import com.centauro.service.TaskService;
import com.centauro.view.ListModelView;
import com.centauro.view.SharedModelView;
import com.centauro.view.SharedView;
import com.centauro.view.TaskListView;
import com.centauro.view.TaskModelView;
import com.centauro.view.TaskView;
import com.google.api.services.tasks.model.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class MessageController {
	
	
}
