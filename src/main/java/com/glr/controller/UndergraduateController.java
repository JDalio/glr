package com.glr.controller;

import com.glr.model.Undergraduate;
import com.glr.repository.UndergraduateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/undergraduate")
public class UndergraduateController
{
    @Autowired
    private UndergraduateRepo undergraduateRepo;

    @GetMapping(path = "/test")
    public void test()
    {
        Undergraduate undergraduate = new Undergraduate();

        undergraduate.setSchool("bupt");
        undergraduate.setAuth(true);
        undergraduateRepo.save(undergraduate);
    }


}
