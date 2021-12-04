package org.closure.course.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

@Configuration
public class FirebaseConfig {
    FirebaseApp app;

    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:google-services.json");
        InputStream serviceAccount = new FileInputStream(file);
        FirebaseOptions.Builder builder = FirebaseOptions.builder();
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = builder.setCredentials(googleCredentials).build();
        app = FirebaseApp.initializeApp(options);
    }

    @Bean
    public Firestore initFireStore() throws IOException {

        return FirestoreClient.getFirestore();
    }

    @Bean
    public FirebaseMessaging initFirebaseMessaging() {
        return FirebaseMessaging.getInstance();
    }
}
