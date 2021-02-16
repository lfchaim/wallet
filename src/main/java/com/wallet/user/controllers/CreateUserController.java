package com.wallet.user.controllers;

import com.wallet.user.dto.UserDTO;
import com.wallet.user.entities.User;
import com.wallet.user.exceptions.ApiErrors;
import com.wallet.user.response.Response;
import com.wallet.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class CreateUserController {

    private UserService userService;
    private ModelMapper modelMapper;

    public CreateUserController(UserService userService, ModelMapper modelMapper){
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Response<UserDTO>> create(@RequestBody @Valid UserDTO userDTO){
        // O hash foi para o UserServiceImpl
        //userDTO.setPassword(Bcrypt.getHash(userDTO.getPassword()));
        Response<UserDTO> response = new Response<UserDTO>();

        User user = userService.save(modelMapper.map(userDTO, User.class));
        user.setPassword(""); // Para o password ficar vazio na resposta

        response.setData(modelMapper.map(user, UserDTO.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Except handler
    // MethodArgumentNotValidException: lançado toda vez que tentamos validar um objeto(@Valid do create)
    // e o objeto não está valido
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExeptions(MethodArgumentNotValidException methodArgumentNotValidException){
        // getBindingResult, resultado da validação que ocorreu ao tentar validar o objeto com o @Valid
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();

        return new ApiErrors(bindingResult);
    }

}
