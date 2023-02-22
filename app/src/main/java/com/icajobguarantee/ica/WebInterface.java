package com.icajobguarantee.ica;

public interface WebInterface {
    public void getResponse(String response, int flag);

    public void onFailure(int flag);
}
