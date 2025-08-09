package com.test.Json;

import com.test.Repository.WiseSayingRepository;
import com.test.Repository.WiseSayingRepositoryImpl;
import com.test.domain.WiseSaying;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.test.Json.JsonPATHConfig.DIR_PATH;

public class JsonBackUpService {

    private final WiseSayingRepository wsRepo = WiseSayingRepositoryImpl.getInstance();
    private final JsonConverter jsonConverter = new JsonConverter();
    public void dataBackUp(){
        try {
            String fileName = DIR_PATH + "/" + "data.json";
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fw);
            StringBuilder sb = new StringBuilder("[");
            List<WiseSaying> ws = wsRepo.findAll();

            int num = 1;
            for (WiseSaying w : ws) {
                sb.append(jsonConverter.writeJson(w));
                if (num != ws.size()) sb.append(",\n");
                num++;
            }
            sb.append("]");
            writer.write(sb.toString());
            writer.close();
        }catch (IOException e){
            throw new RuntimeException("파일을 찾을 수 없습니다.");
        }
    }

}
