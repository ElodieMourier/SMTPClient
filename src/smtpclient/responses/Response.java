package smtpclient.responses;

public abstract class Response {
    private int code;
    private String mess;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public Response(int code, String mess) {
        this.code = code;
        this.mess = mess;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Response)
        {
            Response r = (Response)obj;
            return r.code == this.code;
        }
        return false;
    }
    
    
}
