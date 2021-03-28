package services;


import dto.RequestDto;
import dto.RespounceDto;
import handlers.HandlerDB;
import handlers.HandlerDtoRequest;
import repository.DB;

import java.math.BigDecimal;
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

    public void setHandlerDB(HandlerDB handlerDB) {
        this.handlerDB = handlerDB;
    }

    HandlerDB handlerDB;


    private boolean isBlankString(String string) {
        return string == null || string.trim().isEmpty();
    }

    public BigDecimal fullRatio(ArrayList<String[]> fullFraction) {

        BigDecimal k = BigDecimal.valueOf(1.d);

        LinkedList<String> numerator = new LinkedList();
        LinkedList<String> denominator = new LinkedList();

        numerator.addAll(Arrays.asList(fullFraction.get(0)));
        denominator.addAll(Arrays.asList(fullFraction.get(1)));

        String denumElem;

        while (!numerator.isEmpty()) {
            String num = numerator.getFirst();
            Integer typeNum = dbObject.getTableTypeMeasures().get(num);
            iLoop:
            for (String denum : denominator) {
                denumElem = denum;
                Integer typeDenum = dbObject.getTableTypeMeasures().get(denum);
                if ((int) typeNum == (int) typeDenum) {
                    if(!num.equals(denum)){k = k.multiply(handlerDB.getRatio(num, denum, dbObject));}
                    denominator.remove(denumElem);
                    numerator.removeFirst();
                    break iLoop;

                }





            }

        }
        return k;
    }

    public void run() {

        outputDto = new RespounceDto();

        ArrayList<String[]> fullFraction = null;
        ArrayList<String[]> fromParsered = handlerDtoRequest.parse(inputDto.getFrom());
        ArrayList<String[]> toParsered = handlerDtoRequest.parse(inputDto.getTo());
        fullFraction = handlerDtoRequest.getFullFraction(fromParsered, toParsered);

        boolean bothFieldEmpty = isBlankString(inputDto.getFrom()) && isBlankString(inputDto.getTo());
        boolean bothFieldKnownMeasure = handlerDtoRequest.checkConversionEnable(fullFraction, dbObject.getUniqueMeasure());
        boolean bothFieldEqualLength = handlerDtoRequest.checkConversionEnable(fullFraction);
        boolean bothFieldCountNotEqual = !handlerDtoRequest.checkConversionEnable(fullFraction, dbObject.getTableTypeMeasures());

        if (bothFieldEmpty || ((!bothFieldEqualLength || bothFieldCountNotEqual) && bothFieldKnownMeasure)) {
            outputDto.setStatusCode("404");
            outputDto.setBody("невозможно осуществить такое преобразование");

        } else if (!bothFieldKnownMeasure) {
            outputDto.setStatusCode("400");
            outputDto.setBody("используются неизвестные единицы измерения");
        } else {
            outputDto.setStatusCode("200");
            outputDto.setBody(fullRatio(fullFraction).setScale(15, BigDecimal.ROUND_HALF_UP).toPlainString());

        }


    }

}