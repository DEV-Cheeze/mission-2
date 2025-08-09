package com.test.Repository;

import com.test.Json.JsonDBManager;
import com.test.domain.WiseSaying;
import java.io.*;
import java.util.*;

public class WiseSayingRepositoryImpl implements WiseSayingRepository {

    private final Map<Integer, WiseSaying> storage = new HashMap<>();
    private int sequence = 0;

    private static final WiseSayingRepository instance = new WiseSayingRepositoryImpl(); //singleton
    private final JsonDBManager jsonDBManager = new JsonDBManager();


    public static WiseSayingRepository getInstance(){
        return instance;
    }

    private WiseSayingRepositoryImpl(){ //singleton
        try {
            memoryLoad();
        } catch (IOException e) {
            throw new RuntimeException("메모리 로드 실패");
        }
    }

    @Override
    public void memoryLoad() throws IOException{ //메모리에 미리 적재
        List<WiseSaying> wiseSayings = jsonDBManager.JsonToMemoryDBLoader(); //로드
        wiseSayings.stream().forEach(v -> storage.put(v.getId(), v));
        sequence = jsonDBManager.getSequence();
        //디렉토리 안에있는 json 파일들을 모두 불러오기
        //그 json 파일을 객체로 변환하여 메모리에 올리기 return WiseSaying
    }

    @Override
    public void save(WiseSaying wiseSaying){
        wiseSaying.setId(++sequence);
        storage.put(wiseSaying.getId(), wiseSaying);
        jsonDBManager.setSequence(sequence);
        jsonDBManager.save(wiseSaying);
    }

    @Override
    public void delete(int id){
        storage.remove(id);
        jsonDBManager.delete(id);
    }

    @Override
    public void modify(WiseSaying wiseSaying){
        storage.put(wiseSaying.getId(), wiseSaying);
        jsonDBManager.save(wiseSaying);
    }

    @Override
    public Optional<WiseSaying> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<WiseSaying> findAll(){
        return new ArrayList<>(storage.values());
    }

}
