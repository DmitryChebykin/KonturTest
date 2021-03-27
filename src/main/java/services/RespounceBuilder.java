package services;


import dto.RequestDto;
import dto.RespounceDto;
import handlers.HandlerDtoRequest;
import repository.DB;

import java.util.*;

public class RespounceBuilder {

    public void setDbObject(DB dbObject) {
        this.dbObject = dbObject;
    }

    DB dbObject;

    public RespounceDto getOutputDto() {
        return outputDto;
    }

    RequestDto inputDto;

    public void setInputDto(RequestDto inputDto) {
        this.inputDto = inputDto;
    }

    RespounceDto outputDto;

    public void setHandlerDtoRequest(HandlerDtoRequest handlerDtoRequest) {
        this.handlerDtoRequest = handlerDtoRequest;
    }

    HandlerDtoRequest handlerDtoRequest;


    private boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public double fullRatio(ArrayList<String[]> fullFraction) {

        LinkedList<String> numerator = new LinkedList();
        LinkedList<String> denominator = new LinkedList();

        numerator.addAll(Arrays.asList(fullFraction.get(0)));
        denominator.addAll(Arrays.asList(fullFraction.get(1)));



        while(!numerator.isEmpty()){
            String num = numerator.getFirst();
            Integer typeNum = dbObject.getTableTypeMeasures().get(num);
            denominator.for

        }
        
        
        

        return 0f;
    }

    public void run() {

        ArrayList<String[]> fullFraction = null;
        ArrayList<String[]> fromParsered = handlerDtoRequest.parse(inputDto.getFrom());
        ArrayList<String[]> toParsered = handlerDtoRequest.parse(inputDto.getTo());
        fullFraction = handlerDtoRequest.getFullFraction(fromParsered, toParsered);

        boolean bothFieldEmpty = isBlankString(inputDto.getFrom()) && isBlankString(inputDto.getTo());
        boolean bothFieldKnownMeasure = handlerDtoRequest.checkConversionEnable(fullFraction, dbObject.getUniqueMeasure());
        boolean bothFieldCountNotEqual = !handlerDtoRequest.checkConversionEnable(fullFraction, dbObject.getTableTypeMeasures());

        if (bothFieldEmpty || (bothFieldKnownMeasure && bothFieldCountNotEqual)) {
            outputDto.setStatusCode("404");
            outputDto.setBody("невозможно осуществить такое преобразование");

        } else if (!bothFieldKnownMeasure) {
            outputDto.setStatusCode("400");
            outputDto.setBody("используются неизвестные единицы измерения");
        } else {
            outputDto.setStatusCode("200");
            outputDto.setBody(Double.toString(fullRatio(fullFraction)));

        }


    }

}