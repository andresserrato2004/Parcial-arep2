package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class proxycontroller {
    
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String[] SRVS =  {"http://13.218.77.116:8080", "http://23.23.6.119:8080"};

    int numberurl = 0;

    public String activopasivo(){
        try {
            String serverUrl = SRVS[numberurl];
            numberurl = 0;
            URL obj = new URL(serverUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            System.out.print("respuesta del servidor 1" + responseCode);
            System.out.println(numberurl + "\n");
            System.out.println("Servidor 1 funcionando\n");
            return serverUrl;    
        } catch (Exception e) {
            numberurl = (numberurl + 1) % SRVS.length;
            String serverUrl = SRVS[numberurl];
            System.out.println(numberurl + "\n");
            System.out.println(serverUrl + "\n");
            System.out.println("Servidor 2 funcionando\n");
            numberurl = 0;
            return serverUrl;
        }
        
    }
    
    
    @GetMapping("/linearSearch")
    public String  linearSearch(@RequestParam("list") String list1, @RequestParam("value") String value) throws IOException{

        String correctURL = activopasivo()+"/linearSearch?list="+list1+"&value="+value ;

        System.out.print(correctURL);

        URL obj = new URL(correctURL);
        
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        //System.out.print("hasta aqui llega el programa" + responseCode);
        System.out.println(" ");
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return "ERROR de del backend";
        }
    }


    @GetMapping("/binarySearch")
    public String binarySearch(@RequestParam("list") String list1, @RequestParam("value") String value)throws IOException{

        String correctURL = activopasivo()+"/binarySearch?list="+list1+"&value="+value ;

        System.out.print(correctURL);

        URL obj = new URL(correctURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        
        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println(" ");
        System.out.println("GET Response Code :: " + responseCode);
        
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
            return "ERROR de del backend";
        }
    }


}
