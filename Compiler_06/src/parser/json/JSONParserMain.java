package parser.json;

import java.io.*;
import java.util.*;

public class JSONParserMain {
	
   public static void main(String[] args) {
      FileReader file;
      int count = 0;			// for vega.json 
      String space = "    "; // for vega.json
      try {
    	  
    	// 테스트 JSON 파일을 불러온다
          file = new FileReader("sample.json");
          // JSON 클래스 객체인 parser를 선언하고 할당한다
          JSON<Map<String, Object>, List<Object>> sampleParser = new JSON<Map<String, Object>, List<Object>>(file);
          // 파싱을 위한 핸들러 선언 및 할당
          BasicHandler sampleHandler = new BasicHandler();
          // JSON의 시작이 array이므로 parseArray로 파싱하며 결과를 sampleArray 리스트에 담는다
          List<Object> sampleArray = null;
          sampleArray = sampleParser.parseArray(sampleHandler);
          
          /*
           * 파싱해서 얻은 리스트의 객체들은 각각 다른 성질을 가지고 있으므로 
           * 8개의 객체마다 가지고 있는 형식을 보며 알맞은 포맷으로 구현한다
           */
          
          // 0번째 객체 파싱 후 출력
          Map<String, Object> object0 = (Map<String, Object>) sampleArray.get(0);
          System.out.printf("%18s = { ", "Object0 : promo");
          System.out.print(object0.get("promo"));
          System.out.println(" }");
          
          // 1번째 객체 파싱 후 출력
          Map<String, Object> object1 = (Map<String, Object>) sampleArray.get(1);
          System.out.printf("%18s = { ", "Object1 : story");
          System.out.print(object1.get("story"));
          System.out.println(" }");
          
          // 2번째 객체 파싱 후 출력
          Map<String, Object> object2 = (Map<String, Object>) sampleArray.get(2);
          System.out.printf("%28s = { ", "Object2 : widgetInventory");
          System.out.print(object2.get("widgetInventory"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "sadSavingsAccount");
          System.out.print(object2.get("sadSavingsAccount"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "seattleLatitude");
          System.out.print(object2.get("seattleLatitude"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "seattleLongitude");
          System.out.print(object2.get("seattleLongitude"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "earthsMass");
          System.out.print(object2.get("earthsMass"));
          System.out.println(" }");
          
          // 3번째 객체 파싱 후 출력
          Map<String, Object> object3 = (Map<String, Object>) sampleArray.get(3);
          System.out.printf("%25s = { ", "Object3 : freckleCount");
          System.out.print(object3.get("freckleCount"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "hairy");
          System.out.print(object3.get("hairy"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "watchColor");
          System.out.print(object2.get("watchColor"));
          System.out.println(" }");
          
          // 4번째 객체 파싱 후 출력
          Map<String, Object> object4 = (Map<String, Object>) sampleArray.get(4);
          List<Object> eggCartonArray = (List<Object>) object4.get("eggCarton");
          System.out.printf("%22s = [ ", "Object4 : eggCarton");
          for (int i = 0; i < eggCartonArray.size(); i++) {
         	 System.out.print(eggCartonArray.get(i));
         	 System.out.printf(", ");
          }
          System.out.println("]");
          
          
          // 5번째 객체 파싱 후 출력
          Map<String, Object> object5 = (Map<String, Object>) sampleArray.get(5);
          List<Object> testArray = (List<Object>) object5.get("test");
          System.out.printf("%17s = [ ", "Object5 : test");
          for (int i = 0; i < testArray.size(); i++) {
         	 Map<String, Object> testObject = (Map<String, Object>) testArray.get(i);
         	 System.out.printf("{ ");
         	 System.out.printf("%2s = ", "question");
         	 System.out.print(testObject.get("question"));
         	 System.out.printf(", ");
         	 System.out.printf("%2s = ", "answer");
         	 System.out.print(testObject.get("answer"));
         	 System.out.printf(" }");
         	 System.out.printf(", ");
          }
          System.out.println("]");
          
          // 6번째 객체 파싱 후 출력
          Map<String, Object> object6 = (Map<String, Object>) sampleArray.get(6);
          System.out.printf("%20s = { ", "Object6 : $schema");
          System.out.print(object6.get("$schema"));
          System.out.print(" }, ");
          System.out.printf("%2s = { ", "title");
          System.out.print(object6.get("title"));
          System.out.print(" }, ");
          Map<String, Object> propertiesObject = (Map<String, Object>) object6.get("properties");
          System.out.printf("%2s = { ", "properties");
          Map<String, Object> nameObject = (Map<String, Object>) propertiesObject.get("name");
          System.out.printf("%2s = { ", "name");
          System.out.printf("%2s = ", "type");
          System.out.print(nameObject.get("type"));
     	 System.out.printf(" }, ");
     	 Map<String, Object> ageObject = (Map<String, Object>) propertiesObject.get("age");
     	 System.out.printf("%2s = { ", "age");
     	 System.out.printf("%2s = ", "type");
          System.out.print(ageObject.get("type"));
     	 System.out.printf(" }, ");
     	 System.out.printf("%2s = ", "description");
          System.out.print(ageObject.get("description"));
     	 System.out.printf(" }");
     	 Map<String, Object> declawedObject = (Map<String, Object>) propertiesObject.get("declawed");
     	 // 캡쳐를 위한 줄 바꿈
     	 System.out.println();
     	 System.out.printf("%14s",",");
     	 // 캡쳐를 위한 줄 바꿈
     	 System.out.printf("%2s = { ", "declawed");
          System.out.printf("%2s = ", "type");
          System.out.print(declawedObject.get("type"));
     	 System.out.printf(" }, ");
     	 Map<String, Object> descriptionObject = (Map<String, Object>) propertiesObject.get("description");
          System.out.printf("%2s = { ", "description");
          System.out.printf("%2s = ", "type");
          System.out.print(descriptionObject.get("type"));
     	 System.out.printf(" }");
     	 System.out.println(" }");
     	
          // 7번째 객체 파싱 후 출력
          Map<String, Object> object7 = (Map<String, Object>) sampleArray.get(7);
          System.out.printf("%15s = { ", "Object7 : dt");
          System.out.print(object7.get("dt"));
          System.out.printf(" }, ");
          Map<String, Object> tempObject = (Map<String, Object>) object7.get("temp");
          System.out.printf("%2s = { ", "temp");
          System.out.printf("%2s = ", "day");
          System.out.print(tempObject.get("day"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "min");
          System.out.print(tempObject.get("min"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "max");
          System.out.print(tempObject.get("max"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "night");
          System.out.print(tempObject.get("night"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "eve");
          System.out.print(tempObject.get("eve"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "morn");
          System.out.print(tempObject.get("morn"));
          System.out.printf(" }, ");
          System.out.printf("%2s = { ", "pressure");
          System.out.print(object7.get("pressure"));
          System.out.printf(" }, ");
          System.out.printf("%2s = { ", "humidity");
          System.out.print(object7.get("humidity"));
          System.out.printf(" }");
          List<Object> weatherArray = (List<Object>) (object7.get("weather"));
          // 캡쳐를 위한 줄 바꿈
     	 System.out.println();
     	 System.out.printf("%14s",",");
     	 // 캡쳐를 위한 줄 바꿈
          System.out.printf("%2s = [ ", "weather");
          Map<String, Object> weatherObject = (Map<String, Object>) weatherArray.get(0);
          System.out.printf("%2s = ", "id");
          System.out.print(weatherObject.get("id"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "main");
          System.out.print(weatherObject.get("main"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "description");
          System.out.print(weatherObject.get("description"));
          System.out.printf(", ");
          System.out.printf("%2s = ", "icon");
          System.out.print(weatherObject.get("icon"));
          System.out.printf(" ], ");
          System.out.printf("%2s = { ", "speed");
          System.out.print(object7.get("speed"));
          System.out.printf(" }, ");
          System.out.printf("%2s = { ", "deg");
          System.out.print(object7.get("deg"));
          System.out.printf(" }, ");
          System.out.printf("%2s = { ", "clouds");
          System.out.print(object7.get("clouds"));
          System.out.printf(" }");
          System.out.println(" }");
          // sample.json 파싱 완료. 
          file.close(); // 파일을 close.
          System.out.println("\n");
          
         // 새로운 테스트 JSON 파일을 불러온다
    	  file = new FileReader("vega.json");
    	   JSON<Map<String, Object >, List<Object >> parser = new JSON<Map<String, Object >, List<Object >>(file);
    	   BasicHandler handler = new BasicHandler();
    	   Object result = parser.parse(new BasicHandler());
    	   String input= result.toString();
    	   StringBuffer buff = new StringBuffer();
    	   String identify = null;

    	   for(int i=0; i<input.length(); i++) {
    	    identify = input.substring(i, i+1);
    	    if(identify.equals("{")||identify.equals("[")) {
    	     buff.append(identify).append("\n");
    	     count++;
    	     for(int j=0; j<count; j++)  buff.append(space);
    	     }
    	    else if(identify.equals("}")||identify.equals("]")) {
    	     buff.append("\n");
    	     count--;
    	     for(int j=0; j<count; j++)  buff.append(space);
    	     buff.append(identify);
    	    }
    	    else if(identify.equals(",")) {
    	     buff.append(identify);
    	     buff.append("\n");
    	     for(int j=0; j<count; j++)  buff.append(space);    
    	    }
    	    else 
    	     buff.append(identify);
    	   }
    	   System.out.println(buff);         
         // vega.json 파일을  파싱 완료 후 파일을 닫는다
         
                       
         file.close();
         
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}