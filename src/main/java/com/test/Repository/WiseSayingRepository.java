package com.test.Repository;

import com.test.domain.WiseSaying;

import java.io.*;
import java.util.List;
import java.util.Optional;

public interface WiseSayingRepository {

    void memoryLoad() throws IOException;
    void delete(int id);
    void save(WiseSaying wiseSaying); //저장해서 json 업데이트
    List<WiseSaying> findAll();
    Optional<WiseSaying> findById(int id);
    void modify(WiseSaying wiseSaying);

}
