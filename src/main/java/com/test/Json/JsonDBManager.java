package com.test.Json;

import com.test.domain.WiseSaying;

import java.io.*;
import java.util.List;

import static com.test.Json.JsonPATHConfig.DIR_PATH;
import static com.test.Json.JsonPATHConfig.sequencePATH;

public class JsonDBManager {

    JsonConverter jsonConverter = new JsonConverter();

    public List<WiseSaying> JsonToMemoryDBLoader(){
        try{
            return jsonConverter.JsonFilesTransferDomains();
        }catch (IOException e){
            throw new RuntimeException("Json에서 메모리 데이터로 파싱하는 도중에 오류가 발생하였습니다.");
        }

    }

    public int getSequence() { //lastid.txt 불러오기
        try {
            File file = new File(sequencePATH);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int sequence = 0;
            while((line = br.readLine()) != null){
                sequence = Integer.parseInt(line);
            }
            br.close();
            return sequence;
        }catch (IOException e){
            throw new RuntimeException("파일을 찾을 수 없습니다.");
        }
    }

    public void setSequence(int sequence) { //lastid.txt 갱신
        try {
            FileWriter fw = new FileWriter(sequencePATH);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(Integer.toString(sequence));
            writer.close();
        }catch (IOException e){
            throw new RuntimeException("파일을 찾을 수 없습니다.");
        }
    }

    public void save(WiseSaying wiseSaying) { //Json 파일 쓰기 (덮어쓰기 가능, create, edit 둘다 가능)
        try {
            String fileName = DIR_PATH + "/" + wiseSaying.getId() + ".json";
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(jsonConverter.writeJson(wiseSaying).toString());
            writer.close();
        }catch (IOException e){
            throw new RuntimeException("파일을 찾을 수 없습니다.");
        }
    }

    public void delete(int id) { //Json 파일 삭제
        String delFilePath = DIR_PATH + "/" + id + ".json";
        File file = new File(delFilePath);
        file.delete();
    }



}
