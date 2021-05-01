package com.minseon.dietdiary.db;

import com.minseon.dietdiary.domain.Record;

import java.util.Date;
import java.util.Optional;

public interface RecordRepository {

    Record save(Record record);
    Optional<Record> findByDate(Date date);

    void updateByDate(Date date);
    void updateByPlace(String place);
    void updateByEat(String eat);
    void updateByCategory(int category);
    void updateByUri(String uri);
}
