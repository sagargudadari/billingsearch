package com.mastercard.billingsearch.service;

import com.mastercard.billingsearch.model.SummaryRequestParamsModel;
import com.mastercard.billingsearch.repository.SummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SummaryService {

    @Autowired
    SummaryRepository summaryRepository;

    public List<Object> getSummaryData(SummaryRequestParamsModel summaryRequestParamsModel) {

        List<Object> response = new ArrayList<Object>();


        /**rest Call**/
        /**rest response parsing**/


        /****DAO CALL**/

        response = summaryRepository.getSummaryDataFromDB(summaryRequestParamsModel);

        return response;

    }
}
