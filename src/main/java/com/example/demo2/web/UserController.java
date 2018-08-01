package com.example.demo2.web;

import com.example.demo2.entity.User;
import com.example.demo2.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository mUserRepository;

    @RequestMapping(value = "/user/find")
    public String xxx(@RequestParam String name) {
        if (mUserRepository.findByName(name) == null) {
            System.out.print("null");
            return "xxxx";
        }
        return mUserRepository.findByName(name).getAge() + "";
    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public void addUser(@RequestParam String name, @RequestParam int age) {
        mUserRepository.save(new User(name, age));
    }


}
