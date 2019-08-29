package com.mastercard.billingsearch.repository;


import com.mastercard.billingsearch.model.SummaryRequestParamsModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SummaryRepository {

    public List<Object> getSummaryDataFromDB(SummaryRequestParamsModel summaryRequestParamsModel){


        return new ArrayList<Object>();
    }
}
