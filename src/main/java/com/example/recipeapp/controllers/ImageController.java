package com.example.recipeapp.controllers;

import com.example.recipeapp.command.RecipeCommand;
import com.example.recipeapp.services.ImageService;
import com.example.recipeapp.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;


    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute(recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/imageuploadform";
    }


    @RequestMapping(value = "recipe/{id}/image", method = RequestMethod.POST)
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException{
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        byte[] byteArray = new byte[recipeCommand.getImage().length];
        int i = 0;

        for (Byte wrappetByte : recipeCommand.getImage()){
            byteArray[i++] = wrappetByte;
        }
        response.setContentType("image/jpeg");
        InputStream is =  new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }


}
