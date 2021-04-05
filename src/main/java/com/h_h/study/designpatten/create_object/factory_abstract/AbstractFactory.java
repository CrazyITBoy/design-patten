package com.h_h.study.designpatten.create_object.factory_abstract;

/**
 * @author 元胡
 * @date 2021/04/05 3:18 下午
 */
public class AbstractFactory {

    public static void main(String[] args) {
        //用户网页授权登录微信
        UserLoginFactory userLoginFactory  = new WxUserLoginFactory();
        Oauth oauth = userLoginFactory.getOauth();
        oauth.oauth();

        UserCache userCache = userLoginFactory.getUserCache();
        userCache.cacheUser();

    }

}

//提供一个创建一系列产品的抽象工厂
interface UserLoginFactory {
    Oauth getOauth();

    UserCache getUserCache();
}

//由具体的工厂进行不同的实现。生产不同的产品
class AlipayUserLoginFactory implements UserLoginFactory {

    @Override
    public Oauth getOauth() {
        return new AlipayOauth();
    }

    @Override
    public UserCache getUserCache() {
        return new AlipayUseCache();
    }
}

class WxUserLoginFactory implements UserLoginFactory {

    @Override
    public Oauth getOauth() {
        return new WxOauth();
    }

    @Override
    public UserCache getUserCache() {
        return new WxUseCache();
    }
}




//---------------以下便是关于工厂方法的代码---------------

//----------生成的产品，授权对象--------
interface Oauth{
    int oauth();
}

class WxOauth implements Oauth {
    @Override
    public int oauth() {
        System.out.println("微信授权！");
        return 0;
    }
}

class AlipayOauth implements Oauth {
    @Override
    public int oauth() {
        System.out.println("支付宝授权！");
        return 1;
    }
}

interface UserCache{
    int cacheUser();
}

class AlipayUseCache implements UserCache{

    @Override
    public int cacheUser() {
        System.out.println("缓存支付宝用户OK!");
        return 0;
    }
}

class WxUseCache implements UserCache{

    @Override
    public int cacheUser() {
        System.out.println("缓存微信用户OK!");
        return 0;
    }
}