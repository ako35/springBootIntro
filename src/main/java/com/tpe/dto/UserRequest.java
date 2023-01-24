package com.tpe.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tpe.domain.Role;
import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Isim bilgisini giriniz ")
    private String firstName;

    @NotBlank(message = "Soy isim bilgisini giriniz ")
    private String lastName;

    @NotBlank(message = "Please provide userName ")
    @Size(min = 5, max = 10, message = "Please provide a user name min=5, max=10 chars long")
    private String userName;

    @NotBlank(message = "Sifre bilgisini giriniz ")
    private String password;



}
