package com.epam.ticket.service.api;

import com.epam.ticket.InitialData;

import java.io.IOException;

public interface InitDBService {

    void saveInitialDataInDB(InitialData initialData) throws IOException;

}
