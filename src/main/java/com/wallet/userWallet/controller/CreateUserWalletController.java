package com.wallet.userWallet.controller;

import com.wallet.user.entities.User;
import com.wallet.user.exceptions.ApiErrors;
import com.wallet.user.response.Response;
import com.wallet.userWallet.converter.ConverterDTOToEntity;
import com.wallet.userWallet.converter.ConverterEntityToDTO;
import com.wallet.userWallet.dto.UserWalletDTO;
import com.wallet.userWallet.entities.UserWallet;
import com.wallet.userWallet.services.UserWalletService;
import com.wallet.wallet.entities.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user-wallet")
public class CreateUserWalletController {

    private UserWalletService userWalletService;
    private ModelMapper modelMapper;

    public CreateUserWalletController(UserWalletService userWalletService, ModelMapper modelMapper){
        this.userWalletService = userWalletService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Response<UserWalletDTO>> create(@RequestBody @Valid UserWalletDTO userWalletDTO){
        Response<UserWalletDTO> response = new Response<UserWalletDTO>();

        UserWallet userWallet = this.userWalletService.save(ConverterDTOToEntity.convertDtoToEntity(userWalletDTO));

        response.setData(ConverterEntityToDTO.convertEntityToDTO(userWallet));

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
