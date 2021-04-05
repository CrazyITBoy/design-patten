package com.h_h.study.designpatten.factory;

/**
 * @author 元胡
 * @date 2021/04/05 2:52 下午
 */
public class SimpleFactory {

    public Stock getStock(int type){
        if(type == 1){
            return new RetailPOSStock();
        }else if(type == 2){
            return new MicroStoreStock();
        }
        return null;
    }
}


interface Stock{
    boolean subStock();
}


class RetailPOSStock implements Stock{

    @Override
    public boolean subStock() {
        return false;
    }
}

class MicroStoreStock implements Stock{

    @Override
    public boolean subStock() {
        return false;
    }
}






