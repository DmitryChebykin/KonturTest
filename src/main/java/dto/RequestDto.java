package dto;

public class RequestDto {

    public RequestDto() {
        from = " ";
        to = " ";

    }

    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom() {
        if(from.equals(null)){this.from = " ";} ;
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        if(to.equals(null)){this.to = " ";}
        this.to = to;
    }
}
