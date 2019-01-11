package com.pomocnikprogramisty.ftpfilecreation;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
class EventService {

    private FtpConfig.StreamingGateway streamingGateway;
    private CsvWriter writer;

    EventService(FtpConfig.StreamingGateway streamingGateway, CsvWriter writer) {
        this.streamingGateway = streamingGateway;
        this.writer = writer;
    }

    public void uploadData(final List<Entry> listOfInputs) throws IOException {
        streamingGateway.write(writer.writeToCsv(listOfInputs));
    }

}
