package com.test;

import com.test.Controller.WiseSayingController;
import com.test.Json.JsonBackUpService;
import com.test.Json.JsonConverter;
import com.test.Json.JsonDBManager;
import com.test.Repository.WiseSayingRepository;
import com.test.Repository.WiseSayingRepositoryImpl;
import com.test.Service.WiseSayingService;


import java.io.*;

public class App {

    public void run() throws IOException{ //실제 작업 파일
        System.out.println("== 명언 앱 ==");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        JsonConverter jsonConverter = new JsonConverter();
        JsonDBManager jsonDBManager = new JsonDBManager(jsonConverter);
        WiseSayingRepository wiseSayingRepository = new WiseSayingRepositoryImpl(jsonDBManager);
        JsonBackUpService jsonBackUpService = new JsonBackUpService(wiseSayingRepository, jsonConverter);
        WiseSayingService wiseSayingService = new WiseSayingService(wiseSayingRepository, jsonBackUpService);
        WiseSayingController wiseSayingController = new WiseSayingController(wiseSayingService);

        while(true){
            System.out.print("명령) ");
            String cmd = br.readLine();

            if(cmd.equals("종료")) break;
            else if(cmd.equals("등록")) wiseSayingController.assign();
            else if(cmd.equals("목록")) wiseSayingController.list();
            else if(cmd.startsWith("빌드")) wiseSayingController.build();
            else if(cmd.startsWith("삭제")) {
                String[] split = cmd.split("=");
                int id = Integer.parseInt(split[1]);
                wiseSayingController.delete(id);
            }else if(cmd.startsWith("수정")) {
                String[] split = cmd.split("=");
                int id = Integer.parseInt(split[1]);
                wiseSayingController.modify(id);
            }
        }

    }

}

