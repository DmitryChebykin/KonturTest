package Service;

import DTO.RequestDto;
import Repository.H2MemoryDatabase;

import java.util.ArrayList;
import java.util.List;

public class CalcResponsePostService {
    public void filingDB(List<String[]> records){
      H2MemoryDatabase h2MemoryDatabase =  new H2MemoryDatabase();
      h2MemoryDatabase.AddArrayListToTable(records);
    }
    public void getRatio(RequestDto input) {

    }

}
