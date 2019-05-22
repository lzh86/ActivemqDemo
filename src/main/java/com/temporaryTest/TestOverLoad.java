package com.temporaryTest;

public class TestOverLoad {
    public static void main(String[] args)
    {
        Test test = new Test();
        test.print("as");
    }
}

class Test
{
    //定义一个方法print()
    public void print(String some)
    {
        System.out.println("String version print");
    }
    //重载print()方法
    public void print(Object some)
    {
        System.out.println("Object version print");
    }
    //重载print()方法
    public void print(StringBuffer some)
    {
        System.out.println("StringBuffer version print");
    }

}
