package com.wallet.wallet.controllers;

import com.wallet.user.exceptions.ApiErrors;
import com.wallet.user.response.Response;
import com.wallet.wallet.dto.WalletDTO;
import com.wallet.wallet.entities.Wallet;
import com.wallet.wallet.services.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("wallet")
public class CreateWalletController {

    private WalletService walletService;
    private ModelMapper modelMapper;

    public CreateWalletController(WalletService walletService, ModelMapper modelMapper){
        this.walletService = walletService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<Response<WalletDTO>> createWallet(@Valid @RequestBody WalletDTO walletDTO){
        Response<WalletDTO> response = new Response<WalletDTO>();

        Wallet wallet = this.walletService.save(modelMapper.map(walletDTO, Wallet.class));

        response.setData(modelMapper.map(wallet, WalletDTO.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Except handler
    // MethodArgumentNotValidException: lançado toda vez que tentamos validar um objeto(@Valid do createWallet)
    // e o objeto não está valido
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExeptions(MethodArgumentNotValidException methodArgumentNotValidException){
        // getBindingResult, resultado da validação que ocorreu ao tentar validar o objeto com o @Valid
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();

        return new ApiErrors(bindingResult);
    }
}
