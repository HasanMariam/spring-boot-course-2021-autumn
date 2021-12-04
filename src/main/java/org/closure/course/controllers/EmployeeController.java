package org.closure.course.controllers;

import java.security.Principal;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.messaging.FcmOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import org.closure.course.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    private final static String  token = "eFyEipJeT_KeWspx-Peuwj:APA91bH3kdBJGOPhMkpytFvpZPyL-n3TaE5Xe5_-jlt2_lul1SliUEYvy1UN6-KFuHxIuBit75Z8DcabyhNLn8w6H0FQOvNmvXmBayPWg9z51_dqaUf2L1U367orhDUwSvzptRY0foOk";
    @Autowired
    Firestore firestore;

    @Autowired
    FirebaseMessaging firebaseMessaging;

    @RequestMapping(path = { "/add" },method = RequestMethod.POST)
    public String welcomePage(@RequestBody Employee employee) throws InterruptedException, ExecutionException, FirebaseMessagingException {
        Integer id = new Random().nextInt(10000);
        DocumentReference docRef = firestore.collection("user").document(id.toString());
        ApiFuture<WriteResult>  result = docRef.set(employee);
        Notification notification = Notification
                .builder()
                .setTitle("note.getSubject()")
                .setBody("note.getContent()")
                .build();
        Message message = Message
                .builder()
                .setToken(token)
                .setNotification(notification)
                .build();

         firebaseMessaging.send(message);
        return result.get().toString();
       
    }
}