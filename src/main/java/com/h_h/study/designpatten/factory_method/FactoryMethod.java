package com.h_h.study.designpatten.factory_method;

/**
 * @author 元胡
 * @date 2021/04/05 3:21 下午
 */
public  class FactoryMethod {

    public static void main(String[] args) {
        OauthFactory oauthFactory = new AlipayOauthFactory();
        Oauth oauth = oauthFactory.getOauth();
        System.out.println(oauth.oauth());

        OauthFactory wxOauthFactory = new WxOauthFactory();
        Oauth wxOauth = wxOauthFactory.getOauth();
        System.out.println(wxOauth.oauth());
    }
}

//简言之：一个工厂只负责生产一个产品

//---------------以下便是关于工厂方法的代码---------------
//抽象工厂
interface OauthFactory{

    //获得授权对象
    Oauth getOauth();
}


//具体工厂 生成具体的授权对象
class WxOauthFactory implements OauthFactory{

    @Override
    public Oauth getOauth() {
        return new WxOauth();
    }
}

//具体工厂 生成具体的授权对象
class AlipayOauthFactory implements OauthFactory{

    @Override
    public Oauth getOauth() {
        return new AlipayOauth();
    }
}

//----------生成的产品，授权对象--------
interface Oauth{
    int oauth();
}

class WxOauth implements Oauth{
    @Override
    public int oauth() {
        System.out.println("微信授权！");
        return 0;
    }
}

class AlipayOauth implements Oauth{
    @Override
    public int oauth() {
        System.out.println("支付宝授权！");
        return 1;
    }
}