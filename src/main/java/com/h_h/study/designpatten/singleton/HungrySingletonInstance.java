package com.h_h.study.designpatten.singleton;

/**
 * @author 元胡
 * @date 2021/04/03 6:13 下午
 */
public class HungrySingletonInstance {


}

class HungryInstance {

    private static HungryInstance INSTANCE = new HungryInstance();

    private HungryInstance(){
        if(HungryInstance.getINSTANCE() != null){
            throw new RuntimeException("单列bean 不允许创建多例！");
        }
    }

    public static HungryInstance getINSTANCE() {
        return INSTANCE;
    }
}
