package com.test.Json;

import com.test.domain.WiseSaying;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.test.Json.JsonPATHConfig.DIR_PATH;

public class JsonConverter {

    public StringBuilder writeJson(WiseSaying wiseSaying){ //두 곳에서 쓸 수 있으니까 분리
        StringBuilder sb = new StringBuilder("");
        sb.append("{\n");
        sb.append("    \"id\": \"" + wiseSaying.getId()).append("\",\n");
        sb.append("    \"author\": \"" + wiseSaying.getAuthor()).append("\",\n");
        sb.append("    \"content\": \"" + wiseSaying.getContent()).append("\"\n");
        sb.append("}");

        return sb;
    }

    public List<WiseSaying> JsonFilesTransferDomains() throws IOException { //디렉토리 내 json 파일 모두 조회해서 도메인으로 변환
        List<WiseSaying> objStorage = new ArrayList<>();
        File folder = new File(DIR_PATH); //디렉토리 설정
        File[] jsonFiles = folder.listFiles((dir, name) -> name.endsWith(".json")); //디렉토리에 해당하는 모든 json 파일 읽기

        for(File file : jsonFiles){ //각 json 파일 할당
            //여기에 함수 두기
            WiseSaying wiseSaying = JsonToObjectParser(file); //파일 하나씩 파싱하여 객체로 만들기
            objStorage.add(wiseSaying);
        }

        return objStorage;
    }

    public WiseSaying JsonToObjectParser(File file) throws IOException { //Json파일을 객체로 파싱하는 구문
        StringBuilder parsingContent = new StringBuilder(" ");
        BufferedReader br = new BufferedReader(new FileReader(file)); //파일 하나 통째로 읽기
        //괄호, 중괄호 replace후 trim, 이후 쉼표로 split 후 :로 추가 split
        String line;
        while((line = br.readLine()) != null) { //입력된 값 \n 구분해서 받기
            parsingContent.append(line.trim().replaceAll("[{}\"]", "")); //공백 제거
        }

        String[] result = parsingContent.toString().split(",");

        int id = Integer.parseInt(result[0].split(":")[1].trim());
        String author = result[1].split(":")[1].trim();
        String content = result[2].split(":")[1].trim();

        br.close();
        return new WiseSaying(id, author, content);
    }

}
