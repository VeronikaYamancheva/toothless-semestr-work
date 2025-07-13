package ru.itis.vhsroni.client.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.vhsroni.auth.data.entity.UserData;
import ru.itis.vhsroni.auth.repository.UserDataRepository;
import ru.itis.vhsroni.client.data.entity.Client;
import ru.itis.vhsroni.client.repository.ClientRepository;
import ru.itis.vhsroni.client.service.ClientService;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.exception.InternalException;
import ru.itis.vhsroni.profile.data.dto.inner.ClientProfileInfo;
import ru.itis.vhsroni.profile.data.dto.request.UpdateClientProfileInfoRequest;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserDataRepository userDataRepository;

    private final ClientRepository clientRepository;

    private final ErrorMessageProperties errorMessage;

    @Override
    public void createClientByUserEmail(String email) {
        UserData userData = userDataRepository.findByEmail(email)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        Client client = Client.builder()
                .user(userData)
                .build();
        clientRepository.save(client);
    }

    @Override
    public void createClientByUserId(UUID userId) {
        UserData userData = userDataRepository.findUserDataByUserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.userDataNotFound(), ErrorCode.USER_DATA_NOT_FOUND));
        Client client = Client.builder()
                .user(userData)
                .build();
        clientRepository.save(client);
    }

    @Override
    public ClientProfileInfo getClientProfileInfoByUserId(UUID userId) {
        Client client = clientRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.clientNotFound(), ErrorCode.CLIENT_NOT_FOUND));
        return ClientProfileInfo.builder()
                .passportFullNumber(client.getPassportFullNumber())
                .passportIssueDate(client.getPassportIssueDate())
                .passportIssuedBy(client.getPassportIssuedBy())
                .passportDivisionCode(client.getPassportDivisionCode())
                .policyNumber(client.getPolicyNumber())
                .snils(client.getSnils())
                .build();
    }

    @Override
    public void updateClientProfileInfo(UUID userId, UpdateClientProfileInfoRequest updateClientProfileInfoRequest) {
        Client client = clientRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new InternalException(errorMessage.clientNotFound(), ErrorCode.CLIENT_NOT_FOUND));
        client.setPassportFullNumber(updateClientProfileInfoRequest.passportFullNumber());
        client.setPassportIssuedBy(updateClientProfileInfoRequest.passportIssuedBy());
        client.setPassportIssueDate(updateClientProfileInfoRequest.passportIssueDate());
        client.setPassportDivisionCode(updateClientProfileInfoRequest.passportDivisionCode());
        client.setPolicyNumber(updateClientProfileInfoRequest.policyNumber());
        client.setSnils(updateClientProfileInfoRequest.snils());
        clientRepository.save(client);
    }

    @Transactional
    @Override
    public void deleteClientByUserId(UUID userId) {
        clientRepository.deleteByUser_UserId(userId);
    }
}
