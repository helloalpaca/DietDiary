package com.minseon.dietdiary.db;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.minseon.dietdiary.domain.Record;

import java.util.Date;
import java.util.Optional;

public class SQLiteRecordRepository implements RecordRepository{
    @Override
    public Record save(Record record) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Record> findByDate(Date date) {
        return Optional.empty();
    }

    @Override
    public void updateByDate(Date date) {

    }

    @Override
    public void updateByPlace(String place) {

    }

    @Override
    public void updateByEat(String eat) {

    }

    @Override
    public void updateByCategory(int category) {

    }

    @Override
    public void updateByUri(String uri) {

    }
}
