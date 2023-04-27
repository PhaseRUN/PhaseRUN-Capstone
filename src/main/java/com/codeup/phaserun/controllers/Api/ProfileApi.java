package com.codeup.phaserun.controllers.Api;

import com.codeup.phaserun.models.User;
import com.codeup.phaserun.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileApi {

    private final UserRepository userDao;

    public ProfileApi(UserRepository userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/upload-picture")
    public ExampleData uploadProfilePicture(@RequestBody ExampleData requestData){
        System.out.println("Inside exampledata");
        Long userId = requestData.userId;
        System.out.println(userId);
        System.out.println(userId);
        User user = userDao.findById(userId).get();
        System.out.println(user);
        user.setProfilePic(requestData.profilePicURL);
        userDao.save(user);
        return requestData;
    }



    public static class ExampleData {
        private String profilePicURL;

        private Long userId;


        public String getProfilePicURL() {
            return profilePicURL;
        }

        public void setProfilePicURL(String profilePicURL) {
            this.profilePicURL = profilePicURL;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

    }





}
