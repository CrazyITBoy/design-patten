package com.h_h.study.designpatten.create_object.factory_simple;

/**
 * 简单工厂生
 * @author 元胡
 * @date 2021/04/05 2:52 下午
 */
public class SimpleFactory {

    /**
     * 核心代码
     * 只有一个工厂SimpleFactory
     * 根据不同的条件创建不同的产品
     * @param type
     * @return
     */
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






