package kr.co.sist.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/kakao")
public class KakaoProxyController {

    private static final String KAKAO_API_KEY = "870f846de75ad04159b6d441b9c44094";

    @GetMapping("/search")
    public ResponseEntity<?> searchAddress(@RequestParam("query") String query) {
        try {
            String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" +
                    URLEncoder.encode(query, StandardCharsets.UTF_8);

            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + KAKAO_API_KEY);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();

            return ResponseEntity.ok(result.toString());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("검색 실패: " + e.getMessage());
        }
    }
}


