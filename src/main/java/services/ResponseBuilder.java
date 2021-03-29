package services;
//region Description imports
import dto.RequestDto;
import dto.ResponseDto;
import handlers.HandlerDB;
import handlers.HandlerDtoRequest;
import repository.DB;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
//endregion

public class ResponseBuilder {

    //region Description: Setters and Getters
    public void setDbObject(DB dbObject) {
        this.dbObject = dbObject;
    }

    DB dbObject;

    public ResponseDto getOutputDto() {
        return outputDto;
    }

    RequestDto inputDto;

    public void setInputDto(RequestDto inputDto) {
        this.inputDto = inputDto;
    }

    ResponseDto outputDto;

    public void setHandlerDtoRequest(HandlerDtoRequest handlerDtoRequest) {
        this.handlerDtoRequest = handlerDtoRequest;
    }

    HandlerDtoRequest handlerDtoRequest;

    public void setHandlerDB(HandlerDB handlerDB) {
        this.handlerDB = handlerDB;
    }

    HandlerDB handlerDB;
    //endregion

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

        outputDto = new ResponseDto();

        ArrayList<String[]> fullFraction = null;
        ArrayList<String[]> fromParsed = handlerDtoRequest.parse(inputDto.getFrom());
        ArrayList<String[]> toParsed = handlerDtoRequest.parse(inputDto.getTo());

        fullFraction = handlerDtoRequest.getFullFraction(fromParsed, toParsed);

        boolean bothFieldEmpty = isBlankString(inputDto.getFrom()) && isBlankString(inputDto.getTo());
        boolean bothFieldKnownMeasure = handlerDtoRequest.checkConversionEnable(fullFraction, dbObject.getUniqueMeasure());
        boolean bothFieldEqualLength = handlerDtoRequest.checkConversionEnable(fullFraction);
        boolean bothFieldCountNotEqual = !handlerDtoRequest.checkConversionEnable(fullFraction, dbObject.getTableTypeMeasures());


        if (bothFieldEmpty || ((!bothFieldEqualLength || bothFieldCountNotEqual) && bothFieldKnownMeasure)) {
            outputDto.setStatusCode("404");
            try {
                outputDto.setBody(new String("невозможно осуществить такое преобразование".getBytes("UTF-8"), System.getProperty("file.encoding")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else if (!bothFieldKnownMeasure) {
            outputDto.setStatusCode("400");
            try {
                outputDto.setBody(new String("используются неизвестные единицы измерения".getBytes("UTF-8"),System.getProperty("file.encoding")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            outputDto.setStatusCode("200");
            outputDto.setBody(fullRatio(fullFraction).setScale(15, RoundingMode.HALF_UP).toPlainString());
        }
    }
}