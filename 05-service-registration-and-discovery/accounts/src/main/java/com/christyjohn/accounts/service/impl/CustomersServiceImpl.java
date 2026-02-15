package com.christyjohn.accounts.service.impl;

import com.christyjohn.accounts.dto.AccountsDto;
import com.christyjohn.accounts.dto.CardsDto;
import com.christyjohn.accounts.dto.CustomerDetailsDto;
import com.christyjohn.accounts.dto.LoansDto;
import com.christyjohn.accounts.entity.Accounts;
import com.christyjohn.accounts.entity.Customer;
import com.christyjohn.accounts.exception.ResourceNotFoundException;
import com.christyjohn.accounts.mapper.AccountsMapper;
import com.christyjohn.accounts.mapper.CustomerMapper;
import com.christyjohn.accounts.repository.AccountsRepository;
import com.christyjohn.accounts.repository.CustomerRepository;
import com.christyjohn.accounts.service.ICustomersService;
import com.christyjohn.accounts.service.client.CardsFeignClient;
import com.christyjohn.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}