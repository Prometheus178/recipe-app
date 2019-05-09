package com.example.recipeapp.command;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class NotesCommand {

    private Long id;
//    private Recipe recipe;
    private String recipeNotes;
}
